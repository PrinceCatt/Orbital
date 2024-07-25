package org.example.backend.service.impl;

import org.example.backend.entity.Post;
import org.example.backend.mapper.PostMapper;
import org.example.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostMapper postMapper;

    @Override
    public List<Post> getPostsByIds(List<Integer> postIds) {
        ArrayList<Post> posts = new ArrayList<Post>();

        for (Integer postId : postIds) {
            Post post = postMapper.selectById(postId);
            posts.add(post);
        }

        return posts;
    }
}
