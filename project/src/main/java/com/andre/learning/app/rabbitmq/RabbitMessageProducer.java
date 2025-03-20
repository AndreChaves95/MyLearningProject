package com.andre.learning.app.rabbitmq;

import com.andre.learning.customexceptions.TaskCompletionException;
import com.andre.learning.domain.TaskMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageProducer.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCompleteTaskMessage(TaskMessage taskMessage) throws TaskCompletionException {
        try {
            rabbitTemplate.convertAndSend("rabbit-exchange", "routing-key", taskMessage); // Send message to RabbitMQ
            logger.info("***** RabbitMQ message sent successfully! *****");
        } catch (Exception exception) {
            throw new TaskCompletionException("Error sending message to RabbitMQ to complete task!", exception);
        }
    }

}
