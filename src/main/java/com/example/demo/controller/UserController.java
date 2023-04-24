package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.example.demo.util.JWTUtil;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody User user, HttpServletRequest request) {
        final Response login = userService.login(user);
        if (StrUtil.equals(login.getMessage(), "success")) {
            final String token = JWTUtil.getToken(new HashMap<>(), 60* 1000 * 60 * 24 * 7);
            request.getSession().setAttribute("token", token);
        }
        return login;
    }

    @PutMapping("/update")
    Response update(@RequestBody User user) {
        userService.save(user);
        return new Response("success", null);

    }

    @GetMapping("/get/{id}")
    Response getById(@PathVariable("id") Long id) {
        return new Response("success", userService.findById(id));
    }

    @GetMapping("/getAll")
    Response getAll() {
        return new Response("success", userService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    Response deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new Response("success", null);
    }


}
