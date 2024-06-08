package org.example.backend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Setter
@Getter
@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name = "";
    private String email = "";
    private String avatarPath;
    private String password = "";

    @TableField(exist = false)
    private List<Post> posts;

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
                '}';
    }
//    public void setBiography(char[] biography) {
//        this.biography = biography;
//    }

}