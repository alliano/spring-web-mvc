package com.mvc.springwebmvc.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller @RequestMapping(path = "/view")
public class ViewController {
    
    @GetMapping(path = "/home/{name}")
    public ModelAndView home(@PathVariable(value = "name") String name) {
        // parameter constractor yang pertama adalah nama file template kita, dan parameter ke 2 adalah key dan value data yang dikirimkan ke template
        return new ModelAndView("home", Map.of("title", "Belajar Mustache", "name", name, "content", "Hallo, ".concat(name).concat(" selamat belajar Srping web mvc dan mustache")));
    }
}
