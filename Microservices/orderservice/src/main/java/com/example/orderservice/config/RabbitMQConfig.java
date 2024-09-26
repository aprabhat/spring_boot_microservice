package com.example.orderservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.example.orderservice.util.Constant.ORDER_STATUS_QUEUE_NAME;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue(ORDER_STATUS_QUEUE_NAME, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}

