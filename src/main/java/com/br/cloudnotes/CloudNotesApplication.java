package com.br.cloudnotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.br.cloudnotes")
public class CloudNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudNotesApplication.class, args);
	}

}
