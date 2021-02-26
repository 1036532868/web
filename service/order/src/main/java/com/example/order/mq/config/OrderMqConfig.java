package com.example.order.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 12:03
 * @since 1.0.0
 */
@Configuration
public class OrderMqConfig {

    @Bean
    public Queue orderTimeoutHandlerQueue() {
        return new Queue("q.order.timeout.handler");
    }
    @Bean
    public Exchange orderTimeoutHandlerExchange(){
        return new DirectExchange("ex.order.timeout.handler");
    }
    @Bean
    public Binding orderBinding(Queue orderTimeoutHandlerQueue, Exchange orderTimeoutHandlerExchange){
        return BindingBuilder.bind(orderTimeoutHandlerQueue).to(orderTimeoutHandlerExchange).with("order.timeout.handler").noargs();
    }

    // 超时队列
    @Bean
    public Queue orderTimeoutQueue(){
        return QueueBuilder.durable("q.order.timeout").
                deadLetterExchange("ex.order.timeout.handler").
                deadLetterRoutingKey("order.timeout.handler").
                ttl(30 * 60 * 1000).build();
    }
    @Bean
    public Exchange orderTimeoutExchange(){
        return new DirectExchange("ex.order.timeout");
    }

    @Bean
    public Binding orderTimeoutBinding(Queue orderTimeoutQueue, Exchange orderTimeoutExchange){
        return BindingBuilder.bind(orderTimeoutQueue).to(orderTimeoutExchange).with("order.timeout").noargs();
    }
}
