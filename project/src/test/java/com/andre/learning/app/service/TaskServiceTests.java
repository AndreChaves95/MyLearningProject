package com.andre.learning.app.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;

import com.andre.learning.app.rabbitmq.RabbitMessageProducer;
import com.andre.learning.app.repositories.TaskRepository;
import com.andre.learning.app.services.TaskService;
import com.andre.learning.customexceptions.TaskCompletionException;
import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.domain.Task;
import com.andre.learning.domain.TaskDTO;
import com.andre.learning.mappers.TaskMapper;
import com.andre.learning.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private RabbitMessageProducer rabbitMessageProducer;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Task task = TestData.buildTask(1);
        when(taskRepository.findById(anyLong())).thenReturn(task);
        TaskDTO result = taskService.findById(task.getTaskId());
        assertEquals(TaskMapper.mapToDTO(task), result);
    }

    @Test
    void testFindAll() {
        Task task1 = TestData.buildTask(1);
        Task task2 = TestData.buildTask(2);
        List<Task> tasks = List.of(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);
        List<TaskDTO> result = taskService.findAll();
        assertEquals(TaskMapper.mapToDTO(tasks), result);
    }

    @Test
    void testFindTodayTasks() {
        Task task1 = TestData.buildTask(1);
        Task task2 = TestData.buildTask(2);
        task2.setCreatedAt(Timestamp.valueOf(task2.getCreatedAt().toLocalDateTime().plusDays(1)));
        task2.setCreatedAt(Timestamp.valueOf(task2.getCreatedAt().toLocalDateTime().plusDays(1).plusHours(1)));
        List<Task> tasks = List.of(task1, task2);
        when(taskRepository.findTodayTasks()).thenReturn(tasks);
        List<TaskDTO> result = taskService.findTodayTasks();
        assertEquals(TaskMapper.mapToDTO(tasks), result);
    }

    @Test
    void testCreateTask() throws TaskIdDuplicatedException {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        Task task = TaskMapper.mapToEntity(taskDTO);
        when(taskRepository.createTask(any())).thenReturn(task);
        TaskDTO result = taskService.createTask(taskDTO);
        assertEquals(taskDTO, result);
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteTask(anyLong());
        assertDoesNotThrow(() -> taskService.deleteTask(1L));
    }

    @Test
    void testUpdateTask() {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        Task task = TaskMapper.mapToEntity(taskDTO);
        when(taskRepository.findById(anyLong())).thenReturn(task);
        doNothing().when(taskRepository).updateTask(task, taskDTO.getId());
        assertDoesNotThrow(() -> taskService.updateTask(taskDTO, taskDTO.getId()));
    }

    @Test
    void testCompleteTask() throws TaskCompletionException {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        Task task = TestData.buildTask(1);
        when(taskRepository.findById(anyLong())).thenReturn(task);
        doNothing().when(rabbitMessageProducer).sendCompleteTaskMessage(any());
        assertDoesNotThrow(() -> taskService.completeTask(taskDTO, taskDTO.getId()));
    }
}
