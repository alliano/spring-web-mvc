package com.mvc.springwebmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {

    private @Autowired MockMvc mockMvc;

    @Test
    public void interceptorTestFaill() throws Exception{
        this.mockMvc.perform(
            get("/home/current")
        ).andExpectAll(
            status().is3xxRedirection()
        );
    }


    @Test
    public void testInterceptorSuccess() throws Exception{
        this.mockMvc.perform(
            get("/home/current")
            .header("username", "Abdillah")
            .header("X-API-TOKEN", "AUTH-d39hd732dh27ydr723t")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("Abdillah"))
        );
    }
}
