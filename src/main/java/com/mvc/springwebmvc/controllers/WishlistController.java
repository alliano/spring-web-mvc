package com.mvc.springwebmvc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishlistController {
    
    private final List<String> wishlist = new ArrayList<String>();

    @GetMapping(path = "/wishlist")
    public List<String> addTodo(@RequestParam(value = "wishlist", required = true) String wishlist) {
        this.wishlist.add(wishlist);
        return List.of(wishlist);
    }

    @GetMapping(path = "/wishlists")
    public List<String> getAllTodo() {
        return this.wishlist;
    }
}
