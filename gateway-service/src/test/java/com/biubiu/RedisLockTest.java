package com.biubiu;

import com.biubiu.util.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by Haibiao.Zhang on 2019-03-29 12:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApplication.class)
public class RedisLockTest {

    @Autowired
    private RedisLock redisLock;

    /**
     * redis分布式锁压测用例
     */
    @Test
    public void redisTest() {
        CyclicBarrier barrier = new CyclicBarrier(100);
        String key = "redis:benchmark:test";
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    RedisLock.RedisLockResult result = redisLock.tryLock(key, 1, 2, TimeUnit.SECONDS);
                    try {
                        if (result.isLock()) {
                            System.out.println("thread[" + Thread.currentThread().getName() + "] 获得了锁");
                            Thread.sleep(500);
                        }
                    } finally {
                        if (redisLock.unLock(result)) {
                            System.out.println("thread[" + Thread.currentThread().getName() + "] 释放了锁");
                        } else {
                            System.out.println("thread[" + Thread.currentThread().getName() + "] 未获得锁或业务执行超时");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
