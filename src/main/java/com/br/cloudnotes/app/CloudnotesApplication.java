package com.br.cloudnotes.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.br.cloudnotes")
public class CloudnotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudnotesApplication.class, args);
	}

}
