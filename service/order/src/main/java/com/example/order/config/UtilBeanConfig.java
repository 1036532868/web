package com.example.order.config;

import com.example.util.IdWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/7 22:48
 * @since 1.0.0
 */
@Configuration
public class UtilBeanConfig {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
