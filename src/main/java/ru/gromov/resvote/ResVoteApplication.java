package ru.gromov.resvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories
@EnableWebSecurity
public class ResVoteApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResVoteApplication.class, args);
	}
}

