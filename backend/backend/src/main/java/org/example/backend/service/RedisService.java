package org.example.backend.service;

import org.example.backend.entity.UserLike;
import org.springframework.data.redis.core.Cursor;

import java.util.List;
import java.util.Map;


public interface RedisService {


    void saveLikeToRedis(int likedUserId, int giveLikeId);

    void unlikeFromRedis(int likedUserId, int giveLikeId);

    void deleteLikedFromRedis(int likedUserId, int giveLikeId);

    void incrementLikeCount(int likedUserId, int userId);

    void decrementLikeCount(int likedUserId, int userId);

    List<UserLike> getLikedDataFromRedis();

    Cursor<Map.Entry<Object, Object>> getLikedCountFromRedis();

    void savaInfoFromDb2Re();

}
