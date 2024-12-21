package com.cour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CourApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourApplication.class, args);
    }

}
