package org.example.backend.webSocket;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.backend.config.WebSocketConfig;
import org.example.backend.entity.SocketMsg;
import org.example.backend.entity.User;
import org.example.backend.mapper.MessageMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;



@Slf4j
@ServerEndpoint(value = "/ws", configurator = WebSocketConfig.class, encoders = {OnlineUserEncoder.class, MessageEncoder.class})
@Component
public class MyWebSocket {
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    private static UserMapper userMapper;
    private static MessageMapper messageMapper; ;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private User user;
    private String username;
    private int uid;

    private static Map<String, Session> map = new HashMap<String, Session>();
    private static Map<Integer, String> uidSessionIdMap = new HashMap<Integer, String>();

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        MyWebSocket.userMapper = userMapper;
    }

    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        MyWebSocket.messageMapper = messageMapper;
    }


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;

        this.user = getUserBySession(session);
        this.username = user.getName();
        this.uid = user.getId();
        System.out.println("New connection " + username);

        map.put(session.getId(), session);
        uidSessionIdMap.put(uid, session.getId());

        webSocketSet.add(this);     //加入set中

        sendOnlineUsers();

        System.out.println("有新连接加入:" + username + ",当前在线人数为" + webSocketSet.size());

        SocketMsg noticeMsg = new SocketMsg();
        noticeMsg.setMsg(username + " is online now (Uid: " + user.getId() + ")-->Currently online: " + webSocketSet.size());

        broadcastForElse(noticeMsg);

        SocketMsg welcomeMsg = new SocketMsg();
        welcomeMsg.setMsg("Congratulations! You are now connected to WebSocket server as " + username + "!");

        session.getAsyncRemote().sendObject(welcomeMsg);

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {

        webSocketSet.remove(this);  //从set中删除
        uidSessionIdMap.remove(uid);
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());

        sendOnlineUsers();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */

    @OnMessage
    public void onMessage(String message, Session session) {
        user = getUserBySession(session);
        username = user.getName();

        System.out.println("来自客户端的消息-->" + username + ": " + message);

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;

        try {
            socketMsg = objectMapper.readValue(message, SocketMsg.class);

            if (StringUtils.isBlank(socketMsg.getMsg())) {
                SocketMsg emptyMsg = new SocketMsg();
                emptyMsg.setMsg("System message: You cannot send empty message.");
                session.getAsyncRemote().sendObject(emptyMsg);
            }

            else if (socketMsg.getType() == 1) {

                //单聊.需要找到发送者和接受者.

                socketMsg.setFromUser(session.getId());//发送者.
                socketMsg.setFromUid(user.getId());
                Session fromSession = map.get(socketMsg.getFromUser());
                String toSessionId = uidSessionIdMap.get(socketMsg.getToUid());

                if (userMapper.selectById(socketMsg.getToUid()) == null || Objects.equals(fromSession.getId(), toSessionId)) {
                    SocketMsg warningMsg = new SocketMsg();
                    warningMsg.setType(1);
                    warningMsg.setMsg("System message: you have keyed in an invalid user id.");
                    fromSession.getBasicRemote().sendObject(warningMsg);
                } else {
                    // save the valid private message
                    messageMapper.insert(socketMsg);
                    Session toSession = map.get(toSessionId);
                    //发送给接受者.
                    if (toSession != null) {
                        socketMsg.setMsg(username + ": " + socketMsg.getMsg());
                        //发送给发送者.
                        fromSession.getAsyncRemote().sendObject(socketMsg);
                        toSession.getAsyncRemote().sendObject(socketMsg);
                    } else {
                        SocketMsg noticeMsg = new SocketMsg();
                        noticeMsg.setType(1);
                        noticeMsg.setMsg("System message: the other side is not available right now.");
                        //发送给发送者.
                        fromSession.getAsyncRemote().sendObject(noticeMsg);
                    }
                }

            } else {
                //群发消息
                socketMsg.setMsg(username + ":" + socketMsg.getMsg());
                broadcast(socketMsg);
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            throw new RuntimeException(e);
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

    // get user
    public User getUserBySession(Session session) {
        String token = getHeader(session, "WEBSOCKET_PROTOCOL");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        return userMapper.findByEmail(email);
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


    public void broadcastForElse(Object object) {
        for (MyWebSocket item : webSocketSet) {
            //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
            //this.session.getBasicRemote().sendText(message);
            if (!item.session.getId().equals(this.session.getId())) {
                item.session.getAsyncRemote().sendObject(object);
            }
        }
    }

    public void broadcast(Object object) {
        for (MyWebSocket item : webSocketSet) {
            item.session.getAsyncRemote().sendObject(object);
        }
    }

    // send online users to every user
    public void sendOnlineUsers() {

        OnlineUserList onlineUsersList = new OnlineUserList();
        for (MyWebSocket webSocket : webSocketSet) {
            User onlineUser = getUserBySession(webSocket.session);
            onlineUsersList.add(onlineUser);
        }

        for (MyWebSocket item: webSocketSet) {
            try {
                item.session.getBasicRemote().sendObject(onlineUsersList);
            } catch (IOException | EncodeException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
















