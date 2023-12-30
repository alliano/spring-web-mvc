package com.mvc.springwebmvc.controllers;

import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller @RequestMapping(path = "/view")
public class ViewController {
    
    @GetMapping(path = "/home")
    public ModelAndView home(@RequestParam(value = "name", required = false) String name) {
        // parameter constractor yang pertama adalah nama file template kita, dan parameter ke 2 adalah key dan value data yang dikirimkan ke template
        return new ModelAndView("home", Map.of("title", "Belajar Mustache", "name", name, "content", "Hallo, ".concat(name).concat(" selamat belajar Srping web mvc dan mustache")));
    }

    @GetMapping(path = "/hello")
    public ModelAndView test(@RequestParam(value = "name", required = false) String name) {
        if(Objects.isNull(name)) return new ModelAndView("redirect:/view/home?name=tamu");
            else
        return new ModelAndView("hello", Map.of("title", "hello page", "name", name));
    }
}
