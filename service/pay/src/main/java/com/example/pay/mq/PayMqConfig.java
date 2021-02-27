package com.example.pay.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 16:05
 * @since 1.0.0
 */
@Configuration
public class PayMqConfig {

    @Bean
    public Queue payQueue(){
        return new Queue("q.order.pay");
    }

    @Bean
    public Exchange payExchange(){
        return new DirectExchange("ex.order.pay");
    }

    @Bean
    public Binding payBinding(Queue payQueue, Exchange payExchange){
        return BindingBuilder.bind(payQueue).to(payExchange).with("order.pay").noargs();
    }
}
