package org.example.backend;

import org.example.backend.utils.RedisKeyUtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

class BackendApplicationTests {

    private RedisKeyUtilsTest redisKeyUtilsTest;


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


}
