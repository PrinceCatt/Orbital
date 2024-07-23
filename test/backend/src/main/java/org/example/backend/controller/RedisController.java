package org.example.backend.controller;

import org.example.backend.entity.UserLike;
import org.example.backend.mapper.UserLikeMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.service.RedisService;
import org.example.backend.utils.JwtUtils;
import org.example.backend.utils.RedisKeyUtils;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userlike")
public class RedisController {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserLikeMapper userLikeMapper;
    @Autowired
    private UserMapper userMapper;

    //like
    @PostMapping("/like")
    public Result like(int commentId,  HttpServletRequest request) {

        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        int userId = userMapper.findByEmail(email).getId();

        redisService.saveLikeToRedis(commentId, userId);
        redisService.incrementLikeCount(commentId, userId);
        return Result.ok();
    }

    //unlike when clicked the second time
    @PostMapping("/unlike")
    public Result unlike(int commentId,  HttpServletRequest request) {

        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        int userId = userMapper.findByEmail(email).getId();

        redisService.unlikeFromRedis(commentId, userId);
        redisService.decrementLikeCount(commentId, userId);
        return Result.ok();
    }

    @GetMapping("/restore")
    public Result restore() {
        redisService.savaInfoFromDb2Re();
        return Result.ok();
    }

    @GetMapping("/status")
    public Result status(int commentId,  HttpServletRequest request){

        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        int userId = userMapper.findByEmail(email).getId();

        String key = RedisKeyUtils.getLikedKey(commentId, userId);
        Object temp = redisTemplate.opsForHash().get(RedisKeyUtils.MapUserLiked, key);
        if(temp == null){
            int status = 0;
            return Result.ok().data("status", status);
        }
        int status = (int) temp;

        if(status != 1){
            status = 0;
            return Result.ok().data("status",status);
        }
        return Result.ok().data("status",status);
    }

    @GetMapping("/count")
    public Result count(int commentId) {
        int count = (int) redisTemplate.opsForHash().get(RedisKeyUtils.MapUserLikesCount, commentId);
        return Result.ok().data("count",count);
    }
}
