package com.andre.learning.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.andre.learning.domain.Task;
import com.andre.learning.domain.TaskDTO;

public final class TestData {

    private TestData() {
    }

    public static TaskDTO buildTaskDto(int index) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId((long) index);
        taskDTO.setTitle("Task " + index);
        taskDTO.setDescription("Task Description");
        taskDTO.setCompleted(false);
        taskDTO.setCreatedAt(LocalDateTime.now());
        taskDTO.setUpdatedAt(LocalDateTime.now());
        return taskDTO;
    }

    public static TaskDTO buildCompletedTaskDto(int index) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId((long) index);
        taskDTO.setTitle("Task " + index);
        taskDTO.setDescription("Task Description");
        taskDTO.setCompleted(true);
        taskDTO.setCreatedAt(LocalDateTime.now());
        taskDTO.setUpdatedAt(LocalDateTime.now());
        return taskDTO;
    }

    public static Task buildTask(int index) {
        Task task = new Task();
        task.setTaskId((long) index);
        task.setTitle("Task " + index);
        task.setDescription("Task Description");
        task.setCompleted(false);
        task.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        task.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return task;
    }
}
