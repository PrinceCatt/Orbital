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
    //for storing the corresponding MyWebsocket object of each client
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    private static UserMapper userMapper;
    private static MessageMapper messageMapper; ;

    //the session to each client，we need it to push message to every user
    private Session session;
    private User user;
    private String username;
    private int uid;

    //storing sessionId map to Session
    private static Map<String, Session> map = new HashMap<String, Session>();
    //storing uid map to sessionId, for determining online users
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
     * Method when the connection is successfully established
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

        webSocketSet.add(this);
        sendOnlineUsers(); // send the updated list of online users to every online user
        System.out.println("New connection added:" + username + ",current online number:" + webSocketSet.size());

        SocketMsg noticeMsg = new SocketMsg();
        noticeMsg.setMsg(username + " is online now (Uid: " + user.getId() + ")-->Currently online: " + webSocketSet.size());
        broadcastForElse(noticeMsg);

        SocketMsg welcomeMsg = new SocketMsg();
        welcomeMsg.setMsg("Congratulations! You are now connected to NUSurf chat server as " + username + ".");
        session.getAsyncRemote().sendObject(welcomeMsg);
    }

    /**
     * Method when close websocket connection
     */
    @OnClose
    public void onClose() {

        webSocketSet.remove(this);  //delete from the set
        uidSessionIdMap.remove(uid);
        System.out.println("A connection closed！Currently online number:" + webSocketSet.size());

        sendOnlineUsers();
    }

    /**
     * Method after receiving message from the user end
     *
     * @param message The message from user end
     */

    @OnMessage
    public void onMessage(String message, Session session) {
        user = getUserBySession(session);
        username = user.getName();

        System.out.println("Information from client end-->" + username + ": " + message);

        // The data transmitted from the client is JSON data, so Jackson is used here to convert it into a SocketMSG object,
        // Then, determine whether it is private chat or group chat based on the type of socketMSG, and take corresponding actions:
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;

        try {
            socketMsg = objectMapper.readValue(message, SocketMsg.class);

            // Response when receiving empty message
            if (StringUtils.isBlank(socketMsg.getMsg())) {
                SocketMsg emptyMsg = new SocketMsg();
                emptyMsg.setMsg("System message: You cannot send empty message.");
                session.getAsyncRemote().sendObject(emptyMsg);
            }

            else if (socketMsg.getType() == 1) {
                //private msg, need to find corresponding sender and receiver.
                socketMsg.setFromUser(session.getId());//the sender.
                socketMsg.setFromUid(user.getId());
                Session fromSession = map.get(socketMsg.getFromUser());
                String toSessionId = uidSessionIdMap.get(socketMsg.getToUid());

                if (userMapper.selectById(socketMsg.getToUid()) == null || user.getId() == socketMsg.getToUid()) {
                    SocketMsg warningMsg = new SocketMsg();
                    warningMsg.setType(1);
                    warningMsg.setMsg("System message: you have keyed in an invalid user id.");
                    fromSession.getBasicRemote().sendObject(warningMsg);
                } else {
                    // save the valid private message
                    messageMapper.insert(socketMsg);
                    Session toSession = map.get(toSessionId);
                    //send msg.
                    if (toSession != null) {
                        socketMsg.setFromUser(user.getName());
                        fromSession.getAsyncRemote().sendObject(socketMsg);
                        toSession.getAsyncRemote().sendObject(socketMsg);
                    } else {
                        SocketMsg noticeMsg = new SocketMsg();
                        noticeMsg.setType(1);
                        noticeMsg.setFromUid(socketMsg.getToUid());
                        noticeMsg.setMsg("System message: the other side is not available right now.");
                        //send to the fromUser.
                        fromSession.getAsyncRemote().sendObject(noticeMsg);
                    }
                }

            } else {
                //group msg
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
        System.out.println("An error happened");
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
















