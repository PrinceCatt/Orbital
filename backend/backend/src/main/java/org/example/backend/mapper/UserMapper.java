package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.backend.entity.User;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where email = #{email}")
    User findByEmail(String email);

    @Select("select * from t_user where email = #{email} and password = #{password}")
    User findByEmailAndPassword(String email, String password);

    @Update("update t_user set name=#{name} where email=#{email}")
    int updateName(@Param("email") String email, @Param("name") String name);

    @Update("update t_user set avatar=#{avatar} where email=#{email}")
    int updateAvatar(@Param("email")String email, @Param("avatar") MultipartFile avatar);

}