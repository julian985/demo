package com.example.demo.service;

import cn.hutool.core.util.StrUtil;
import com.example.demo.util.JWTUtil;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.respository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {


    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;


    @Test
    @Order(1)
    void save() {
        userRepository.deleteAll();
        User user = new User(1l, "tyson", "123456", new Date());
        userService.save(user);
        Assertions.assertEquals(1, userService.findAll().size());
    }

    @Test
    @Order(2)
    void exist() {
        User user = new User(null, "tyson", "123456", null);
        Assertions.assertEquals(true, userService.exist(user));
    }

    @Test
    @Order(3)
    void findById() {
        final User user1 = userService.findById(1l);
        User user2 = new User(1l, "tyson", "123456", new Date());
        Assertions.assertEquals(user1.getId(), user2.getId());
        Assertions.assertEquals(user1.getName(), user2.getName());
        Assertions.assertEquals(user1.getPassword(), user2.getPassword());
    }

    @Test
    @Order(4)
    void findAll() {
        Assertions.assertEquals(1, userService.findAll().size());
    }

    @Test
    @Order(5)
    void login() {
        final User user = new User(null, "tyson", "123456", null);
        final Response response = userService.login(user);
        Assertions.assertEquals(true, StrUtil.equals(response.getMessage(), "success"));
    }

    @Test
    @Order(6)
    void deleteById() {
        userService.deleteById(1l);
        Assertions.assertEquals(0, userService.findAll().size());
    }

    @Test
    void jwtTest() throws Exception {
        final String token = JWTUtil.getToken(new HashMap<>(), 0);
        Thread.sleep(1000);
        Assertions.assertEquals(false,JWTUtil.verify(token));
    }
}