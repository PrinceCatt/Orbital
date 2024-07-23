package org.example.backend.service.impl;

import org.example.backend.entity.Comment;
import org.example.backend.mapper.CommentMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    // For storing the set of all child replies
    private List<Comment> childComments = new ArrayList<>();


    @Override
    public List<Comment> listComments(@RequestParam("postId") int postId) {
        List<Comment> comments = commentMapper.findByPostIdAndParentCommentIdNull(postId,-1);
        for (Comment comment : comments) {
            int id = comment.getId();
            int uid = comment.getUid();
            String name = userMapper.findNameByUid(uid);
            String avatarPath = userMapper.findAvatarPathByUid(uid);
            comment.setAuthorName(name);
            comment.setAvatar(avatarPath);

            childComments = commentMapper.findByParentCommentId(id);

            for (Comment childComment : childComments) {
                int cUid = childComment.getUid();
                String cName = userMapper.findNameByUid(cUid);
                String cAvatarPath = userMapper.findAvatarPathByUid(cUid);
                childComment.setAuthorName(cName);
                childComment.setAvatar(cAvatarPath);
            }

            comment.setReplyComments(childComments);
            childComments = new ArrayList<>();
        }
        return comments;
    }



    @Override
    public int saveComment(Comment comment) {

        return commentMapper.insert(comment);
    }
}
