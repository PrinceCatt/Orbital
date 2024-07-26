package org.example.backend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.example.backend.entity.Post;
import org.example.backend.entity.User;
import org.example.backend.mapper.PostMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.service.UserService;

import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    PostMapper postMapper;

    @Override
    public List<Integer> convertStringToIntegerList(String history) {

        if (!history.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(history);
            return jsonArray.toJavaList(Integer.class);
        }
        return new ArrayList<>();
    }

    @Override
    public int addHistoryByUser(int postId, User user) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return 0;
        }
        String history = user.getHistory();

        //convert history from JSON String to JSON array, create a new array with +1 length, if history != null
        List<Integer> converted = convertStringToIntegerList(history);

        //check if the new postId is repeatedly called, if yes, replace the old one
        for (Integer i : converted) {
            if (i == postId) {
                converted.remove(i);
                break;
            }
        }
        converted.add(0, postId);

        //convert the array back to JSON
        JSONArray resultJsonArray = JSONArray.parseArray(JSON.toJSONString(converted));
        String result = resultJsonArray.toString();

        //update user
        user.setHistory(result);
        return userMapper.updateById(user);
    }
}
