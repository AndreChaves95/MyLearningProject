package com.andre.learning.app.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import com.andre.learning.app.repositories.TaskRepository;
import com.andre.learning.domain.Task;
import com.andre.learning.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.simple.JdbcClient;

class TaskRepositoryTests {

    @Mock
    private JdbcClient jdbcClient;

    @InjectMocks
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Task task = TestData.buildTask(1);
        String sql = "SELECT * FROM TASK WHERE task_id = :task_id";
        JdbcClient.StatementSpec statementSpec = Mockito.mock(JdbcClient.StatementSpec.class);
        JdbcClient.MappedQuerySpec<Task> mappedQuerySpec = Mockito.mock(JdbcClient.MappedQuerySpec.class);
        when(jdbcClient.sql(sql)).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.query(Task.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.single()).thenReturn(task);
        Task result = taskRepository.findById(task.getTaskId());
        assertEquals(task, result);
    }

    @Test
    void testFindAll() {
        Task task1 = TestData.buildTask(1);
        Task task2 = TestData.buildTask(2);
        List<Task> tasks = List.of(task1, task2);
        String sql = "SELECT * FROM TASK";
        JdbcClient.StatementSpec statementSpec = Mockito.mock(JdbcClient.StatementSpec.class);
        JdbcClient.MappedQuerySpec<Task> mappedQuerySpec = Mockito.mock(JdbcClient.MappedQuerySpec.class);
        when(jdbcClient.sql(sql)).thenReturn(statementSpec);
        when(statementSpec.query(Task.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(tasks);
        List<Task> result = taskRepository.findAll();
        assertEquals(tasks, result);
    }

    @Test
    void testFindTodayTasks() {
        Task task1 = TestData.buildTask(1);
        Task task2 = TestData.buildTask(2);
        List<Task> tasks = List.of(task1, task2);
        String sql = "SELECT * FROM TASK WHERE DATE(created_at) = CURRENT_DATE OR DATE(updated_at) = CURRENT_DATE";
        JdbcClient.StatementSpec statementSpec = Mockito.mock(JdbcClient.StatementSpec.class);
        JdbcClient.MappedQuerySpec<Task> mappedQuerySpec = Mockito.mock(JdbcClient.MappedQuerySpec.class);
        when(jdbcClient.sql(sql)).thenReturn(statementSpec);
        when(statementSpec.param(any(), any())).thenReturn(statementSpec);
        when(statementSpec.query(Task.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(tasks);
        List<Task> result = taskRepository.findTodayTasks();
        assertEquals(tasks, result);
    }

    @Test
    void testCreateTask() {
        Task task = TestData.buildTask(1);
        String sql = "INSERT INTO TASK (task_id, title, description, completed, created_at, updated_at) VALUES (?,?,?,?,?,?)";
        JdbcClient.StatementSpec statementSpec = Mockito.mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(sql)).thenReturn(statementSpec);
        when(statementSpec.params(any(), any(), any(), any(), any(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1); // Mocking to return 1, indicating one row affected
        assertDoesNotThrow(() -> taskRepository.createTask(task));
        assertEquals(1, task.getTaskId());
    }

    @Test
    void testDeleteTask() {
        Task task = TestData.buildTask(1);
        String sql = "DELETE FROM TASK WHERE task_id = :task_id";
        JdbcClient.StatementSpec statementSpec = Mockito.mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(sql)).thenReturn(statementSpec);
        when(statementSpec.param(task.getTaskId())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertEquals(1, task.getTaskId());
    }

    @Test
    void testUpdateTask() {
        Task task = TestData.buildTask(1);
        String sql = "UPDATE TASK SET title = ?, description = ?, completed = ?, created_at = ?, updated_at = ? WHERE task_id = ?";
        JdbcClient.StatementSpec statementSpec = Mockito.mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(sql)).thenReturn(statementSpec);
        when(statementSpec.params(any(), any(), any(), any(), any(), anyLong())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> taskRepository.updateTask(task, task.getTaskId()));
        assertEquals(1, task.getTaskId());
    }

    @Test
    void testCompleteTask() {
        Task task = TestData.buildTask(1);
        String sql = "UPDATE TASK SET completed = ?, updated_at = ? WHERE task_id = ?";
        JdbcClient.StatementSpec statementSpec = Mockito.mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(sql)).thenReturn(statementSpec);
        when(statementSpec.params(any(), any(), anyLong())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> taskRepository.completeTask(task));
        assertEquals(1, task.getTaskId());
    }
}
