package com.ejemplo.java.pizzeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories /**Funciona para que pueda crear la base de datos*/
@EnableJpaAuditing /**Auditoría con Spring Data*/
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
