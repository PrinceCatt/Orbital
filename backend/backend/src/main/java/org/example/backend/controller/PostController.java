package org.example.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.backend.entity.Post;
import org.example.backend.mapper.PostMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post") // baseUrl: http:localhost:8088/post
public class PostController {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/find/user")
    public Result getPostsOfUser(@RequestParam int uid,
                                 @RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Post> posts = postMapper.selectByUid(uid);
        PageInfo<Post> pageInfo = new PageInfo<>(posts);
        return Result.ok().data("pageInfo", pageInfo);
    }


     // Find all posts (by page)
    @GetMapping("/find")
    public Result findPosts(@RequestParam(defaultValue = "1") int pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Post> posts = postMapper.selectList(null);
        PageInfo<Post> pageInfo = new PageInfo<>(posts);
        return Result.ok().data("pageInfo", pageInfo);
    }

    // Find post by postId
    @GetMapping("/find/id")
    public Result findPost(int postId) {
        Post post = postMapper.selectById(postId);
        String name = userMapper.findNameByUid(post.getUid());
        post.setAuthor(name);
        return Result.ok().data("post", post);
    }

    @GetMapping("/section")
    public Result findPostsBySection(@RequestParam String section,
                                     @RequestParam(defaultValue = "1") int pageNum,
                                     @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Post> posts = postMapper.selectSectionWithAuthorName(section);
        PageInfo<Post> pageInfo = new PageInfo<>(posts);
        return Result.ok().data("pageInfo",pageInfo);
    }


}


