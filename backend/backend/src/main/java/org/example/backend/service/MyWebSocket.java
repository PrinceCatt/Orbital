package org.example.backend.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.backend.config.WebSocketConfig;
import org.example.backend.entity.SocketMsg;
import org.example.backend.mapper.UserMapper;
import org.example.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;



@Slf4j
@ServerEndpoint(value = "/ws", configurator = WebSocketConfig.class)
@Component
public class MyWebSocket {
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    @Autowired
    private UserMapper userMapper;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String username;

    private static Map<String, Session> map = new HashMap<String, Session>();

    public MyWebSocket(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.username = getUserName(session);
        System.out.println("New connection " + username);

        map.put(session.getId(), session);

        webSocketSet.add(this);     //加入set中

        System.out.println("有新连接加入:" + username + ",当前在线人数为" + webSocketSet.size());
        this.session.getAsyncRemote().sendText("恭喜" + username + "成功连接上WebSocket(其频道号：" + session.getId() + ")-->当前在线人数为：" + webSocketSet.size());

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */

    @OnMessage
    public void onMessage(String message, Session session) {
        username = getUserName(session);
        System.out.println("来自客户端的消息-->" + username + ": " + message);

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;

        try {
            socketMsg = objectMapper.readValue(message, SocketMsg.class);
            if (socketMsg.getType() == 1) {
                //单聊.需要找到发送者和接受者.

                socketMsg.setFromUser(session.getId());//发送者.
                Session fromSession = map.get(socketMsg.getFromUser());
                Session toSession = map.get(socketMsg.getToUser());
                //发送给接受者.
                if (toSession != null) {
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText(username + "：" + socketMsg.getMsg());
                    toSession.getAsyncRemote().sendText(username + "：" + socketMsg.getMsg());
                } else {
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号不对");
                }
            } else {
                //群发消息
                broadcast(username + ": " + socketMsg.getMsg());
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    // get username
    private String getUserName(Session session) {
        String token = getHeader(session, "WEBSOCKET_PROTOCOL");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        return userMapper.findByEmail(email).getName();
    }

    public String getHeader(Session session, String headerName) {
        String header = (String) session.getUserProperties().get(headerName);
        if (StringUtils.isBlank(header)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return header;
    }

    /**
     * 群发自定义消息
     */
    public void broadcast(String message) {
        for (MyWebSocket item : webSocketSet) {
            //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
            //this.session.getBasicRemote().sendText(message);
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }


}
















