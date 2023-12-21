package com.mvc.springwebmvc.mockingtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mvc.springwebmvc.SpringWebMvcApplication;
import com.mvc.springwebmvc.services.GreetingService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringWebMvcApplication.class)
public class GrettinngControllerMockingTest {
    
    @MockBean
    private GreetingService greetingService;

    private @Autowired MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        Mockito.when(this.greetingService.greet(Mockito.anyString())).thenReturn("Hello Guest");

    }

    @Test
    public void testGreting() throws Exception {
        this.mockMvc.perform(
            get("/greet")
            .queryParam("name", "Alliano")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("Hello Guest"))
        );
    } 
}
