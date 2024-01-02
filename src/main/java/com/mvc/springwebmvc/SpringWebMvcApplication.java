package com.mvc.springwebmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication @EnableFeignClients
public class SpringWebMvcApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringWebMvcApplication.class, args);
	}
}
