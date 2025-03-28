package com.andre.learning.app.services;

import java.util.List;

import com.andre.learning.app.rabbitmq.RabbitMessageProducer;
import com.andre.learning.app.repositories.TaskRepository;
import com.andre.learning.customexceptions.TaskCompletionException;
import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.customexceptions.TaskNotFoundException;
import com.andre.learning.domain.Task;
import com.andre.learning.domain.TaskDTO;
import com.andre.learning.domain.TaskMessage;
import com.andre.learning.mappers.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    // Class to connect the controller with the repository
    // Here I´m using Mapper methods to hide logic from Database connection

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    private final RabbitMessageProducer rabbitMessageProducer;

    @Autowired
    public TaskService(TaskRepository taskRepository, RabbitMessageProducer rabbitMessageProducer) {
        this.taskRepository = taskRepository;
        this.rabbitMessageProducer = rabbitMessageProducer;
    }

    public TaskDTO findById(Long id) {
        return TaskMapper.mapToDTO(taskRepository.findById(id));
    }

    public List<TaskDTO> findAll() {
        return TaskMapper.mapToDTO(taskRepository.findAll());
    }

    public List<TaskDTO> findTodayTasks() {
        return TaskMapper.mapToDTO(taskRepository.findTodayTasks());
    }

    public TaskDTO createTask(TaskDTO taskDTO) throws TaskIdDuplicatedException {
        Task task = TaskMapper.mapToEntity(taskDTO);
        Task taskSaved = taskRepository.createTask(task);
        return TaskMapper.mapToDTO(taskSaved);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteTask(id);
    }

    public void updateTask(TaskDTO taskDTO, Long id) {
        if (taskRepository.findById(id) == null) {
            throw new TaskNotFoundException(">>> Task not found with id: " + id);
        }
        Task task = TaskMapper.mapToEntity(taskDTO);
        taskRepository.updateTask(task, id);
    }

    public String completeTask(TaskDTO taskDTO, Long id) throws TaskCompletionException {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException(">>> Task not found with id: " + id);
        }
        if (task.isCompleted()) {
            return "Task is already completed!";
        }
        TaskMessage taskMessage = TaskMapper.mapToMessage(taskDTO);
        taskMessage.setTaskId(id);
        rabbitMessageProducer.sendCompleteTaskMessage(taskMessage);
        return "Task completed successfully!";
    }

}
