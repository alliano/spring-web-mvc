package com.mvc.springwebmvc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller @RequestMapping(path = "/auth")
public class AuthenticationController {
    
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        if (email.equals("abdillah@gmail.com") && password.equals("scret"))
            return ResponseEntity.status(HttpStatus.OK).body("Success Authenticated!");
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized!");
    }

    @PostMapping(path = "/addCookie")
    public ResponseEntity<?> setCookie(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpServletResponse response) {
        if (username.equals("test") && password.equals("pass")) {
            Cookie cookie = new Cookie("username", username);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.status(HttpStatus.OK).body("Success Authenticated!");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized!");
        }
    }

    @GetMapping(path = "/user")
    public ResponseEntity<?> getCookie(@CookieValue(name = "username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(username);
    }
}


