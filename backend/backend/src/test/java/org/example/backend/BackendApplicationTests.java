package org.example.backend;

import org.example.backend.controller.CommentControllerTest;
import org.example.backend.controller.UserControllerTest;
import org.example.backend.utils.RedisKeyUtilsTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BackendApplicationTests {

    private RedisKeyUtilsTest redisKeyUtilsTest;
    private CommentControllerTest commentControllerTest;
    private UserControllerTest userControllerTest;

    @Test
    void contextLoads() {

    }

    @Test
    void testRKU() {
        redisKeyUtilsTest = new RedisKeyUtilsTest();
        redisKeyUtilsTest.getLikedKeyTest();
    }

    @Test
    void testCommentController() throws Exception {

    }

    @Test
    void testUserController() throws Exception {
        userControllerTest = new UserControllerTest();
        userControllerTest.loginTest();
    }
}
