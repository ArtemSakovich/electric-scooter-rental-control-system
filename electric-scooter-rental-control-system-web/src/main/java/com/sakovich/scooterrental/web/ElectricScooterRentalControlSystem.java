package com.sakovich.scooterrental.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sakovich.scooterrental"})
@EntityScan(basePackages = {"com.sakovich.scooterrental"})
@EnableJpaRepositories(basePackages = {"com.sakovich.scooterrental"})
public class ElectricScooterRentalControlSystem {
    public static void main(String[] args) {
        SpringApplication.run(ElectricScooterRentalControlSystem.class, args);
    }
}
