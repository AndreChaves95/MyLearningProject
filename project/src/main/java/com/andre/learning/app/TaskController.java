package com.andre.learning.app;

import com.andre.learning.domain.Task;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
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

    @ResponseStatus(HttpStatus.CREATED) // This will return status 201 with Created state
    @PostMapping("/create")
    public String createTask(@Valid @RequestBody Task task) {
        taskService.createTask(task);
        return "Task created successfully!";
    }
}
