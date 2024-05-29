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

        String token = JwtUtils.generateToken((user.getUsername()));
        return Result.ok().data("token", token);
    }

    @GetMapping("/info") // "token:xxx“
    public Result info(String token){
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        String url = "https://i0.hdslb.com/bfs/new_dyn/0541977156360c6862b78b2059738c624305299.jpg@1036w_!web-dynamic.webp";
        return Result.ok().data("name", username).data("avatar", url);
    }

    @PostMapping("/logout") // "token:xxx"
    public Result logout(){ return Result.ok(); }

}
