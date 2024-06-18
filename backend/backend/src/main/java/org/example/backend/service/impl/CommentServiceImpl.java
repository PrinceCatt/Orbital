package org.example.backend.service.impl;

import org.example.backend.entity.Comment;
import org.example.backend.entity.User;
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
    private List<Comment> tempReplies = new ArrayList<>();


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
            List<Comment> childComments = commentMapper.findByParentCommentId(id);
            // query for child comments
            combineChildren(childComments, name);
            comment.setReplyComments(tempReplies);
            tempReplies = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(List<Comment> childComments, String parentName) {
        // check if there is first level child replies
        if (!childComments.isEmpty()) {
            // iterate to find child comments' id
            for (Comment childcomment : childComments) {
                int uid = childcomment.getUid();
                String name = userMapper.findNameByUid(uid);
                childcomment.setAuthorName(name);
                String avatarPath = userMapper.findAvatarPathByUid(uid);
                childcomment.setAvatar(avatarPath);
                childcomment.setParentCommentAuthor(parentName);
                tempReplies.add(childcomment);
                int childId = childcomment.getId();
                recursively(childId, name);
            }
        }
    }

    private void recursively(int childId, String parentName) {
        // Find second level child comments with the first level comments' id
        List<Comment> replayComments = commentMapper.findByChildCommentId(childId);

        if (!replayComments.isEmpty()) {
            for (Comment replayComment : replayComments) {
                int uid = replayComment.getUid();
                String name = userMapper.findNameByUid(uid);
                replayComment.setAuthorName(name);
                String avatarPath = userMapper.findAvatarPathByUid(uid);
                replayComment.setParentCommentAuthor(parentName);
                replayComment.setAvatar(avatarPath);
                tempReplies.add(replayComment);
                int replayId = replayComment.getId();
                recursively(replayId, name);
            }
        }
    }



    @Override
    public int saveComment(Comment comment) {
        return commentMapper.insert(comment);
    }
}
