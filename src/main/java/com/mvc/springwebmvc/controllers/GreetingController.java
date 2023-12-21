package com.mvc.springwebmvc.controllers;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.springwebmvc.services.GreetingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller @AllArgsConstructor
public class GreetingController {
 
    private final GreetingService greetingService;

    // endpoin ini haya bisa di akses dengan HTTP Method GET
    @RequestMapping(path = "/greet", method = RequestMethod.GET)
    public void greeting(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(this.greetingService.greet(request.getParameter("name")));
    }
}
