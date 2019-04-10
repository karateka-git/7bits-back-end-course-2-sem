package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.service.TaskMapper;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.*;

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
        RequestGetAllTasks mockRequestGetAllTasks = mock(RequestGetAllTasks.class);
        when(mockJdbcOperations.query(anyString(), any(PreparedStatementSetter.class), any(RowMapper.class)))
                .thenReturn(mockListTasks);
        when(mockRequestGetAllTasks.getOrder()).thenReturn("desc");
        List<Task> excectedList= databaseTasksRepository.getAllTasks(mockRequestGetAllTasks);
        verify(mockJdbcOperations, times(1)).query(
                eq("SELECT id, text, status, createdAT, updateAT FROM task " +
                        "where status=? " +
                        "order by createdAT desc " +
                        "limit ? offset ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        );

        Assert.assertSame(excectedList, mockListTasks);
    }

    @Test
    public void testGetTask() {
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";
        Task mockTask = mock(Task.class);

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyString())).thenReturn(mockTask);
        Task expectedTask = databaseTasksRepository.getTask(taskId);

        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT id, text, status, createdAT, updateAT FROM task WHERE id = ?"),
                //any(TaskMapper.class),
                any(RowMapper.class),
                eq(taskId)
        );

        Assert.assertEquals(mockTask, expectedTask);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetTaskException() {
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyString()))
                .thenThrow(IncorrectResultSizeDataAccessException.class);
        databaseTasksRepository.getTask(taskId);
    }

    @Test
    public void testCreate() {
        int expectedCreateTasks = 1;
        Task mockTask = mock(Task.class);

        when(mockJdbcOperations.update(anyString(), any(PreparedStatementSetter.class))).thenReturn(expectedCreateTasks);
        Task expectedTask = databaseTasksRepository.createTask(mockTask);

        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (id, text, status, createdAT, updateAT) VALUES (?, ?, ?, ?, ?)"),
                any(PreparedStatementSetter.class)
        );

        Assert.assertEquals(mockTask, expectedTask);
    }

    @Test
    public void testDeleteTask() {
        int expectedDeletedTasks = 1;
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";
        when(mockJdbcOperations.update(anyString(), any(PreparedStatementSetter.class))).thenReturn(1);

        int receivedDeletedTasks = databaseTasksRepository.deleteTask(taskId);

        verify(mockJdbcOperations, times(1)).update(
                eq("DELETE FROM task WHERE id = ?"),
                any(PreparedStatementSetter.class)
        );
        Assert.assertEquals(expectedDeletedTasks, receivedDeletedTasks);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteTaskException() {
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";
        when(mockJdbcOperations.update(anyString(), any(PreparedStatementSetter.class)))
                .thenThrow(IncorrectResultSizeDataAccessException.class);
        int receivedDeletedTasks = databaseTasksRepository.deleteTask(taskId);
    }

    @Test
    public void testUpdateTask() {
        Task expectedTask = mock(Task.class);

        when(mockJdbcOperations.update(anyString(), any(PreparedStatementSetter.class))).thenReturn(1);

        Task receivedTask = databaseTasksRepository.updateTask(expectedTask);

        verify(mockJdbcOperations, times(1)).update(
                eq("UPDATE task SET text = ?, status = ?, updateAT = ? WHERE id = ?"),
                any(PreparedStatementSetter.class)
        );
        Assert.assertEquals(receivedTask, expectedTask);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testUpdateTaskException() {
        Task expectedTask = mock(Task.class);
        when(mockJdbcOperations.update(anyString(), any(PreparedStatementSetter.class)))
                .thenThrow(IncorrectResultSizeDataAccessException.class);
        Task receivedTask = databaseTasksRepository.updateTask(expectedTask);
    }

}
