package org.example.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public List<Post> getPostsOfUser(@RequestParam int uid) {
        return postMapper.selectByUid(uid);
    }

    // Find all posts (by page)
    @GetMapping("/find")
    public IPage findPosts() {
        Page<Post> page = new Page<>(0, 2);
        IPage iPage = postMapper.selectPage(page, null);
        return iPage;
    }

    @GetMapping("/section/find")
    public IPage findByPageInSection(String section) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("section", section);

        Page<Post> page = new Page<>(0, 2);
        IPage ipage = postMapper.selectPage(page, queryWrapper);
        return ipage;
    }

}
