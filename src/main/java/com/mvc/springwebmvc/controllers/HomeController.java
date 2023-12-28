package com.mvc.springwebmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller @AllArgsConstructor @RequestMapping(path = "/home")
public class HomeController {

    @GetMapping(path = "/current")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String username = request.getHeader("username");
        return ResponseEntity.ok().body("Hello "+username);
    }
    
}
