package org.example.backend.service.impl;

import org.example.backend.entity.Post;
import org.example.backend.mapper.PostMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<Post> getPostsByIds(List<Integer> postIds) {

        ArrayList<Post> posts = new ArrayList<>();

        for (Integer postId : postIds) {
            Post post = postMapper.selectById(postId);
            if (post != null) {
                post.setAuthor(userMapper.findNameByUid(post.getUid()));
                posts.add(post);
            }
        }

        return posts;
    }
}
