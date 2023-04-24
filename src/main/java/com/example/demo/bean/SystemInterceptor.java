package com.example.demo.bean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.demo.entity.Response;
import com.example.demo.util.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SystemInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("token");
            if(StrUtil.isNotEmpty(token)&& !JWTUtil.verify(token)){
                response.getWriter().println(JSONUtil.toJsonStr(new Response("token outTime","null")));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
