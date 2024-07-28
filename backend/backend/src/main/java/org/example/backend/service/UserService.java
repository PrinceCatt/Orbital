package org.example.backend.service;

import org.example.backend.entity.Post;
import org.example.backend.entity.User;

import java.util.List;

public interface UserService {

    public List<Integer> convertStringToIntegerList(String history);

    public List<Post> findHistoryOfUser(User user);

    public int addHistoryByUser(int postId, User user);
}
