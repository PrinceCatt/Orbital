package org.example.backend.controller;

import org.example.backend.service.RedisService;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {


    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/like")
    public Result like(int postId, int userId) {
        RedisService redisService = (RedisService) redisTemplate;
        redisService.saveLikeToRedis(postId, userId);
        redisService.incrementLikeCount(postId, userId);
        return Result.ok();
    }

    @GetMapping("/unlike")
    public Result unlike(int postId, int userId) {
        RedisService redisService = (RedisService) redisTemplate;
        redisService.unlikeFromRedis(postId, userId);
        redisService.decrementLikeCount(postId, userId);
        return Result.ok();
    }

}
