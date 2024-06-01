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
    //json: {id,username,password,email}
    //As the frontEnd sending data in json format, @RequestBody is needed
    public Result login(@RequestBody User user){

        // need to be amended to compare to the email and password in database
        if (user.getEmail() == null || user.getPassword() == null){
            return Result.error();
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
        return Result.ok().data("email", email).data("avatar", url).data("name", name); // more information to be added
    }

    @PostMapping("/logout") // "remove token and all, see details in frontEnd "
    public Result logout(){ return Result.ok(); }

}
