package com.andre.learning.app.controller;

import com.andre.learning.domain.TaskDTO;
import com.andre.learning.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Sql({"/clean-database.sql", "/fill-database.sql"})
    void testFindById() throws Exception {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        mockMvc.perform(get("/api/v1/tasks/{id}", taskDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDTO.getId()))
                .andExpect(jsonPath("$.title").value(taskDTO.getTitle()));
    }

    @Test
    @Sql({"/clean-database.sql", "/fill-database.sql"})
    void testFindAll() throws Exception {
        TaskDTO task1 = TestData.buildTaskDto(1);
        TaskDTO task2 = TestData.buildTaskDto(2);
        TaskDTO task3 = TestData.buildTaskDto(3);
        TaskDTO task4 = TestData.buildCompletedTaskDto(4);
        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(task1.getId()))
                .andExpect(jsonPath("$[0].title").value(task1.getTitle()))
                .andExpect(jsonPath("$[0].completed").value(task1.isCompleted()))
                .andExpect(jsonPath("$[1].id").value(task2.getId()))
                .andExpect(jsonPath("$[1].title").value(task2.getTitle()))
                .andExpect(jsonPath("$[1].completed").value(task2.isCompleted()))
                .andExpect(jsonPath("$[2].id").value(task3.getId()))
                .andExpect(jsonPath("$[2].title").value(task3.getTitle()))
                .andExpect(jsonPath("$[2].completed").value(task3.isCompleted()))
                .andExpect(jsonPath("$[3].id").value(task4.getId()))
                .andExpect(jsonPath("$[3].title").value(task4.getTitle()))
                .andExpect(jsonPath("$[3].completed").value(task4.isCompleted()));
    }

    @Test
    @Sql({"/clean-database.sql", "/fill-database.sql"})
    void testCreateTask() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/create")
                        .contentType("application/json")
                        .content("{\"id\": 5, " +
                                "\"title\": \"Task 5\", " +
                                "\"description\": \"Task 5 description\", " +
                                "\"completed\": false, " +
                                "\"createdAt\": \"" + LocalDateTime.now() + "\", " +
                                "\"updatedAt\": \"" + LocalDateTime.now().plusHours(1) + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Task created successfully!"));
    }

    @Test
    @Sql({"/clean-database.sql", "/fill-database.sql"})
    void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully!"));
    }

    @Test
    @Sql({"/clean-database.sql", "/fill-database.sql"})
    void testUpdateTask() throws Exception {
        TaskDTO taskDTO = TestData.buildTaskDto(1);
        mockMvc.perform(put("/api/v1/tasks/{id}", 1)
                .contentType("application/json")
                .content("{\"title\": \"Task 11\", " +
                        "\"description\": \"Task 11 description\", " +
                        "\"createdAt\": \"" + taskDTO.getCreatedAt() + "\", " +
                        "\"updatedAt\": \"" + LocalDateTime.now() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated successfully!"));
    }

    @Test
    @Sql({"/clean-database.sql", "/fill-database.sql"})
    void testAlreadyCompletedMessageWhenTaskIsCompleted() throws Exception {
        TaskDTO taskDTO = TestData.buildCompletedTaskDto(4);
        mockMvc.perform(put("/api/v1/tasks/{id}/complete", taskDTO.getId())
                        .contentType("application/json")
                        .content("{\"completed\": true, " +
                                "\"createdAt\": \"" + taskDTO.getCreatedAt() + "\", " +
                                "\"updatedAt\": \"" + LocalDateTime.now() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task is already completed!"));
    }

}