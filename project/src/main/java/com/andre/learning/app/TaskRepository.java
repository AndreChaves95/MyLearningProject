package com.andre.learning.app;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.andre.learning.customexceptions.TaskIdDuplicatedException;
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
        String sql = "SELECT * FROM TASK WHERE task_id = :task_id";
        return jdbcClient.sql(sql)
                .param("task_id", id)
                .query(Task.class)
                .single();
    }

    public List<Task> findAll() {
        String sql = "SELECT * FROM TASK";
        return jdbcClient.sql(sql)
                .query(Task.class)
                .list();
    }

    public void createTask(Task task) throws TaskIdDuplicatedException {
        String sql = "INSERT INTO TASK (task_id, title, description, completed, created_at, updated_at) VALUES (?,?,?,?,?,?)";
        try {
            jdbcClient.sql(sql)
                    .params(task.getTaskId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getCreatedAt(), task.getUpdatedAt())
                    .update();
        } catch (Exception exception) {
            throw new TaskIdDuplicatedException("Task with ID:" + task.getTaskId() + " already exists", exception);
        }
    }

}
