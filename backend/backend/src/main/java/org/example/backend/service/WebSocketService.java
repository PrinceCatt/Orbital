package org.example.backend.service;

import org.example.backend.entity.Message;
import org.example.backend.mapper.MessageMapper;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketService {

    // 存放每个客户端对应的WebSocket对象
    private static ConcurrentHashMap<Integer, WebSocketService> webSocketMap = new ConcurrentHashMap<>();

    // connection to a certain user end
    private Session session;

    private int userId;

    public static MessageMapper messageMapper = null;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") int userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
            //加入set中
        } else {
            webSocketMap.put(userId, this);
            //加入set中
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public static void close(@PathParam("userId") int userId) {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
        }
    }

    public static Map getOnlineUser() {
        return webSocketMap;
    }

}
