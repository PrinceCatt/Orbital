package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.backend.entity.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where email = #{email}")
    User findByEmail(String email);

    @Select("select * from t_user where email = #{email} and password = #{password}")
    User findByEmailAndPassword(String email, String password);

    @Select("select * from t_user where id = #{uid}")
    String findNameByUid(int uid);

    @Update("update t_user set name=#{name} where email=#{email}")
    int updateName(@Param("email") String email, @Param("name") String name);

    @Update("update t_user set avatarPath=#{avatarPath} where email=#{email}")
    int updateAvatar(@Param("email")String email, @Param("avatarPath") String avatarPath);

    @Update("update t_user set password=#{password} where email=#{email}")
    int changePassword(@Param("email")String email, @Param("password")String password);

    @Select("select * from t_user where email = #{email}")
    @Results(
            {
                    @Result(column = "id",property = "id"),
                    @Result(column = "name",property = "name"),
                    @Result(column = "avatarPath",property = "avatarPath"),
                    @Result(column = "id",property = "posts",javaType= List.class,
                            many = @Many(select = "org.example.backend.mapper.PostMapper.selectByUid")
                    )
            }
    )
    User findPostsByEmail(String email);
}