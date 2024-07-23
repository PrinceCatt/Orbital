package org.example.backend.webSocket;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.entity.User;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OnlineUserList {

    private int size;
    private List<OnlineUser> onlineUsers;

    public OnlineUserList() {
        onlineUsers = new ArrayList<>();
        size = 0;
    }

    @Override
    public String toString() {

        return "onlineUser{" +
                "size=" + size +
                ", onlineUsers=" + onlineUsers +
                '}';
    }

    public void add(User user) {

        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setId(user.getId());
        onlineUser.setName(user.getName());
        onlineUser.setAvatar(user.getAvatarPath());

        onlineUsers.add(onlineUser);
        size++;
    }

}
