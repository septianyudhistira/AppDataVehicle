package com.seeyou.vehicledataapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.seeyou.vehicledataapp.repository")
public class AppDataVehicleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDataVehicleApplication.class, args);
    }


}
