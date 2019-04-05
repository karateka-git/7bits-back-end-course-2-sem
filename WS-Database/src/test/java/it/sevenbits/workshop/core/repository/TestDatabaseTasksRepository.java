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
        long taskId = 1;
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
        long taskId = 1;

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyLong()))
                .thenThrow(IncorrectResultSizeDataAccessException.class);
        databaseTasksRepository.getTask(taskId);
    }

//    @Test
//    public void testCreate() {
//        long taskId = 1;
//        Task mockTask = mock(Task.class);
//
//        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyLong())).thenReturn(mockTask);
//        Task expectedTask = databaseTasksRepository.getTask(taskId);
//
//        verify(mockJdbcOperations, times(1)).queryForObject(
//                eq("SELECT id, text, status, createdAT, updateAT FROM task WHERE id = ?"),
//                any(RowMapper.class),
//                eq(taskId)
//        );
//
//        Assert.assertEquals(mockTask, expectedTask);
//    }

    @Test
    public void testDeleteTask() {
        int expectedDeletedTasks = 1;
        long taskId = 1;
        when(mockJdbcOperations.update(any(PreparedStatementCreator.class))).thenReturn(1);

        int receivedDeletedTasks = databaseTasksRepository.deleteTask(taskId);
        verify(mockJdbcOperations, times(1)).update(
                any(PreparedStatementCreator.class)
        );
        Assert.assertEquals(expectedDeletedTasks, receivedDeletedTasks);
    }


}
