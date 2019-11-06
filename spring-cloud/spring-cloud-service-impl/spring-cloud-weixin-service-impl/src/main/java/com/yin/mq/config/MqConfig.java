package com.yin.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class MqConfig {

    @Bean
    public Queue queue(){
        return  new Queue("directQueue");

    }
    @Bean
    public DirectExchange directExchange(){
        return  new DirectExchange("directExchangeTest");
    }

    @Bean
    Binding directbindingExchangeMessage(@Qualifier("queue") Queue queueMessage, DirectExchange directExchange) {
        return BindingBuilder.bind(queueMessage).to(directExchange).with("directRoutKey");
    }
//    @Bean
//    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}