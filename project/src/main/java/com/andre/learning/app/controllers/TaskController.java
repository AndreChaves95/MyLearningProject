package com.andre.learning.app.controllers;

import java.util.List;

import com.andre.learning.app.services.TaskService;
import com.andre.learning.customexceptions.TaskCompletionException;
import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.domain.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    // Class that handles the requests and responses
    // Here I´m using DTOs for the transportation of data

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/homepage")
    public String home(){
        return "My Learning Project Starting Page!";
    }

    @GetMapping("/{id}")
    public TaskDTO findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping("")
    public List<TaskDTO> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/today")
    public List<TaskDTO> findTodayTasks() {
        return taskService.findTodayTasks();
    }

    @ResponseStatus(HttpStatus.CREATED) // This will return status 201 with Created state
    @PostMapping("/create")
    public String createTask(@Valid @RequestBody TaskDTO taskDTO) throws TaskIdDuplicatedException {
        taskService.createTask(taskDTO);
        return "Task created successfully!";
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // This will return status 204 with No Content state
    @PutMapping("/{id}")
    public void updateTask(@Valid @RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        taskService.updateTask(taskDTO, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/complete")
    public void completeTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) throws TaskCompletionException {
        taskService.completeTask(taskDTO, id);
    }

}
