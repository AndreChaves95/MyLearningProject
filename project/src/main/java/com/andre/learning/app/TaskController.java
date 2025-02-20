package com.andre.learning.app;

import java.util.List;

import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.domain.Task;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class TaskController {

    // Class that handles the requests and responses

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/homepage")
    public String home(){
        return "My Learning Project Starting Page!";
    }

    @GetMapping("/task/{id}")
    public Task findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping("/tasks")
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED) // This will return status 201 with Created state
    @PostMapping("/create")
    public String createTask(@Valid @RequestBody Task task) throws TaskIdDuplicatedException {
        taskService.createTask(task);
        return "Task created successfully!";
    }

}
