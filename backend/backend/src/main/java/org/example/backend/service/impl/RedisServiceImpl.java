package org.example.backend.service.impl;

import org.example.backend.entity.LikedStatusEnum;
import org.example.backend.entity.UserLike;
import org.example.backend.service.LikedService;
import org.example.backend.service.RedisService;
import org.example.backend.utils.RedisKeyUtils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {


    //in the redis database, two types of data are respectively stored in hashmaps
    //type1: like data, the key of hashmap is MapUserLiked, the key of like is [commentId::userId], the value either 0 or 1
    //type2: like counts of a post, the key of hashmap is MapUserLikesCount, the key of count is [commentId], the value is the number of counts


    @Resource
    RedisTemplate redisTemplate;

    @Resource
    LikedService likedService;


    //save like data to redis hashmap
    @Override
    public void saveLikeToRedis(int commentId, int giveUserId) {
        String key = RedisKeyUtils.getLikedKey(commentId, giveUserId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MapUserLiked, key, LikedStatusEnum.Like.getCode());
        System.out.println("--------------------------------------------saved");
    }

    @Override
    public void unlikeFromRedis(int commentId, int giveUserId) {
        String key = RedisKeyUtils.getLikedKey(commentId, giveUserId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MapUserLiked, key, LikedStatusEnum.Unlike.getCode());
        System.out.println("--------------------------------------------unliked");
    }


    //delete is not used for now
    @Override
    public void deleteLikedFromRedis(int commentId, int giveUserId) {
        String key = RedisKeyUtils.getLikedKey(commentId, giveUserId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MapUserLiked, key);
    }

    //increment the number of likes of a certain post given commentId
    @Override
    public void incrementLikeCount(int commentId, int userId) {
        String key = RedisKeyUtils.getLikedKey(commentId, userId);

        //the user like is not created or something went wrong so the increment of like count terminates
        if(redisTemplate.opsForHash().get(RedisKeyUtils.MapUserLiked, key) == null || // user like not created
                (int) redisTemplate.opsForHash().get(RedisKeyUtils.MapUserLiked, key) == 0)  // status being unliked which is quite impossible
        {
          return;
        }

        //the like count is not created so create one and increase
        if(redisTemplate.opsForHash().get(RedisKeyUtils.MapUserLikesCount, commentId) == null) {  // if user lies exists and being 1
            redisTemplate.opsForHash().put(RedisKeyUtils.MapUserLikesCount, commentId, 0);  // create a like count with initial value of 0
            System.out.println("--------------------------------------------incrementLikeCount 1");
            redisTemplate.opsForHash().increment(RedisKeyUtils.MapUserLikesCount, commentId, 1); // increment
        }

        //normal condition: like count exists so increase by one
        else {
            System.out.println("--------------------------------------------incrementLikeCount 2");
            redisTemplate.opsForHash().increment(RedisKeyUtils.MapUserLikesCount, commentId, 1);
        }
    }

    @Override
    public void decrementLikeCount(int commentId, int userId) {
        String key = RedisKeyUtils.getLikedKey(commentId, userId);
        System.out.println("--------------------------------------------decrementInit");
        System.out.println(redisTemplate.opsForHash().get(RedisKeyUtils.MapUserLiked, key));

        if(((int)redisTemplate.opsForHash().get(RedisKeyUtils.MapUserLiked, key) == 0)) {  // this is the status of user like being already unliked
            System.out.println("--------------------------------------------decrementLikeCount");
            redisTemplate.opsForHash().increment(RedisKeyUtils.MapUserLikesCount, commentId, -1);  // decrement
        }
    }

    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MapUserLiked, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();

            String[] split = key.split("::");
            int status =  (int) entry.getValue();


            UserLike like = new UserLike();
            like.setCommentId(Integer.parseInt(split[0]));
            like.setGiveUserId(Integer.parseInt(split[1]));
            like.setStatus(status);
            list.add(like);

            // redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }

        return list;
    }

    @Override
    public Cursor<Map.Entry<Object, Object>> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MapUserLikesCount, ScanOptions.NONE);
        return cursor;
    }


    @Override
    public void savaInfoFromDb2Re() {
        List<UserLike> likeds = likedService.getAllUserLikeFromMysql();
        if(likeds.isEmpty() || likeds.equals("")) {
            return;
        }
        Iterator<UserLike> iterator = likeds.iterator();
        while(iterator.hasNext()) {
            UserLike userLike = iterator.next();
            int commentId = userLike.getCommentId();
            int giveUserId = userLike.getGiveUserId();
            int status = userLike.getStatus();
            String key = RedisKeyUtils.getLikedKey(commentId, giveUserId);
            redisTemplate.opsForHash().put(RedisKeyUtils.MapUserLiked,key, status);
        }
    }
}
