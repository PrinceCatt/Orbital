package org.example.backend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name = "";
    private String email = "";
    @TableField("avatarPath")
    private String avatarPath;
    private String password = "";
    private String history = "";

    @TableField(exist = false)
    private List<Integer> favoritePosts;


    // Personal info not done yet:
//    private String birthday = "2000-01-01";
//    // 0 = male, 1 = female
//    private int sex = 0;
//    private String major = "";
//    private String hobbies = "";

    @TableField(exist = false)
    private List<Post> posts;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                '}';
    }


}