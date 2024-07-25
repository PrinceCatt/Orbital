package org.example.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.Comment;

import java.util.List;


@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    // Find the most parents' comments of a post
    @Select("select * from t_comment where postId = #{postId} and parentCommentId = #{parentCommentId}")
    List<Comment> findByPostIdAndParentCommentIdNull(@Param("postId") int postId, @Param("parentCommentId") int parentCommentId);

    @Select("select * from t_comment where parentCommentId = #{id}")
    List<Comment> findByParentCommentId(Integer id);

    @Select("select * from t_comment where parentCommentId = #{childId}")
    List<Comment> findByChildCommentId(Integer childId);

}
