package com.example.backend;

import com.example.backend.auth.AuthenticationServiceImpl;
import com.example.backend.auth.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.example.backend.role.Role.ADMIN;
import static com.example.backend.role.Role.MANAGER;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareProvider")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationServiceImpl service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("adminFirst")
					.lastname("adminLast")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			log.info("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.firstname("managerFirst")
					.lastname("managerLast")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.build();
			log.info("Manager token: " + service.register(manager).getAccessToken());
		};
	}
}
