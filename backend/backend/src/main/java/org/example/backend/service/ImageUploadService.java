package org.example.backend.service;

import org.apache.ibatis.annotations.Param;
import org.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageUploadService")
public class ImageUploadService {
    @Autowired
    private UserMapper userMapper;

    public String imageUpload(String avatarPath, String email) {
        int result = userMapper.updateAvatar(email, avatarPath);
        if (result > 0) {
            return "Upload success";
        }
        return "Upload failed";
    }
}
