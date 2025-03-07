package com.andre.learning.app.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  //For Spring to know that this is a Java Configuration class and define the Queues
public class RabbitConfig {

    public static final String TASK_COMPLETE_QUEUE = "andre.rabbit-task-queue";

    @Bean
    public Queue queue() {
        return new Queue(TASK_COMPLETE_QUEUE, false); //Create a new Queue with the name 'hello-rabbit'
    }

    @Bean
    // The ConnectionFactory is responsible for creating connections to the RabbitMQ broker.
    // It encapsulates the configuration details like the broker's address, port, username, and password.
    // With this we can manage these details in one place and reuse the connection settings across the application.
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

}
