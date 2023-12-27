package com.mvc.springwebmvc.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageCustomController implements ErrorController {
    
    @RequestMapping(path = "/error")
    public ResponseEntity<?> error(HttpServletRequest request) throws IOException {
        Integer statusCode = (Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = (String)request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        File file = ResourceUtils.getFile("classpath:pages/errorPage.html");
        String html = new String(Files.readAllBytes(Path.of(file.getPath())));
        html.replace("$StatusCode", statusCode.toString());
        html.replace("$Message", errorMessage);
        return ResponseEntity.status(statusCode).body(html);
    }
}
