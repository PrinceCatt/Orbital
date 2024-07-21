package org.example.backend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class RedisKeyUtilsTest {

    @Test
    public void getLikedKeyTest() {
        String testKey = RedisKeyUtils.getLikedKey(1,1);
        assertNotNull(testKey);
        assertEquals(testKey, "1::1");
    }
}