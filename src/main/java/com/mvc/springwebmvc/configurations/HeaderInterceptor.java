package com.mvc.springwebmvc.configurations;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HeaderInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-API-TOKEN");
        if(Objects.isNull(token) && checToken(token)) {
            response.sendRedirect("/login");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean checToken(String token) {
        if(!Objects.isNull(token) && !token.startsWith("AUTH-")) return false;
        else
        return true;
    }
}
