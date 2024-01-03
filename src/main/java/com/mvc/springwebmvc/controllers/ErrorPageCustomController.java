package com.mvc.springwebmvc.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

@Controller
public class ErrorPageCustomController implements ErrorController {
    
    @RequestMapping(path = "/error") @SneakyThrows
    public ResponseEntity<?> error(HttpServletRequest request) {
        Optional<Object> statusCode = Optional.ofNullable(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        String errorMessage = (String)request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        File file = ResourceUtils.getFile("classpath:pages/errorPage.html");
        String raw = new String(Files.readAllBytes(Path.of(file.getPath())));
        String html = raw.replace("$StatusCode", statusCode.get().toString()).replace("$Message", errorMessage);
        return ResponseEntity.status((Integer)statusCode.get()).body(html);
    }
}
