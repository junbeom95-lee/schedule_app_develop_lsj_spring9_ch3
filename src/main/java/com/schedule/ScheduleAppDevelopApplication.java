package com.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleAppDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleAppDevelopApplication.class, args);
    }

}
