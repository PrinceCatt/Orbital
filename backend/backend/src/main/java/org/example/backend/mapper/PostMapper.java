package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.Post;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    @Select("select * from t_post where uid = #{uid}")
    List<Post> selectByUid(Integer uid);

    @Delete("delete from t_post where id = #{id}")
    int deleteById(Integer id);

    @Select("select * from t_post where id = #{id}")
    Post selectById(Integer id);


}
