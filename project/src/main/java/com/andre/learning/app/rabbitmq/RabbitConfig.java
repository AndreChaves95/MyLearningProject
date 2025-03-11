package com.andre.learning.app.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  //For Spring to know that this is a Java Configuration class and define the Queues
public class RabbitConfig {

    public static final String TASK_COMPLETE_QUEUE = "andre.rabbit-task-queue";

    @Bean
    public Queue queue() {
        return new Queue(TASK_COMPLETE_QUEUE, false); //Create a new Queue with the name 'hello-rabbit'
    }

    // The Exchange is responsible for routing the messages to the correct Queue.
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("rabbit-exchange");
    }

    // The Binding is responsible for bind the Queue to the Exchange.
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing-key");
    }

    // The ConnectionFactory is responsible for creating connections to the RabbitMQ broker.
    // It encapsulates the configuration details like the broker's address, port, username, and password.
    // With this we can manage these details in one place and reuse the connection settings across the application.
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("com.andre.learning.domain");
        converter.setClassMapper(classMapper);
        return converter;
    }

}
