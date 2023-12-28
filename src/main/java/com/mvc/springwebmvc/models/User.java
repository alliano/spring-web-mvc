package com.mvc.springwebmvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Builder
@Setter @Getter @AllArgsConstructor
public class User {
    
    private String username;

    private String role;
}
