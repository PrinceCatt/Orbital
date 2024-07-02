package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.User;
=======
>>>>>>> 89b201ead3a5e22bd7d78453fe26da7f7bbe99f9
import org.example.backend.entity.UserLike;

@Mapper
public interface UserLikeMapper extends BaseMapper<UserLike> {
<<<<<<< HEAD

    @Select("select * from t_userLike where postId = #{postId} and userId = #{userId}")
    UserLike findByPostIdAndUserId(int postId, int userId);

=======
>>>>>>> 89b201ead3a5e22bd7d78453fe26da7f7bbe99f9
}
