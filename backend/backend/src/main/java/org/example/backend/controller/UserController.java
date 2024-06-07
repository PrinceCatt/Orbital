package org.example.backend.controller;

import org.apache.ibatis.annotations.Param;
import org.example.backend.entity.User;
import org.example.backend.mapper.UserMapper;
import org.example.backend.service.ImageUploadService;
import org.example.backend.utils.FileUtils;
import org.example.backend.utils.JwtUtils;
import org.example.backend.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


@RestController
@RequestMapping("/user") // baseUrl: http:localhost:8088/user
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    //json: {email,password}
    //As the frontEnd sending data in json format, @RequestBody is needed
    public Result login(@RequestBody User user){
        String email = user.getEmail();
        String password = user.getPassword();

        User ActualUser = userMapper.findByEmail(email);
        if (ActualUser == null){
            return Result.error().message("This email has not been registered");
        }

        if (!password.equals(ActualUser.getPassword())){
            return Result.error().message("Please enter the correct email and password");
        }

        String token = JwtUtils.generateToken((user.getEmail()));
        return Result.ok().data("token", token);
    }

    @GetMapping("/info") // "token:xxx“
    public Result info(String token){
        // find user's email by token
        String email = JwtUtils.getClaimsByToken(token).getSubject();

        // find user's information
        User user = userMapper.findByEmail(email);
        String name = user.getName();
        String avatarPath = user.getAvatarPath();
        return Result.ok().data("email", email).data("name", name).data("avatar", null ); // Avatar path to be changed
    }

    @PostMapping("/logout") // "remove token and all, see details in frontEnd "
    public Result logout(){ return Result.ok(); }

    //json:{email,password,name}
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        String email = user.getEmail();
        if (userMapper.findByEmail(email) != null){
            return Result.error().message("This email has been registered");
        }

        int result = userMapper.insert(user);
        if(result > 0){
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("/updateName")
    public Result updateName(String name, HttpServletRequest request){

        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);
        if(user.getName().equals(name)){
            return Result.error().message("Please enter a new name");
        }
        
        int result = userMapper.updateName(email, name);
        if (result > 0){
            return Result.ok();
        }
        else { return Result.error().message("Invalid update"); }
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ImageUploadService imageUploadService;

    @Value("${web.upload-path}")
    private String path;

    @Value("${server.port}")
    private String port;

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request){
        if (avatar.isEmpty()) {
            return Result.error().message("Avatar should not be empty");
        }
        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();

        String contentType = avatar.getContentType();
        String fileName = avatar.getOriginalFilename();
        logger.info("Original fine content type is {} name is {}", contentType, fileName);
        String newRandomFileName = FileUtils.generateRandomFileName(fileName);
        try {
            FileUtils.upload(avatar, path, newRandomFileName);
        } catch (Exception e) {
            System.out.println("Upload avatar exception: " + e.getMessage());
        }

        return Result.ok().message("Avatar updated successfully. Link stored: " + imageUploadService.imageUpload(path + File.separator + newRandomFileName, email));
    }








}