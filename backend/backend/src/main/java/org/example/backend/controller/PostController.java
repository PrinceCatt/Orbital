package org.example.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.backend.entity.Post;
import org.example.backend.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post") // baseUrl: http:localhost:8088/post
public class PostController {

    @Autowired
    private PostMapper postMapper;

    @GetMapping("/find/uid")
    public PageInfo<Post> getPostsOfUser(@RequestParam int uid,
                                         @RequestParam(defaultValue = "1") int pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Post> posts = postMapper.selectByUid(uid);
        return new PageInfo<>(posts);
    }

    // Find all posts (by page)
    @GetMapping("/find")
    public PageInfo<Post> findPosts(@RequestParam(defaultValue = "1") int pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Post> posts = postMapper.selectList(null);
        return new PageInfo<>(posts);
    }

    @GetMapping("/section/find")
    public PageInfo<Post> findPostsBySection(@RequestParam String section,
                                             @RequestParam(defaultValue = "1") int pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Post> posts = postMapper.selectBySection(section);
        return new PageInfo<>(posts);
    }
}


