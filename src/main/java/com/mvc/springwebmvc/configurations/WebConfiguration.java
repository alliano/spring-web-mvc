package com.mvc.springwebmvc.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;

@Configuration @AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    
    private final HeaderInterceptor headerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerInterceptor)
            // addPathPatterens() digunakan untuk memberitahu interceptor ini nanti akan digunakan di url mana
            .addPathPatterns("/home/*");
    }
}
