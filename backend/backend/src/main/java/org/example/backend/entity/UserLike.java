package org.example.backend.entity;

public class UserLike {

    private int id;
    private int commentId;
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
