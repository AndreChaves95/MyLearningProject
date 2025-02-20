package com.andre.learning.app;

import java.util.List;

import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    // Class to connect the controller with the repository

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void createTask(Task task) throws TaskIdDuplicatedException {
        taskRepository.createTask(task);
    }

}
