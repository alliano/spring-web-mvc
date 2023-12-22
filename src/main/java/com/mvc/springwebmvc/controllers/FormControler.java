package com.mvc.springwebmvc.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FormControler {
    
    @ResponseBody
    @GetMapping(path = "/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String form(@RequestParam(value = "data") String data){
        return data;
    }

    @GetMapping(path = "/html/hello", produces = MediaType.TEXT_HTML_VALUE) @ResponseBody
    public String helloHtml(@RequestParam(value = "name") String name) {
        return """
                <html>
                    <body>
                        <h1>Hello My name is $name</h1>
                    </body>
                </html>
                """.replace("$name", name);
    }

    @PostMapping(path = "/auth") @ResponseBody
    public String requestHeader(@RequestHeader(value = "TOKEN-API", required = true) String token) {
        return token;
    }
}
