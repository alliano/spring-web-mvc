package com.mvc.springwebmvc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvc.springwebmvc.models.UserRequest;
import com.mvc.springwebmvc.models.UserResponse;
import com.mvc.springwebmvc.services.GreetingService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.UUID;
import org.hamcrest.Matchers;

@AutoConfigureMockMvc // melakukan konfigurasi otomatis MockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	private @Autowired GreetingService gretingService;

	private @Autowired ObjectMapper objectMapper;

	@Test
	public void greetingNoParam() throws Exception {
		this.mockMvc.perform(
			get("/greet")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Assalamuallaikum Brouther"))
		);
	}

	@Test
	public void greetingWithParam() throws Exception {
		String name = "Abdillah";
		this.mockMvc.perform(
			get("/greet")
			.queryParam("name", name)
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Assalamuallaikum ya ".concat(name)))
		);
	}

	@Test
	public void testGreetingService() {
		String name = "Abdillah";
		String greet1 = this.gretingService.greet(null);
		String greet2 = this.gretingService.greet(name);
		Assertions.assertArrayEquals(new String[]{"Assalamuallaikum Brouther...", "Assalamuallaikum ya ".concat(name)}, new String[]{greet1, greet2});
	}

	@Test
	public void testUserController() throws Exception {
		this.mockMvc.perform(
			get("/user/hello")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Hello User..."))
		);

		this.mockMvc.perform(
			post("/user/save")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("SAVING......."))
		);
	}

	@Test
	public void testRequestParam() throws Exception {
		this.mockMvc.perform(
			get("/user/test")
			.queryParam("name", "Abdillah")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Abdillah"))
		);
	}

	@Test
	public void testConvertDateFromReqParam() throws Exception {
		this.mockMvc.perform(
			get("/user/date")
			.queryParam("date", "3-4-2003")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Date : 2003 04 03"))
		);
	}

	@Test
	public void testRequestContentType() throws Exception {
		this.mockMvc.perform(
			get("/form")
			.queryParam("data", "some data")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("some data"))
		);
	}

	@Test
	public void testContentProduceType() throws Exception{
		this.mockMvc.perform(
			get("/html/hello")
			.contentType(MediaType.TEXT_HTML)
			.queryParam("name", "Abdillah")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Hello My name is Abdillah"))
		);
	}

	@Test
	public void testRequestHeader() throws Exception {
		String token = UUID.randomUUID().toString();
		this.mockMvc.perform(
			post("/auth")
			.header("TOKEN-API", token)
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString(token))
		);
	}

	@Test
	public void testPathVariable() throws Exception {
		this.mockMvc.perform(
			get("/user/".concat("alliano/addresses/").concat("addressId"))
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("userID : alliano\naddressId : addressId"))
		);
	}

	@Test
	public void testFileMangerController() throws IOException, Exception {
		this.mockMvc.perform(
			multipart("/file/upload")
			.file(new MockMultipartFile("file", "profile.png", "image/png", getClass().getResourceAsStream("/images/DispatcherServlet-Controller.jpg")))
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.queryParam("name", "Abdillah")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Success upload file"))
		);
	}

	@Test
	public void testReqResJson() throws Exception {
		UserRequest request = UserRequest.builder()
			.name("Abdillah")
			.email("unkown@gmail.com")
			.build();
		
		this.mockMvc.perform(
			post("/user/reqJson")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(request))
		).andExpectAll(
			status().isOk(),
			header().string(HttpHeaders.CONTENT_TYPE, Matchers.containsString(MediaType.APPLICATION_JSON_VALUE))
		).andDo(result -> {
			UserResponse response = this.objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
			Assertions.assertNotNull(response);
			Assertions.assertEquals("Abdillah", response.getName());
			Assertions.assertEquals("unkown@gmail.com", response.getEmail());
			Assertions.assertNotNull(response.getDate());
		});
	}

	@Test
	public void testResponseStatus() throws Exception {
		this.mockMvc.perform(
			delete("/user/238hs12y7")
		).andExpectAll(
			status().isAccepted(),
			content().string(Matchers.containsString("Success Deleted!"))
		);
	}
}
