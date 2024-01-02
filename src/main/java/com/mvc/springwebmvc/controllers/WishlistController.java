package com.mvc.springwebmvc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishlistController {
    
    private final List<String> wishlist = new ArrayList<String>();

    @GetMapping(path = "/wishlist")
    public List<String> addWishlist(@RequestParam(value = "wishlist", required = true) String wishlist) {
        this.wishlist.add(wishlist);
        return List.of(wishlist);
    }

    @GetMapping(path = "/wishlists")
    public List<String> getAllTodo() {
        return this.wishlist;
    }

    @PostMapping(path = "/testRest")
    public List<String> get(@RequestParam(value = "name") String name) {
        return List.of(name);
    }
}
