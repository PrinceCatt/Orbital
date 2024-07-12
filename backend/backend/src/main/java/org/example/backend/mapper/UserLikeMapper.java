package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.example.backend.entity.UserLike;

@Mapper
public interface UserLikeMapper extends BaseMapper<UserLike> {

    @Select("select * from t_userlike where commentId = #{commentId} and giveUserId = #{giveUserId}")
    UserLike findByCommentIdAndUserId(@Param("commentId") int commentId, @Param("giveUserId") int giveUserId);

}
