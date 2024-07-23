package org.example.backend;

import org.example.backend.controller.CommentControllerTest;
import org.example.backend.controller.UserControllerTest;
import org.example.backend.utils.RedisKeyUtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        userControllerTest.login();
    }
}
