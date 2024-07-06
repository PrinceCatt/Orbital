package org.example.backend.controller;

import org.example.backend.entity.UserLike;
import org.example.backend.mapper.UserLikeMapper;
import org.example.backend.service.RedisService;
import org.example.backend.utils.RedisKeyUtils;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userlike")
public class RedisController {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserLikeMapper userLikeMapper;

    //like
    @PostMapping("/like")
    public Result like(int postId, int userId) {
        RedisService redisService = (RedisService) redisTemplate;
        redisService.saveLikeToRedis(postId, userId);
        redisService.incrementLikeCount(postId, userId);
        return Result.ok();
    }

    //unlike when clicked the second time
    @PostMapping("/unlike")
    public Result unlike(int postId, int userId) {
        RedisService redisService = (RedisService) redisTemplate;
        redisService.unlikeFromRedis(postId, userId);
        redisService.decrementLikeCount(postId, userId);
        return Result.ok();
    }

    @GetMapping("/restore")
    public Result restore() {
        redisService.savaInfoFromDb2Re();
        return Result.ok();
    }

    @GetMapping("/status")
    public Result status(int commentId, int userId){
        String key = RedisKeyUtils.getLikedKey(commentId, userId);
        UserLike userLike = (UserLike) redisTemplate.opsForHash().get(RedisKeyUtils.MapUserLiked, key);
        int status = userLike.getStatus();
        return Result.ok().data("status",status);
    }
}
