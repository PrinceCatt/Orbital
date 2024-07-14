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
@TableName("t_comment")
public class Comment {

    private int likes = 0;

    @TableId(type = IdType.AUTO)
    private int id;

    private String content;
    private int uid;

    @TableField(exist = false)
    private String authorName;
    @TableField(exist = false)
    private String avatar;

    @TableField("createTime")
    private String createTime;
    @TableField("postId")
    private int postId;
    @TableField("parentCommentId")
    private int parentCommentId = -1;

    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>();

    // For setting parentCommentId
    @TableField(exist = false)
    private Comment parentComment;

    @TableField(exist = false)
    private String parentCommentAuthor;


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", uid=" + uid +
                ", authorName='" + authorName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime='" + createTime + '\'' +
                ", postId=" + postId +
                ", parentCommentId=" + parentCommentId +
                '}';
    }
}
