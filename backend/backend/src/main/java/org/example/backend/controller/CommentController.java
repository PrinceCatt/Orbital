package org.example.backend.controller;

import org.example.backend.entity.Comment;
import org.example.backend.entity.User;
import org.example.backend.mapper.CommentMapper;
import org.example.backend.mapper.PostMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.service.CommentService;
import org.example.backend.utils.JwtUtils;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/post/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private PostMapper postMapper;


    @GetMapping("")
    public Result getComments(@RequestParam("postId") int postId) {
        List<Comment> comments = commentService.listComments(postId);
        return Result.ok().data("comments", comments);
    }

    @PostMapping("/new")
    public Result addComment(@RequestBody Comment comment,
                             HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);
        comment.setUid(user.getId());
        comment.setAvatar(user.getAvatarPath());

        int parentId = comment.getParentCommentId();
        if (parentId >= 0) {
            if (commentMapper.selectById(parentId) == null) {
                return Result.error().message("Parent comment does not exist");
            }
        }

        int postId = comment.getPostId();
        if (postMapper.selectById(postId) == null) {
            return Result.error().message("Post to comment on does not exist");
        }

        int result = commentService.saveComment(comment);
        if (result > 0) {
            return Result.ok();
        }
        return Result.error().message("Add comment failed");
    }
}
