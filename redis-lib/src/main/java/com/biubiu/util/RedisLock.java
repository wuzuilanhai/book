package com.biubiu.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * redis分布式锁 参考:https://redis.io/commands/set
 * <p>
 * 1.SET命令是原子性操作，NX指令保证只要当key不存在时才会设置value
 * 2.设置的value要有唯一性，来确保锁不会被误删(value=系统时间戳+UUID)
 * 3.当上述命令执行返回OK时，客户端获取锁成功，否则失败
 * 4.客户端可以通过redis释放脚本来释放锁
 * 5.如果锁到达了最大生存时间将会自动释放
 * <p>
 * Created by Haibiao.Zhang on 2019-03-29 10:45
 */
@Component
public class RedisLock {

    private static final String NX = "NX";

    private static final String EX = "EX";

    private static final String OK = "OK";

    private static final String UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " + "then "
            + "    return redis.call(\"del\",KEYS[1]) " + "else " + "    return 0 " + "end ";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 单机和集群redis分布式锁
     *
     * @param key     redis key
     * @param expire  锁定的最大时长(秒[EX])
     * @param timeout 尝试获取锁的最长时间
     * @param unit    尝试时间的时间单位
     * @return 锁定结果
     */
    public RedisLockResult tryLock(String key, Integer expire, long timeout, TimeUnit unit) {
        long nowNanoTime = System.nanoTime();
        long timeoutNanoTime = unit.toNanos(timeout);
        String value = System.currentTimeMillis() + UUID.randomUUID().toString();
        while ((System.nanoTime() - nowNanoTime) < timeoutNanoTime) {
            if (doTryLock(key, value, expire))
                return RedisLockResult.builder().lock(true).key(key).value(value).build();
            //随机休眠一段时间
            LockSupport.parkNanos(new Random().nextInt(50000));
        }
        return RedisLockResult.builder().lock(false).key(key).build();
    }

    /**
     * 释放锁
     *
     * @param lockResult 加锁结果
     * @return 释放结果
     */
    public boolean unLock(RedisLockResult lockResult) {
        if (lockResult.lock) {
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                Object nativeConnection = connection.getNativeConnection();
                Long result = 0L;
                List<String> keys = Collections.singletonList(lockResult.getKey());
                List<String> values = Collections.singletonList(lockResult.getValue());
                // 集群
                if (nativeConnection instanceof JedisCluster) {
                    result = (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, values);
                }
                //单机
                if (nativeConnection instanceof Jedis) {
                    result = (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, values);
                }
                return result == 1L;
            });
        }
        return false;
    }

    private boolean doTryLock(String key, String value, Integer expire) {
        String lockResult = redisTemplate.execute((RedisCallback<String>) connection -> {
            Object nativeConnection = connection.getNativeConnection();
            String result = null;
            // 集群
            if (nativeConnection instanceof JedisCluster) {
                result = ((JedisCluster) nativeConnection).set(key, value, NX, EX, expire);
            }
            //单机
            if (nativeConnection instanceof Jedis) {
                result = ((Jedis) nativeConnection).set(key, value, NX, EX, expire);
            }
            return result;
        });
        return OK.equalsIgnoreCase(lockResult);
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class RedisLockResult {

        private boolean lock;

        private String key;

        private String value;

    }

}
