package org.example.backend.entity;

public class UserLike {

    private int id;
    private int postId;
    private int giveUserId;
    private int status = LikedStatusEnum.Unlike.getCode();

    public UserLike() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public UserLike(int postId, int giveUserId, int status) {
        this.postId = postId;
        this.giveUserId = giveUserId;
        this.status = status;
    }
}
