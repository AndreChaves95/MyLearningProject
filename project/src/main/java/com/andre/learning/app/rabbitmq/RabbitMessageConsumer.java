package com.andre.learning.app.rabbitmq;

import static com.andre.learning.app.rabbitmq.RabbitConfig.TASK_COMPLETE_QUEUE;

import com.andre.learning.app.repositories.TaskRepository;
import com.andre.learning.customexceptions.TaskCompletionException;
import com.andre.learning.domain.TaskMessage;
import com.andre.learning.mappers.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RabbitMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageConsumer.class);

    @Autowired
    private final TaskRepository taskRepository;

    RabbitMessageConsumer(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @RabbitListener(queues = TASK_COMPLETE_QUEUE)
    public void handleTaskCompletionEventMessage(TaskMessage taskMessage) throws TaskCompletionException {
        try {
            logger.info(">>> Received message from RabbitMQ!");
            taskRepository.completeTask(TaskMapper.mapToEntity(taskMessage));
            logger.info(">>> Task completion processed successfully!");
        } catch (Exception exception) {
            throw new TaskCompletionException("Error processing message from RabbitMQ!", exception);
        }
    }

}
