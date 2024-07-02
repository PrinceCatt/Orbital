package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.User;
import org.example.backend.entity.UserLike;

@Mapper
public interface UserLikeMapper extends BaseMapper<UserLike> {

    @Select("select * from t_userLike where postId = #{postId} and userId = #{userId}")
    UserLike findByPostIdAndUserId(int postId, int userId);

}
