package org.example.backend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.net.URL;

@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name = "";
    private String email = "";
    private String avatar = "";
    private String password = "";

// private    char[] biography = new char[100];

//    public char[] getBiography() {
//        return biography;
//    }
//

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatar=" + avatar +
                '}';
    }
//    public void setBiography(char[] biography) {
//        this.biography = biography;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}