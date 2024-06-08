package org.example.backend.service;

import org.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageUploadService")
public class ImageUploadService {
    @Autowired
    private UserMapper userMapper;

    public int imageUpload(String avatarPath, String email) {
        return userMapper.updateAvatar(email, avatarPath);
    }
}
