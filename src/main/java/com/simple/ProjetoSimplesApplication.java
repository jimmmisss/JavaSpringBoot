package com.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.simple")
@SpringBootApplication
public class ProjetoSimplesApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProjetoSimplesApplication.class, args);
	}

}