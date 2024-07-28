package org.example.backend.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.backend.entity.Post;
import org.example.backend.entity.User;
import org.example.backend.mapper.PostMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.service.CommentService;
import org.example.backend.service.ImageUploadService;
import org.example.backend.service.PostService;
import org.example.backend.service.UserService;
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
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;


    // For login function
    @PostMapping("/login")
    //json: {email,password}
    //As the frontEnd sending data in json format, @RequestBody is needed
    public Result login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        User ActualUser = userMapper.findByEmail(email);
        if (ActualUser == null) {
            return Result.error().message("This email has not been registered");
        }

        if (!password.equals(ActualUser.getPassword())) {
            return Result.error().message("Please enter the correct email and password");
        }
        String token = JwtUtils.generateToken(email);
        return Result.ok().data("token", token);
    }

    // To get user info
    @GetMapping("/info") // "token:xxx“
    public Result info(String token) {
        if (!JwtUtils.validateToken(token)) {
            return Result.error().message("Invalid token");
        }
        // find user's email by token
        String email = JwtUtils.getClaimsByToken(token).getSubject();

        // find user's information
        User user = userMapper.findByEmail(email);
        int id = user.getId();
        String name = user.getName();
        String avatarPath = user.getAvatarPath();
        return Result.ok().data("email", email).data("name", name).data("avatar", avatarPath).data("id", id);
    }

    // For logout function
    @PostMapping("/logout") // "remove token and all, see details in front end "
    public Result logout() {
        return Result.ok();
    }

    // For new user to register
    //json:{email,password,name}
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String email = user.getEmail();
        if (userMapper.findByEmail(email) != null) {
            return Result.error().message("This email has been registered");
        }

        if (user.getPassword().length() < 6){
            return Result.error().message("Password must be at least 6 characters long");
        }

        int result = userMapper.insert(user);
        if (result > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    // For user to change name
    @PostMapping("/updateName")
    public Result updateName(String name, HttpServletRequest request) {

        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);
        if (user.getName().equals(name)) {
            return Result.error().message("Please enter a new name");
        }

        int result = userMapper.updateName(email, name);
        if (result > 0) {
            return Result.ok();
        } else {
            return Result.error().message("Invalid update");
        }
    }

    // For user to change password
    @PostMapping("/changePassword")
    public Result changePassword(String oldPassword, String newPassword, HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);
        if (!user.getPassword().equals(oldPassword)) {
            return Result.error().message("Please enter the correct old password");
        } else if (newPassword.length() < 6) {
            return Result.error().message("Password must be at least 6 characters long");
        } else {
            int result = userMapper.changePassword(email, newPassword);
            if (result > 0) {
                return Result.ok();
            }
            return Result.error().message("Invalid update");
        }
    }

    // For user to update avatar
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ImageUploadService imageUploadService;

    @Value("${web.upload-path}")
    private String path;

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) {
        if (avatar.isEmpty()) {
            return Result.error().message("Avatar should not be empty");
        }
        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);
        String avatarPath = user.getAvatarPath();

        if (avatarPath != null) {
            File tempFile = new File(avatarPath);
            String oldFileName = tempFile.getName();

            String realAvatarPath = path + File.separator + oldFileName;
            File file = new File(realAvatarPath);

            if (file.exists()) {
                boolean deleted = file.delete();
                System.out.println("Avatar deleted = " + deleted);
            }
        }

        String contentType = avatar.getContentType();
        String fileName = avatar.getOriginalFilename();
        logger.info("Original fine content type is {} name is {}", contentType, fileName);
        String newRandomFileName = FileUtils.generateRandomFileName(fileName);
        try {
            FileUtils.upload(avatar, path, newRandomFileName);
        } catch (Exception e) {
            System.out.println("Upload avatar exception: " + e.getMessage());
        }

        String resourceHandlerPath = "http://114.55.89.49/user/avatar/";

        int result = imageUploadService.imageUpload(resourceHandlerPath + newRandomFileName, email);
        if (result > 0) {
            return Result.ok().message("Avatar updated successfully. Path stored.");
        }
        return Result.error().message("Avatar update failed");
    }

    // To get the user's own posts
    @GetMapping("/post")
    public Result findPostsOfUser(HttpServletRequest request,
                                  @RequestParam(defaultValue = "1") int pageNum,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);
        int uid = user.getId();

        PageHelper.startPage(pageNum, pageSize);
        List<Post> posts = postMapper.selectByUid(uid);
        PageInfo<Post> pageInfo = new PageInfo<>(posts);
        return Result.ok().data("pageInfo", pageInfo);
    }

    // For user to create one post of his own
    @PostMapping("/post/new")
    public Result add(@RequestBody Post post, HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        int uid = userMapper.findByEmail(email).getId();
        post.setUid(uid);

        int result = postMapper.insert(post);
        if (result > 0) {
            return Result.ok();
        }
        return Result.error().message("Post add failed");
    }

    // For user to delete one post of his own
    @PostMapping("/post/delete")
    public Result delete(@RequestBody Integer postId, HttpServletRequest request) {
        // check whether this is the user's own post
        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);
        int uid = user.getId();

        System.out.println(uid);
        System.out.println(postId);

        int postUid = postMapper.selectById(postId).getUid();
        if (postUid != uid) {
            return Result.error().message("Post delete failed. You are not allowed to delete others' posts");
        }

        int result0 = postMapper.deleteById(postId);
        commentService.deleteAllCommentsToPost(postId);
        if(result0 > 0){
            return Result.ok();
        }
        return Result.error().message("Post delete failed");
    }

    @PostMapping("/post/update")
    public Result update(@RequestBody Post post, HttpServletRequest request) {
        // check whether this is the user's own post
        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);
        int uid = user.getId();
        int id = post.getId();
        int postUid = postMapper.selectById(id).getUid();
        if (postUid != uid) {
            return Result.error().message("Post update failed. You are not allowed to update others' posts");
        }

        int result = postMapper.updatePost(post.getId(), post.getTitle(), post.getContent(), post.getTime());
        if (result > 0) {
            return Result.ok();
        }
        return Result.error().message("Post update failed");
    }

    @GetMapping("/post/getHistory")
    public Result getHistory(@RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "20") int pageSize,
                             HttpServletRequest request) {

        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.findByEmail(email);

        PageHelper.startPage(pageNum, pageSize);
        List<Post> postHistory = userService.findHistoryOfUser(user);
        PageInfo<Post> pageInfo = new PageInfo<>(postHistory);

        return Result.ok().data("pageInfo", pageInfo);
    }
}