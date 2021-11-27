package com.m2w.sispj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SispjApplication {

	public static void main(String[] args) {
		SpringApplication.run(SispjApplication.class, args);
	}

}
