package org.example.backend.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.backend.entity.Comment;
import org.example.backend.entity.UserLike;
import org.example.backend.mapper.CommentMapper;
import org.example.backend.mapper.UserLikeMapper;
import org.example.backend.service.impl.RedisServiceImpl;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LikedService {

    @Resource
    private RedisServiceImpl redisService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserLikeMapper userLikeMapper;

    @Resource
    private CommentMapper commentMapper;

    //transfer liked from redis to mysql
    public void transLiked() {
        List<UserLike> likeds = redisService.getLikedDataFromRedis();
        for (UserLike liked : likeds) {
            Date date = new Date();
            UserLike newLike = userLikeMapper.findByCommentIdAndUserId(liked.getCommentId(), liked.getGiveUserId());
            if (newLike == null) {
                userLikeMapper.insert(liked);
            } else {
                liked.setId(newLike.getId());
                userLikeMapper.updateById(liked);
            }
        }
    }

    //transfer count from redis to mysql
    public void transCount() {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.getLikedCountFromRedis();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            int commentId = (int) map.getKey();
            int likes = (int) redisTemplate.opsForHash().get("MapUserLikesCount", commentId);
            Comment currComment = commentMapper.selectById(commentId);
            currComment.setLikes(likes);

            if (commentId == 35) {
                System.out.println(likes);
            }

            commentMapper.updateById(currComment);
        }
    }

    //get all userLike from mysql to a list of userLike
    public List<UserLike> getAllUserLikeFromMysql() {
        QueryWrapper<UserLike> queryWrapper = new QueryWrapper<>();
        List<UserLike> map = userLikeMapper.selectList(queryWrapper);
        return map;
    }
}