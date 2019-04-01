package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.core.model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class TestDatabaseTasksRepository {
    private JdbcOperations jdbcOperations;
    private DatabaseTasksRepository databaseTasksRepository;

    @Before
    public void setup() {


        databaseTasksRepository = new DatabaseTasksRepository(jdbcOperations);
    }

    @Test
    public void testGetAllItems() {


        Task task = mock(Task.class);
        when(new Task()).thenReturn(task);
        Task newTask = databaseTasksRepository.create(anyString());
        Assert.assertEquals(task, newTask);
    }

}
