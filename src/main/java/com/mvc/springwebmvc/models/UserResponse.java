package com.mvc.springwebmvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class UserResponse {
    
    private String name;

    private String email;

    private String date;
}
