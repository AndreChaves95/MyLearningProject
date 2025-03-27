package com.andre.learning.app.controllers;

import java.util.List;

import com.andre.learning.app.services.TaskService;
import com.andre.learning.customexceptions.TaskCompletionException;
import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.domain.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
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

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) throws TaskIdDuplicatedException {
        taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "Task deleted successfully!";
    }

    @PutMapping("/{id}")
    public String updateTask(@Valid @RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        taskService.updateTask(taskDTO, id);
        return "Task updated successfully!";
    }

    @PutMapping("/{id}/complete")
    public String completeTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) throws TaskCompletionException {
        return taskService.completeTask(taskDTO, id);
    }

}
