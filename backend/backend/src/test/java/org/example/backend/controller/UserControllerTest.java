package org.example.backend.controller;

import lombok.SneakyThrows;
import org.example.backend.entity.User;
import org.example.backend.service.UserService;
import org.example.backend.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

//    @Before
//    public void setUp() throws Exception {
//        System.out.println("---------------start---------------");
//        save();
//        get();
//        System.out.println("================end================");
//    }
    @SneakyThrows
    @Test
    public void loginTest() throws Exception {
        String email = "test@test.com";
        User user = new User();
        user.setName("test user");
        user.setEmail(email);
        user.setPassword("password");

        Mockito.when(userService.userLogin(email)).thenReturn(user);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user/login"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(result -> System.out.println("请求响应：" + result.getResponse().getContentAsString()));

        String expect = "{\"name\":\"test user\",\"email\":\"test@test.com\"}";
        resultActions.andExpect(MockMvcResultMatchers.content().json(expect, true));


    }
}