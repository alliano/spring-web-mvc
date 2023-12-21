package com.mvc.springwebmvc.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletResponse;

@Controller @RequestMapping(path = "/user")
public class UserController {

    @GetMapping(path = "/hello")
    public void helloUser(HttpServletResponse response) throws IOException {
        response.getWriter().println("Hello User...");
    }

    @PostMapping(path = "/save")
    public void save(HttpServletResponse response) throws IOException{
        response.getWriter().println("SAVING.......");
    }
}
