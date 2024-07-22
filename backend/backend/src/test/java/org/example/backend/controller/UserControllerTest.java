package org.example.backend.controller;

import lombok.SneakyThrows;
import org.example.backend.entity.User;
import org.example.backend.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserMapper userMapper = Mockito.mock(UserMapper.class);

    @Test
    public void loginTest() throws Exception {
        String userJson = "{\"name\":\"testUser\",  \"password\": \"123456\", email\":\"test@gmail.com\"}";
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        user.setName("testUser");

        Mockito.when(userMapper.findByEmail(Mockito.eq("test@gmail.com"))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType("application/json")
                .content(userJson))
                .andExpect(status().isOk());
    }
}