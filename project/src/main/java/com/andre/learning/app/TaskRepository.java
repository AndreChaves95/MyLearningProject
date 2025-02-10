package com.andre.learning.app;

import com.andre.learning.domain.Task;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component // so it can be Spring to manage this
public class TaskRepository {

    private final JdbcClient jdbcClient;

    public TaskRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Task findById(Long id) {
        String sql = "SELECT * FROM Task WHERE id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Task.class)
                .single();
    }
}
