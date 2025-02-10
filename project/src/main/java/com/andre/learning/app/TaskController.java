package com.andre.learning.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    public TaskController() {
    }

    @RequestMapping("/homepage")
    public String home(){
        return "My Learning Project Starting Page!";
    }
}
