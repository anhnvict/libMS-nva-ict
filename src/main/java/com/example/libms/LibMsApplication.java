package com.example.libms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LibMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibMsApplication.class, args);
    }

}
