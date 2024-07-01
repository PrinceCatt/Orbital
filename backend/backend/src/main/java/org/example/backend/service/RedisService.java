package org.example.backend.service;

import org.example.backend.entity.UserLike;

import java.util.List;

public interface RedisService {


    void saveLikeToRedis(String likedUserId, String giveLikeId);

    void unlikeFromRedis(String likedUserId, String giveLikeId);

    void deleteLikedFromRedis(String likedUserId, String giveLikeId);

    void incrementLikeCount(String likedUserId);

    void decrementLikeCount(String likedUserId);

    List<UserLike> getLikedDataFromRedis();
}
