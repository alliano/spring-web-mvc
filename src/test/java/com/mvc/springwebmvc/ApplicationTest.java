package com.mvc.springwebmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvc.springwebmvc.models.RegisterRequest;
import com.mvc.springwebmvc.models.UserRequest;
import com.mvc.springwebmvc.models.UserResponse;

import jakarta.servlet.http.Cookie;

@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

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

    @Test
    public void testCookie() throws Exception {
        this.mockMvc.perform(
            post("/auth/addCookie")
            .queryParam("username", "test")
            .queryParam("password", "pass")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("Success Authenticated!")),
            cookie().value("username", "test")

        );

        this.mockMvc.perform(
            get("/auth/user")
            .cookie(new Cookie("username", "test"))
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("test"))
        );
    }

    @Test
    public void formRequestTest() throws Exception {
        final String firstName = "Alli";
        final String lastName = "Abdillah";
        final String email = "alli@gmail.com";
        final String password = "secret";
        final String age = "20";
        final String brithDate = "4-3-2003";
        this.mockMvc.perform(
            post("/auth/register")
            .param("firstName", firstName)
            .param("lastName", lastName)
            .param("email", email)
            .param("password", password)
            .param("age", age)
            .param("brithDate", brithDate)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpectAll(
            status().isCreated()
        ).andDo(result -> {
            RegisterRequest req = this.objectMapper.readValue(result.getResponse().getContentAsString(), RegisterRequest.class);
            Assertions.assertNotNull(req);
            Assertions.assertNotNull(req.getAge());
            Assertions.assertNotNull(req.getFirstName());
            Assertions.assertNotNull(req.getLastName());
            Assertions.assertNotNull(req.getEmail());
            Assertions.assertNotNull(req.getPassword());
            Assertions.assertNotNull(req.getBrithDate());
            Assertions.assertEquals(firstName, req.getFirstName());
            Assertions.assertEquals(lastName, req.getLastName());
            Assertions.assertEquals(email, req.getEmail());
            Assertions.assertEquals(password, req.getPassword());
            Assertions.assertEquals(brithDate, req.getBrithDate());
            Assertions.assertEquals(age, req.getAge());
        });
    }

    @Test
    public void testJson() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                    .name("Abdillah")
                    .email("abdillah@gmail.com")
                    .build();
        String json = this.objectMapper.writeValueAsString(userRequest);
        this.mockMvc.perform(
            post("/user/get")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            UserResponse response = this.objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
            Assertions.assertNotNull(response);
            Assertions.assertEquals("Abdillah", response.getName());
            Assertions.assertEquals("abdillah@gmail.com", response.getEmail());
            Assertions.assertEquals("12-12-2023", response.getDate());
        });
    }

    @Test
    public void testBeanvalidationFail() throws JsonProcessingException, Exception {
        this.mockMvc.perform(
            post("/user/get")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(new UserRequest()))
        ).andExpectAll(
            status().isBadRequest()
        );
    }

    @Test
    public void testControllerAdvice() throws JsonProcessingException, Exception {
        this.mockMvc.perform(
            post("/user/get")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(new UserRequest()))
        ).andExpectAll(
            status().isBadRequest(),
            content().string(Matchers.containsString("Violation Exception :"))
        );
    }
}
 