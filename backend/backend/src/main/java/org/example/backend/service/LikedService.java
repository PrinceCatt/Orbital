package org.example.backend.service;

import org.example.backend.entity.Post;
import org.example.backend.entity.UserLike;
import org.example.backend.mapper.PostMapper;
import org.example.backend.mapper.UserLikeMapper;
import org.example.backend.service.impl.RedisServiceImpl;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
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
    private PostMapper postMapper;

    public void transLiked(){
        List<UserLike> likeds = redisService.getLikedDataFromRedis();
        for(UserLike liked : likeds){
            Date date = new Date();
            UserLike newLike = userLikeMapper.findByPostIdAndUserId(liked.getPostId(), liked.getGiveUserId());
            if(newLike == null){
                userLikeMapper.insert(liked);
            } else {
                userLikeMapper.updateById(liked);
            }
        }
    }

    public void transCount(){
        Cursor<Map.Entry<Object, Object>> cursor = redisService.getLikedCountFromRedis();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            int postId = (int)map.getKey();
            int likes = (int) redisTemplate.opsForHash().get("MapUserLikesCount", postId);
            Post currPost = postMapper.selectById(postId);
            currPost.setLikes(likes);
            postMapper.updateById(currPost);
        }
    }
}
