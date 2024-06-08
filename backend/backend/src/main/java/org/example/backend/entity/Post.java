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
    private String title;
    private String contentText;
    private String time;
    private int uid;

    @TableField(exist = false)
    private User author;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contentText='" + contentText + '\'' +
                ", time='" + time + '\'' +
                ", uid=" + uid +
                ", author=" + author.getName() +
                '}';
    }

}
