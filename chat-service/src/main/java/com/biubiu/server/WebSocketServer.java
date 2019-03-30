package com.biubiu.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.biubiu.dto.Message;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Haibiao.Zhang on 2019-03-30 15:08
 */
@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocketServer {

    public static final Map<String, WebSocketServer> CACHE_SERVER_MAP = Maps.newConcurrentMap();

    //在线人数
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String userId;

    /**
     * 建立连接
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        int count = ONLINE_COUNT.incrementAndGet();
        CACHE_SERVER_MAP.putIfAbsent(userId, this);
        log.info("[onOpen] ==> online count is: {}", count);
        sendMessage("connect success");
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        WebSocketServer socketServer = CACHE_SERVER_MAP.get(userId);
        if (socketServer == null) return;
        int count = ONLINE_COUNT.decrementAndGet();
        CACHE_SERVER_MAP.remove(userId);
        log.info("[onClose] ==> online count is: {}", count);
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("receive message from {} : {}", userId, message);
        if (StringUtils.isEmpty(message)) return;
        JSONArray array = JSONArray.parseArray(message);
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String toUserId = object.getString("toId");
            String content = object.getString("message");
            if (StringUtils.isEmpty(toUserId) && StringUtils.isEmpty(content)) {
                WebSocketServer server = CACHE_SERVER_MAP.get(toUserId);
                if (server != null) {
                    Message messageDto = Message.builder()
                            .fromId(this.userId)
                            .toId(toUserId)
                            .message(content)
                            .build();
                    server.sendMessage(JSON.toJSONString(messageDto));
                }
            }
        }
    }

    @OnError
    public void OnError(Throwable error) {
        log.error(error.getLocalizedMessage());
    }

    /**
     * 服务器主动推送
     */
    private void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

}
