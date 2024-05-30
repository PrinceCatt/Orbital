package org.example.backend.Controller;


import org.example.backend.Entity.User;
import org.example.backend.Utils.JwtUtils;
import org.example.backend.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    //json: {id,username,password,email}
    //As the frontEnd sending data in json format, @RequestBody is needed
    public Result login(@RequestBody User user){
        if (user.getEmail() == null || user.getPassword() == null){
            return Result.error();
        }
        String token = JwtUtils.generateToken((user.getEmail()));
        return Result.ok().data("token", token);
    }

    @GetMapping("/info") // "token:xxx“
    public Result info(String token){
        // to be amended to connect to database
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        String url = "https://i0.hdslb.com/bfs/new_dyn/0541977156360c6862b78b2059738c624305299.jpg@1036w_!web-dynamic.webp";
        return Result.ok().data("email", email).data("avatar", url);
    }

    @PostMapping("/logout") // "token:xxx"
    public Result logout(){ return Result.ok(); }

}
