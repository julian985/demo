package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class IndexController {
    @Autowired
    UserService userService;

    @RequestMapping("/doLogin")
    String do_login(HttpServletRequest request) {
        final String name = request.getParameter("name");
        final String password = request.getParameter("password");
        User user = new User(null, name, password, null);
        final Response login = userService.login(user);
        if (StrUtil.equals(login.getMessage(), "success")) {
            final String token = JWTUtil.getToken(new HashMap<>(), 60 * 1000 * 60 * 24 * 7);
            request.getSession().setAttribute("token", token);
            return "redirect:index.html";
        }
        return "redirect:login.html";
    }

    @GetMapping({"/login"})
    String to_login() {
        return "login.html";
    }
}
