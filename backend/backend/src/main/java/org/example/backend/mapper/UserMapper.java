package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.User;
import org.example.backend.utils.Result;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where email = #{email}")
    User findByEmail(String email);

    @Select("select * from t_user where email = #{email} and password = #{password}")
    User findByEmailAndPassword(String email, String password);

    @Insert("insert into user values(#{email},#{password},#{name})")
    int register(User user);
}
