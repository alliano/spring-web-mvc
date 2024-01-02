package com.mvc.springwebmvc.openFeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8080/", name = "wishlistFeign")
public interface WishlistFeign {

    @GetMapping(path = "/wishlist")
    public ResponseEntity<List<String>> addWishlist(@RequestParam(value = "wishlist", required = true) String wishlist);
    
}
