package com.andre.learning.app;

import java.util.List;

import com.andre.learning.customexceptions.TaskDeletionErrorException;
import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.customexceptions.TaskNotFoundException;
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
            throw new TaskIdDuplicatedException("Task with ID: " + task.getTaskId() + " already exists!");
        }
    }

    public void updateTask(Task task, Long id) {
        String sql = "UPDATE TASK SET title = :title, description = :description, completed = :completed, created_at = :created_at, updated_at = :updated_at WHERE task_id = :task_id";
        try {
            jdbcClient.sql(sql)
                    .params(task.getTitle(), task.getDescription(), task.isCompleted(), task.getCreatedAt(), task.getUpdatedAt())
                    .param("task_id", id)
                    .update();
        } catch (Exception exception) {
            throw new TaskNotFoundException("Task with ID: " + id + " not found! No Update done!");
        }
    }

    public void deleteTask(Long id) {
        String sql = "DELETE FROM TASK WHERE task_id = :task_id";
        try {
            int rowsDeleted = jdbcClient.sql(sql)
                    .param("task_id", id)
                    .update();
            if (rowsDeleted == 0) {
                throw new TaskNotFoundException("Task with ID: " + id + " not found! No Delete done!");
            }
        } catch (Exception exception) {
            throw new TaskDeletionErrorException("Error deleting Task!", exception);
        }
    }

}
