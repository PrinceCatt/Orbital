package org.example.backend.service;

import org.example.backend.entity.Comment;
import java.util.List;


public interface CommentService {
    // Query comment list
    List<Comment> listComments(int postId);

    // Save new comment
    int saveComment(Comment comment);
}
