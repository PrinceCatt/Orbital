package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.User;
import org.example.backend.utils.Result;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where email = #{email}")
    User findByEmail(String email);

    @Select("select * from t_user where email = #{email} and password = #{password}")
    User findByEmailAndPassword(String email, String password);

    @Insert("insert into t_user values(#{email},#{password},name=#{name})")
    int register(User user);

    @Update("update t_user set name=#{name} where email=#{email}")
    int updateName(String email, String name);

    @Update("update t_user set avatar=#{avatar} where email=#{email}")
    int updateAvatar(String email, String avatar);

}
