package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.core.model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class TestDatabaseTasksRepository {
    private JdbcOperations mockJdbcOperations;
    private DatabaseTasksRepository databaseTasksRepository;

    @Before
    public void setup() {
        mockJdbcOperations = mock(JdbcOperations.class);
        databaseTasksRepository = new DatabaseTasksRepository(mockJdbcOperations);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> mockListTasks = mock(List.class);
        when(mockJdbcOperations.query(anyString(), any(RowMapper.class))).thenReturn(mockListTasks);
        List<Task> excectedList= databaseTasksRepository.getAllTasks();
        verify(mockJdbcOperations, times(1)).query(
                eq("SELECT id, text, status, createdAT, updateAT FROM task"),
                any(RowMapper.class)
        );

        Assert.assertSame(excectedList, mockListTasks);
    }

    @Test
    public void testGetTask() {
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";
        Task mockTask = mock(Task.class);

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyLong())).thenReturn(mockTask);
        Task expectedTask = databaseTasksRepository.getTask(taskId);

        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT id, text, status, createdAT, updateAT FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(taskId)
        );

        Assert.assertEquals(mockTask, expectedTask);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetTaskException() {
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyLong()))
                .thenThrow(IncorrectResultSizeDataAccessException.class);
        databaseTasksRepository.getTask(taskId);
    }

    @Test
    public void testCreate() {
        long taskId = 1;
        int expectedCreateTasks = 1;
        Task mockTask = mock(Task.class);

        when(mockJdbcOperations.update(any(PreparedStatementCreator.class))).thenReturn(expectedCreateTasks);
        Task expectedTask = databaseTasksRepository.createTask(mockTask);

        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (id, text, status, createdAT, updateAT) VALUES (?, ?, ?, ?, ?)"),
                any(RowMapper.class),
                eq(taskId)
        );

        Assert.assertEquals(mockTask, expectedTask);
    }

    @Test
    public void testDeleteTask() throws SQLException {
        int expectedDeletedTasks = 1;
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";
//        Connection conn = mock(Connection.class);
        when(mockJdbcOperations.update(any(PreparedStatementCreator.class))).thenReturn(1);

        int receivedDeletedTasks = databaseTasksRepository.deleteTask(taskId);

        verify(mockJdbcOperations, times(1)).update(
                any(PreparedStatementCreator.class)
        );
        Assert.assertEquals(expectedDeletedTasks, receivedDeletedTasks);

//        verify(conn, times(1)).prepareStatement(
//            eq("DELETE FROM task WHERE id = ?")
//        );
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteTaskException() {
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";
        when(mockJdbcOperations.update(any(PreparedStatementCreator.class)))
                .thenThrow(IncorrectResultSizeDataAccessException.class);
        int receivedDeletedTasks = databaseTasksRepository.deleteTask(taskId);
    }

    @Test
    public void testUpdateTask() {
        Task expectedTask = mock(Task.class);

        when(mockJdbcOperations.update(any(PreparedStatementCreator.class))).thenReturn(1);

        Task receivedTask = databaseTasksRepository.updateTask(expectedTask);

        verify(mockJdbcOperations, times(1)).update(
                any(PreparedStatementCreator.class)
        );
        Assert.assertEquals(receivedTask, expectedTask);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testUpdateTaskException() {
        Task expectedTask = mock(Task.class);
        when(mockJdbcOperations.update(any(PreparedStatementCreator.class)))
                .thenThrow(IncorrectResultSizeDataAccessException.class);
        Task receivedTask = databaseTasksRepository.updateTask(expectedTask);
    }

}
