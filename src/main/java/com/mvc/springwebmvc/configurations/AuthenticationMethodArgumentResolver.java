package com.mvc.springwebmvc.configurations;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import com.mvc.springwebmvc.models.AuthenticationRequest;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthenticationRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = ((HttpServletRequest)webRequest.getNativeRequest());
        String token = servletRequest.getHeader("AUTH-TOKEN");
        if(!Objects.isNull(token)) {
            return AuthenticationRequest.builder()
                .username("Abdillah")
                .role("Admin")
                .build();
        }
        else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
        }
    }
}
