package org.example.backend.controller;

import org.example.backend.entity.User;
import org.example.backend.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.testng.AssertJUnit.assertEquals;

@RestController
@RequestMapping("/test")
public class TestController {
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/user")
    public Result userTest() {
        User user = new User();
        user.setEmail("mayitao@");
        user.setPassword("1111");
        user.setName("myt");

       Result result  = restTemplate.postForObject("http://localhost:8088/user/login", user, Result.class);
       assertEquals(result.getCode(), 20000);
       assertEquals(result.getData(), "{\"email\":\"mayitao@\",\"password\":\"1111\"}");

       return result;
    }

    @GetMapping("")
    public Result commentTest() {}

    @GetMapping("")
    public Result messageTest() {}

    @GetMapping("")
    public Result postTest() {}

    @GetMapping("")
    public Result redisTest() {}
}
