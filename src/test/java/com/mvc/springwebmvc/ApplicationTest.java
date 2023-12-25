package com.mvc.springwebmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    @Test
    public void testLoginSuccess() throws Exception {
        this.mockMvc.perform(
            post("/auth/login")
            .queryParam("email", "abdillah@gmail.com")
            .queryParam("password", "scret")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("Success Authenticated!"))
        );
    }

    @Test
    public void testLoginFaill() throws Exception {
        this.mockMvc.perform(
            post("/auth/login")
            .queryParam("email", "wrong@gmail.com")
            .queryParam("password", "unkown")
        ).andExpectAll(
            status().isUnauthorized(),
            content().string(Matchers.containsString("Unauthorized!"))
        );
    }
}