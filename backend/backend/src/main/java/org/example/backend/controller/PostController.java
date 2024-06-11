package org.example.backend.controller;

import org.example.backend.entity.Post;
import org.example.backend.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/post") // baseUrl: http:localhost:8088/post
public class PostController {

    @Autowired
    private PostMapper postMapper;

    @GetMapping
    public List<Post> getPostsOfUser(@RequestParam int uid) {
        return postMapper.selectByUid(uid);
    }


}
