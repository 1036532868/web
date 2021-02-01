package com.example.eureka10001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka10001Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka10001Application.class, args);
    }

}
