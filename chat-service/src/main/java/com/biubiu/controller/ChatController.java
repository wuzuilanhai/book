package com.biubiu.controller;

import com.biubiu.server.WebSocketServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Haibiao.Zhang on 2019-03-30 15:04
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/socket/push/{uid}")
    public void push(@PathVariable("uid") String uid, String message) {
        WebSocketServer server = WebSocketServer.CACHE_SERVER_MAP.get(uid);
        if (server != null) server.onMessage(message);
    }

}
