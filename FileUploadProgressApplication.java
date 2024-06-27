package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import demo.example.service.ProgressHandler;

@SpringBootApplication
public class FileUploadProgressApplication {

	public static void main(String[] args) {

		SpringApplication.run(FileUploadProgressApplication.class, args);
	}

	@Bean
	public ProgressHandler progressHandler() {
		return new ProgressHandler();
	}
}
