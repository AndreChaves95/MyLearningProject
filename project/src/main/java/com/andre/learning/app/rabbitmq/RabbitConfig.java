package com.andre.learning.app.rabbitmq;

import java.util.HashMap;
import java.util.Map;

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
    public static final String DEAD_LETTER_QUEUE = "andre.dead-letter-queue";
    public static final String DEAD_LETTER_EXCHANGE = "andre.dead-letter-exchange";

    // The Queue is responsible for holding the messages until they are consumed.
    @Bean
    public Queue queue() {
        Map<String, Object> args = new HashMap<>();
        args.put("dlq-exchange", DEAD_LETTER_EXCHANGE);
        return new Queue(TASK_COMPLETE_QUEUE, true, false, false, args);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE, true);
    }

    // The Exchange is responsible for routing the messages to the correct Queue.
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("rabbit-exchange");
    }

    // The Dead Letter Exchange is responsible for routing the messages to the Dead Letter Queue.
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    // The Binding is responsible for bind the Queue to the Exchange.
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing-key");
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("dlq-routing-key");
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

    // The Jackson2JsonMessageConverter is responsible for converting the Java objects to JSON and vice versa.
    // The DefaultClassMapper is responsible for mapping the class names to the JSON payload.
    // By using it, we can specify the trusted packages that the Jackson library can deserialize.
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("com.andre.learning.domain");
        converter.setClassMapper(classMapper);
        return converter;
    }

}
