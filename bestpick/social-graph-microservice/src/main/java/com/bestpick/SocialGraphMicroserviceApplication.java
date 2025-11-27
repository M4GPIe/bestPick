package com.bestpick;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bestpick.repository.SocialGraphRepository;

@SpringBootApplication
public class SocialGraphMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialGraphMicroserviceApplication.class, args);
    }

    // TODO: remove for production

    @Bean
    public CommandLineRunner emptyDatabase(SocialGraphRepository socialGraphRepository) {
        return args -> {
            socialGraphRepository.deleteAll();
        };
    }
}
