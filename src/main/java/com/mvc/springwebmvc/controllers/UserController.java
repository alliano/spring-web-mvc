package com.mvc.springwebmvc.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletResponse;

@Controller @RequestMapping(path = "/user")
public class UserController {
    
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy mm dd");
    
    @GetMapping(path = "/hello")
    public void helloUser(HttpServletResponse response) throws IOException {
        response.getWriter().println("Hello User...");
    }
    
    @PostMapping(path = "/save")
    public void save(HttpServletResponse response) throws IOException{
        response.getWriter().println("SAVING.......");
    }
    
    @GetMapping(path = "/test")
    public void test(@RequestParam(value = "name", required = false) String name, HttpServletResponse response) throws IOException {
        response.getWriter().println(name);
    }

    @GetMapping(path = "/date")
    public void dateConverter(@RequestParam(value = "date", required = false) Date date, HttpServletResponse response) throws IOException {
        response.getWriter().println("Date : "+ simpleDateFormat.format(date));
    }
}