package org.paolo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("org.paolo.springboot.persistence.repository")
@EntityScan("org.paolo.springboot.persistence.model")
public class ParentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParentsApplication.class, args);
	}
}
