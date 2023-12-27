package com.mvc.springwebmvc.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvc.springwebmvc.models.UserRequest;
import com.mvc.springwebmvc.models.UserResponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller @RequestMapping(path = "/user")
public class UserController {
    
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy mm dd");
    
    private final ObjectMapper objectMapper;

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

    @GetMapping(path = "/date") @ResponseBody
    public String dateConverter(@RequestParam(value = "date", required = false) Date date, HttpServletResponse response) throws IOException {
        return "Date : "+ simpleDateFormat.format(date);
    }

    @GetMapping(path = "/{userId}/addresses/{addressId}") @ResponseBody
    public String address(@PathVariable(value = "userId") String userId, @PathVariable(value = "addressId") String addressId){
        return "userID : ".concat(userId+"\n").concat("addressId : ").concat(addressId);
    }

    @PostMapping(path = "/reqJson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) @ResponseBody
    public String reqJson(@RequestBody String userRequest) throws JsonMappingException, JsonProcessingException {
        UserRequest userReq = this.objectMapper.readValue(userRequest, UserRequest.class);
        return this.objectMapper.writeValueAsString(UserResponse.builder()
            .name(userReq.getName())
            .email(userReq.getEmail())
            .date(this.simpleDateFormat.format(new Date(System.currentTimeMillis())))
            .build());
    }

    @DeleteMapping(path = "/{id}") @ResponseStatus(code = HttpStatus.ACCEPTED) @ResponseBody
    public String delete(@PathVariable(value = "id") String id) {
        return "Success Deleted!";
    }

    @PostMapping(path = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@RequestBody @Valid UserRequest request) throws JsonMappingException, JsonProcessingException {
        UserResponse response = this.objectMapper.readValue(this.objectMapper.writeValueAsString(request), UserResponse.class);
        response.setDate("12-12-2023");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/bindingResult")
    public ResponseEntity<?> bindingResult(@RequestBody @Valid UserRequest userRequest, BindingResult bindingResult){
       List<String> errors = bindingResult.getFieldErrors().stream().map(err -> {
               return err.getField().concat(" : ").concat(err.getDefaultMessage());
              }).collect(Collectors.toList());
         return ResponseEntity.badRequest().body(errors);
    }
}