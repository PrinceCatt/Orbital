package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where email = #{email}")
    User findByEmail(String email);

}
