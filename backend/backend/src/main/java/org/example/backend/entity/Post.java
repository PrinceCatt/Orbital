package org.example.backend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("t_post")
public class Post {

    @TableId(type = IdType.AUTO)
    private int id;
    private String section;
    private String title;
    private String content;
    private int views;
    private int likes = 0;

    private String time;
    private int uid;

    @TableField(exist = false)
    private String authorAvatar;

    @TableField(exist = false)
    private String author;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", section='" + section + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", time='" + time + '\'' +
                ", uid=" + uid +
                ", author='" + author + '\'' +
                '}';
    }

}
