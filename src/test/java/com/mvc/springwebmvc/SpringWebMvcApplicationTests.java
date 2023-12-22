package com.mvc.springwebmvc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.mvc.springwebmvc.services.GreetingService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.hamcrest.Matchers;

@AutoConfigureMockMvc // melakukan konfigurasi otomatis MockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	private @Autowired GreetingService gretingService;

	@Test
	public void greetingNoParam() throws Exception {
		this.mockMvc.perform(
			get("/greet")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Assalmuallikum Brouther"))
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
		Assertions.assertArrayEquals(new String[]{"Assalamuallikum Brouther...", "Assalamuallikum ya ".concat(name)}, new String[]{greet1, greet2});
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
}
