package com.mvc.springwebmvc.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class UserRequest {
    
    @NotBlank // annotation milik bean validation
    private String name;

    @NotBlank @Email // annotation milik bean validation
    private String email;
}
