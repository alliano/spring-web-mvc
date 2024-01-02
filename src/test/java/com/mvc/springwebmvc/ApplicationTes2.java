package com.mvc.springwebmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvc.springwebmvc.models.AuthenticationRequest;
import com.mvc.springwebmvc.openFeign.WishlistFeign;

@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {

    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

    private @Autowired WishlistFeign wishlistFeign;

    private @Autowired RestTemplate restTemplate;

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

    @Test
    public void testMethodArgumentResolver() throws Exception{
        this.mockMvc.perform(
            get("/auth/resource")
            .header("AUTH-TOKEN", "AUTH-ceihfr8y230r082ud023ud802u38")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            AuthenticationRequest response = this.objectMapper.readValue(result.getResponse().getContentAsString(), AuthenticationRequest.class);
            Assertions.assertNotNull(response);
            Assertions.assertEquals("Abdillah", response.getUsername());
        });
    }

    @Test
    public void testMethodArgumnentResolverFaill() throws Exception{
        this.mockMvc.perform(
            get("/auth/resource")
        ).andExpectAll(
            status().isUnauthorized()
        );
    }

    @Test
    public void testView() throws Exception{
        this.mockMvc.perform(
            get("/view/home/abdillah")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("abdillah")),
            content().string(Matchers.containsString("selamat belajar Srping web mvc dan mustache"))
        );
    }

    @Test
    public void testRedirectModelAndView() throws Exception{
        this.mockMvc.perform(
            get("/view/hello")
        ).andExpectAll(
            status().is3xxRedirection()
        );
    }

    @Test
    public void testWishlistController() throws Exception{
        this.mockMvc.perform(
            get("/wishlist")
            .queryParam("wishlist", "Haji tahun 2024")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            List<String> response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>(){});
            Assertions.assertNotNull(response);
            Assertions.assertEquals("Haji tahun 2024", response.get(0));
        });
    }

    @Test
    public void wishlistFeign(){
        ResponseEntity<List<String>> wishlist = this.wishlistFeign.addWishlist("Berangkat haji tahun 2024");
        Assertions.assertNotNull(wishlist);
        Assertions.assertEquals(HttpStatus.OK, wishlist.getStatusCode());
        List<String> body = wishlist.getBody();
        Assertions.assertEquals("Berangkat haji tahun 2024", body.get(0));
    }

    @Test
    public void testRestTemplate(){
        String url = "http://localhost:8080/wishlist?wishlist=jalan%20ke%20swish";
        RequestEntity<Object> request = new RequestEntity<>(HttpMethod.GET, URI.create(url));
        ResponseEntity<List<String>> response = this.restTemplate.exchange(request, new ParameterizedTypeReference<List<String>>(){});
        Assertions.assertNotNull(response);
        Assertions.assertEquals("jalan ke swish", response.getBody().get(0));
    }

    @Test
    public void testRest(){
        String url = "http://localhost:8080/testRest";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        LinkedMultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("name", "Abdillah Alli");
        RequestEntity<LinkedMultiValueMap<String,Object>> request = new RequestEntity<>(form, httpHeaders, HttpMethod.POST, URI.create(url));
        ResponseEntity<List<String>> respose = this.restTemplate.exchange(request, new ParameterizedTypeReference<List<String>>(){});
        Assertions.assertNotNull(respose);
        Assertions.assertEquals("Abdillah Alli", respose.getBody().get(0));
    }

    // @Test
    // public void testRestTemplate(){
    //     String url = "http://localhost:8080/wishlist";
    //     HttpHeaders httpHeaders = new HttpHeaders();
    //     httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    //     MultiValueMap<String,Object> form = new LinkedMultiValueMap<String, Object>();
    //     form.add("wishlist", "Liburan ke swish");
    //     RequestEntity<MultiValueMap<String,Object>> request = new RequestEntity<>(form, httpHeaders, HttpMethod.POST, URI.create(url));
    //     ResponseEntity<List<String>> respose = this.restTemplate.exchange(request, new ParameterizedTypeReference<List<String>>(){});
    //     Assertions.assertEquals(HttpStatus.OK, respose.getStatusCode());
    // }
}
