package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.Post;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    @Select("select * from t_post where uid = #{uid}")
    List<Post> selectByUid(Integer uid);


}
