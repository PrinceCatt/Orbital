package org.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    @GetMapping("/user/like")
    public String hello() {
        return "Hello World";
    }
}
