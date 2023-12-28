package com.mvc.springwebmvc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvc.springwebmvc.models.RegisterRequest;
import com.mvc.springwebmvc.models.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller @RequestMapping(path = "/auth") @AllArgsConstructor
public class AuthenticationController {
    
    private final ObjectMapper objectMapper;


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

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> register(@ModelAttribute RegisterRequest registerRequest) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.objectMapper.writeValueAsString(registerRequest));
    }

    @GetMapping(path = "/session", produces = MediaType.APPLICATION_JSON_VALUE) @ResponseBody
    public String createSession(HttpServletRequest request) {
        User user = User.builder()
                    .username("Abdillah")
                    .role("user")
                    .build();
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        return "success create session...";
    }

    @GetMapping(path = "/session/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSession(@SessionAttribute(value = "user") User user) {
        return ResponseEntity.ok().body(user);
    }
}


