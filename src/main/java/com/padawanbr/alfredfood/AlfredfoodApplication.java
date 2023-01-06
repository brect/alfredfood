package com.padawanbr.alfredfood;

import com.padawanbr.alfredfood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlfredfoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlfredfoodApplication.class, args);
	}

}
