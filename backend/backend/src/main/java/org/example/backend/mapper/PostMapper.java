package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.backend.entity.Post;
import org.example.backend.entity.User;


import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    @Select("select * from t_post where uid = #{uid}")
    List<Post> selectByUid(Integer uid);

    @Delete("delete from t_post where id = #{id}")
    int deleteById(Integer id);

    @Select("select * from t_post where id = #{id}")
    Post selectById(Integer id);

    @Select("select * from t_post where section = #{section}")
    List<Post> selectBySection(String section);

    @Select("select * from t_post where section = #{section}")
    @Results(
            {
                    @Result(column = "id",property = "id"),
                    @Result(column = "section",property = "section"),
                    @Result(column = "title",property = "title"),
                    @Result(column = "content",property = "content"),
                    @Result(column = "time",property = "time"),
                    @Result(column = "uid",property = "author",javaType = String.class,
                            one = @One(select = "org.example.backend.mapper.UserMapper.findNameByUid")
                    )
            }
    )
    List<Post> selectSectionWithAuthorName(String section);
}
