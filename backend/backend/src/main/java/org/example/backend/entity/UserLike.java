package org.example.backend.entity;

public class UserLike {

    private int id;
    private int likedUserId;
    private int giveUserId;
    private int status = LikedStatusEnum.Unlike.getCode();

    public UserLike() {}

    public UserLike(int id, int likedUserId, int giveUserId, int status) {
        this.likedUserId = likedUserId;
        this.giveUserId = giveUserId;
        this.status = status;
    }
}
