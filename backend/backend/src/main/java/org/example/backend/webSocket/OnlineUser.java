package org.example.backend.webSocket;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OnlineUser {

    private int id;
    private String name;
    private String avatar;

    @Override
    public String toString() {
        return "OnlineUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

}
