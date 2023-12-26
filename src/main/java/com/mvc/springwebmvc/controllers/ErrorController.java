package com.mvc.springwebmvc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorController {
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Violation Exception : "+methodArgumentNotValidException.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> constraintVionationException(ConstraintViolationException constraintViolationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Violation Exception : "+constraintViolationException.getMessage());
    }
}
