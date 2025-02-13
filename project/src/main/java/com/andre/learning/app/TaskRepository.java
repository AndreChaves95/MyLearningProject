package com.andre.learning.app;

import com.andre.learning.domain.Task;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository // so it can be Spring to manage this
public class TaskRepository {

    // Class to manage the connection to the database

    private final JdbcClient jdbcClient;

    public TaskRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Task findById(Long id) {
        String sql = "SELECT * FROM TASK WHERE task_id = :id";
        return jdbcClient.sql(sql)
                .param("task_id", id)
                .query(Task.class)
                .single();
    }

    public void createTask(Task task) {
        String sql = "INSERT INTO TASK (task_id, title, description, completed, created_at, updated_at) VALUES (?,?,?,?,?,?)";
        jdbcClient.sql(sql)
                .params(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getCreatedAt(), task.getUpdatedAt())
                .update();
    }
}
