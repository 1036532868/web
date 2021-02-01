package com.example.eureka10002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka10002Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka10002Application.class, args);
    }

}
