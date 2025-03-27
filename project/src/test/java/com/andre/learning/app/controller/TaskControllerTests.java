package com.andre.learning.app.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import com.andre.learning.app.controllers.TaskController;
import com.andre.learning.app.services.TaskService;
import com.andre.learning.customexceptions.TaskCompletionException;
import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.domain.TaskDTO;
import com.andre.learning.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerTests {

    @Mock
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        taskController.findById(taskDTO.getId());
        verify(taskController, times(1)).findById(taskDTO.getId());
    }

    @Test
    void testFindAll() {
        taskController.findAll();
        verify(taskController, times(1)).findAll();
    }

    @Test
    void testCreateTask() throws TaskIdDuplicatedException {
        TaskDTO taskDTO = TestData.buildTaskDto(5);
        when(taskController.createTask(taskDTO)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(taskDTO));
        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);
        verify(taskController, times(1)).createTask(taskDTO);
        assertEquals(taskDTO, response.getBody());
    }

    @Test
    void testDeleteTask() {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        when(taskController.deleteTask(taskDTO.getId())).thenReturn("Task deleted successfully");
        String response = taskController.deleteTask(taskDTO.getId());
        verify(taskController, times(1)).deleteTask(taskDTO.getId());
        assertEquals("Task deleted successfully", response);
    }

    @Test
    void testUpdateTask() {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        TaskDTO taskDTOUpdated = new TaskDTO();
        taskDTOUpdated.setTitle("Task Updated");
        taskDTOUpdated.setDescription("Task Description Updated");
        taskDTOUpdated.setUpdatedAt(LocalDateTime.now());
        when(taskController.updateTask(taskDTO, taskDTO.getId())).thenReturn("Task updated successfully");
        String response = taskController.updateTask(taskDTO, taskDTO.getId());
        verify(taskController, times(1)).updateTask(taskDTO, taskDTO.getId());
        assertEquals("Task updated successfully", response);
    }

    @Test
    void testCompleteTask() throws TaskCompletionException {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        TaskDTO taskCompleted = new TaskDTO();
        taskCompleted.setCompleted(true);
        taskCompleted.setUpdatedAt(LocalDateTime.now());
        when(taskController.completeTask(taskDTO, taskDTO.getId())).thenReturn("Task completed successfully");
        String response = taskController.completeTask(taskDTO, taskDTO.getId());
        verify(taskController, times(1)).completeTask(taskDTO, taskDTO.getId());
        assertEquals("Task completed successfully", response);
    }
}