package com.example.websearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.goodsApi.feign")
@EnableEurekaClient
public class WebSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSearchApplication.class, args);
    }

}
