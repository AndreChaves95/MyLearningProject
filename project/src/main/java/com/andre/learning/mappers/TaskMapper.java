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
        if (taskDTO.getTitle() != null) {
            task.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }
        task.setCompleted(taskDTO.isCompleted());
        task.setCreatedAt(Timestamp.valueOf(taskDTO.getCreatedAt()));
        task.setUpdatedAt(Timestamp.valueOf(taskDTO.getUpdatedAt()));
        return task;
    }

    public static TaskMessage mapToMessage(TaskDTO taskDTO) {
        TaskMessage taskMessage = new TaskMessage();
        if (taskDTO.getTitle() != null) {
            taskMessage.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getDescription() != null) {
            taskMessage.setDescription(taskDTO.getDescription());
        }
        taskMessage.setCompleted(taskDTO.isCompleted());
        taskMessage.setCreatedAt(Timestamp.valueOf(taskDTO.getCreatedAt()));
        taskMessage.setUpdatedAt(Timestamp.valueOf((taskDTO.getUpdatedAt())));
        return taskMessage;
    }

    public static Task mapToEntity(TaskMessage taskMessage) {
        Task task = new Task();
        task.setTaskId(taskMessage.getTaskId());
        task.setTitle(taskMessage.getTitle());
        task.setDescription(taskMessage.getDescription());
        task.setCompleted(taskMessage.isCompleted());
        task.setCreatedAt(Timestamp.valueOf(taskMessage.getCreatedAt().toLocalDateTime()));
        task.setUpdatedAt(Timestamp.valueOf((taskMessage.getUpdatedAt()).toLocalDateTime()));
        return task;
    }
}
