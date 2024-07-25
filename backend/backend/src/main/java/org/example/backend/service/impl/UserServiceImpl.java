package org.example.backend.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.example.backend.service.UserService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<Integer> convertStringToIntegerList(String history) {

        if (!history.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(history);
            return jsonArray.toJavaList(Integer.class);
        }
        return new ArrayList<>();
    }
}
