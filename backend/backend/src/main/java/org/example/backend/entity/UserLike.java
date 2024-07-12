package org.example.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_userlike")
public class UserLike {

    private int id;
    @TableField("commentId")
    private int commentId;
    @TableField("giveUserId")
    private int giveUserId;
    private int status = LikedStatusEnum.Unlike.getCode();

    public UserLike() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getGiveUserId() {
        return giveUserId;
    }

    public void setGiveUserId(int giveUserId) {
        this.giveUserId = giveUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserLike(int commentId, int giveUserId, int status) {
        this.commentId = commentId;
        this.giveUserId = giveUserId;
        this.status = status;
    }
}
