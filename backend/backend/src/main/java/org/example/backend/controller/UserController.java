package org.example.backend.controller;


import org.example.backend.entity.User;
import org.example.backend.mapper.UserMapper;
import org.example.backend.utils.JwtUtils;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
        String url = user.getAvatar();
        return Result.ok().data("email", email).data("name", name).data("avatar", url); // more information to be added
    }

    @PostMapping("/logout") // "remove token and all, see details in frontEnd "
    public Result logout(){ return Result.ok(); }

    @PostMapping("/register")
    public Result register(User user){
        int result = userMapper.insert(user);
        if(result > 0){
            return Result.ok();
        }
        return Result.error();
    }
}
