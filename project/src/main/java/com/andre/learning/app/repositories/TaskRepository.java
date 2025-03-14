package com.andre.learning.app.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.andre.learning.customexceptions.TaskCompletionException;
import com.andre.learning.customexceptions.TaskDeletionErrorException;
import com.andre.learning.customexceptions.TaskIdDuplicatedException;
import com.andre.learning.customexceptions.TaskNotFoundException;
import com.andre.learning.domain.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository // so it can be Spring to manage this
public class TaskRepository {

    // Class to manage the connection to the database
    // Here I´m using Task entity to handle data with db
    
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

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

    public List<Task> findTodayTasks() {
        String sql = "SELECT * FROM TASK WHERE DATE(created_at) = CURRENT_DATE OR DATE(updated_at) = CURRENT_DATE";
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
            logger.info(">>> Task created successfully!");
        } catch (Exception exception) {
            throw new TaskIdDuplicatedException("Task with ID: " + task.getTaskId() + " already exists!");
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
            } else {
                logger.info(">>> Task deleted successfully!");
            }
        } catch (Exception exception) {
            throw new TaskDeletionErrorException("Error deleting Task!", exception);
        }
    }

    public void updateTask(Task task, Long id) {
        String sql = "UPDATE TASK SET title = ?, description = ?, completed = ?, created_at = ?, updated_at = ? WHERE task_id = ?";
        try {
            jdbcClient.sql(sql)
                    .params(task.getTitle(), task.getDescription(), task.isCompleted(), task.getCreatedAt(), task.getUpdatedAt(), id)
                    .update();
            logger.info(">>> Task updated successfully!");
        } catch (Exception exception) {
            throw new TaskNotFoundException("Task with ID: " + id + " not found! No Update done!");
        }
    }

    public void completeTask(Task task) throws TaskCompletionException {
        String sql = "UPDATE TASK SET completed = ?, updated_at = ? WHERE task_id = ?";
        try {
            jdbcClient.sql(sql)
                    .params(task.isCompleted(), LocalDateTime.now(), task.getTaskId())
                    .update();
            logger.info(">>> Task status updated successfully!");
        } catch (Exception exception) {
            throw new TaskCompletionException("Error completing Task from RabbitMQ!", exception);
        }
    }

}
