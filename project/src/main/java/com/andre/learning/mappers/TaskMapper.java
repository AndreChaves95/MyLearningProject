package com.andre.learning.mappers;

import java.sql.Timestamp;
import java.util.List;

import com.andre.learning.domain.Task;
import com.andre.learning.domain.TaskDTO;
import com.andre.learning.domain.TaskMessage;

public class TaskMapper {

    public static TaskDTO mapToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getTaskId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCompleted(task.isCompleted());
        taskDTO.setCreatedAt(task.getCreatedAt().toLocalDateTime());
        taskDTO.setUpdatedAt(task.getUpdatedAt().toLocalDateTime());
        return taskDTO;
    }

    public static List<TaskDTO> mapToDTO(List<Task> task) {
        return task.stream().map(TaskMapper::mapToDTO).toList();
    }

    public static Task mapToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTaskId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        task.setCreatedAt(Timestamp.valueOf(taskDTO.getCreatedAt()));
        task.setUpdatedAt(Timestamp.valueOf((taskDTO.getUpdatedAt())));
        return task;
    }

    public static TaskMessage mapToMessage(TaskDTO taskDTO) {
        TaskMessage taskMessage = new TaskMessage();
        taskMessage.setTaskId(taskDTO.getId());
        taskMessage.setTitle(taskDTO.getTitle());
        taskMessage.setDescription(taskDTO.getDescription());
        taskMessage.setCompleted(taskDTO.isCompleted());
        taskMessage.setCreatedAt(Timestamp.valueOf(taskDTO.getCreatedAt()));
        taskMessage.setUpdatedAt(Timestamp.valueOf((taskDTO.getUpdatedAt())));
        return taskMessage;
    }

    public static TaskDTO mapToDTO(TaskMessage taskMessage) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskMessage.getTaskId());
        taskDTO.setTitle(taskMessage.getTitle());
        taskDTO.setDescription(taskMessage.getDescription());
        taskDTO.setCompleted(taskMessage.isCompleted());
        taskDTO.setCreatedAt(taskMessage.getCreatedAt().toLocalDateTime());
        taskDTO.setUpdatedAt(taskMessage.getUpdatedAt().toLocalDateTime());
        return taskDTO;
    }
}
