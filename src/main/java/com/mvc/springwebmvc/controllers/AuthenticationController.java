package com.mvc.springwebmvc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller @RequestMapping(path = "/auth")
public class AuthenticationController {
    
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        if (email.equals("abdillah@gmail.com") && password.equals("scret"))
            return ResponseEntity.status(HttpStatus.OK).body("Success Authenticated!");
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized!");
    }
}
