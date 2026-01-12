package com.example.claimsadministrationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClaimsAdministrationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimsAdministrationSystemApplication.class, args);
    }

}
