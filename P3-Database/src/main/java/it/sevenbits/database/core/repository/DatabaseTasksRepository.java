package it.sevenbits.database.core.repository;

import it.sevenbits.database.core.model.EnumValues;
import it.sevenbits.database.core.model.Task;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseTasksRepository implements TaskRepository {
    private JdbcOperations jdbcOperations;
    public DatabaseTasksRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    private long getNextId() {
        return jdbcOperations.queryForObject(
                "select nextval('task_id_seq')", Long.class);
    }

    @Override
    public List<Task> getAllItems() {
        return jdbcOperations.query(
                "SELECT id, name, status FROM task",
                new RowMapper<Task>() {
                    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
                        long id = resultSet.getLong(1);
                        String text = resultSet.getString(2);
                        String status = resultSet.getString(3);
                        return new Task(id, text, status);
                    }
                });
    }

    @Override
    public Task getTask(String id) {
        return null;
    }

    @Override
    public Task create(String text) {
        long id = getNextId();
        String status = EnumValues.EnumStatus.inbox.toString();
        Task task = new Task(id, text, status);
        int rows = jdbcOperations.update(
                "INSERT INTO task (id, text, status) VALUES (?, ?, ?)",
                id, text, status
        );
        return task;  // or select from DB
    }

    @Override
    public Task deleteTask(String id) {
        return null;
    }
}
