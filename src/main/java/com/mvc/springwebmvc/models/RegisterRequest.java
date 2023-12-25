package com.mvc.springwebmvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class RegisterRequest {
    
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String age;

    private String brithDate;
}
