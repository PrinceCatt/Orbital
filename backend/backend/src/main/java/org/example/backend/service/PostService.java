package org.example.backend.service;

import org.example.backend.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> getPostsByIds(List<Integer> postIds);

}
