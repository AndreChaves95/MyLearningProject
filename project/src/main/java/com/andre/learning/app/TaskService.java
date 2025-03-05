package com.andre.learning.app;

import java.util.List;

import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.domain.Task;
import com.andre.learning.domain.TaskDTO;
import com.andre.learning.mappers.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    // Class to connect the controller with the repository
    // Here I´m using mappers to hide logic from Database connection

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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

    public void createTask(TaskDTO taskDTO) throws TaskIdDuplicatedException {
        Task task = TaskMapper.mapToEntity(taskDTO);
        taskRepository.createTask(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteTask(id);
    }

    public void updateTask(TaskDTO taskDTO, Long id) {
        Task task = TaskMapper.mapToEntity(taskDTO);
        taskRepository.updateTask(task, id);
    }

}
