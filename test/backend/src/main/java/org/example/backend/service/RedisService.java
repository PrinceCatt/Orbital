package org.example.backend.service;

import org.example.backend.entity.UserLike;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


public interface RedisService {

    @Resource
    RedisTemplate redisTemplate = new RedisTemplate();

    void saveLikeToRedis(int likedUserId, int giveLikeId);

    void unlikeFromRedis(int likedUserId, int giveLikeId);

    void deleteLikedFromRedis(int likedUserId, int giveLikeId);

    void incrementLikeCount(int likedUserId, int userId);

    void decrementLikeCount(int likedUserId, int userId);

    List<UserLike> getLikedDataFromRedis();

    Cursor<Map.Entry<Object, Object>> getLikedCountFromRedis();

    void savaInfoFromDb2Re();

}
