package com.mvc.springwebmvc.services;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String greet(String name) {
       if(name == null) return "Assalamuallaikum Brouther...";
       else
       return "Assalamuallaikum ya ".concat(name);
    } 
}
