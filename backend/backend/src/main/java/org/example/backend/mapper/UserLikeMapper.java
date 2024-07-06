package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;

import org.example.backend.entity.UserLike;

@Mapper
public interface UserLikeMapper extends BaseMapper<UserLike> {

    @Select("select * from t_userLike where commentId = #{commentId} and userId = #{userId}")
    UserLike findByCommentIdAndUserId(int commentId, int userId);

}
