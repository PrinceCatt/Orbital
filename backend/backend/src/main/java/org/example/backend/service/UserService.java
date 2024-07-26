package org.example.backend.service;

import org.example.backend.entity.User;

import java.util.List;

public interface UserService {

    public List<Integer> convertStringToIntegerList(String history);

    public int addHistoryByUser(int postId, User user);
}
