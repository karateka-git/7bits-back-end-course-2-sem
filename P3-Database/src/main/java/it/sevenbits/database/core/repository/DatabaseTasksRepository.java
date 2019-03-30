package it.sevenbits.database.core.repository;

import it.sevenbits.database.core.model.EnumValues;
import it.sevenbits.database.core.model.Task;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    private String getCurrentDate() {
        Date utilDate = new Date();
        Timestamp sq = new Timestamp(utilDate.getTime());
        return sq.toString();
    }

    @Override
    public List<Task> getAllItems() {
        return jdbcOperations.query(
                "SELECT id, text, status, createdAT FROM task",
                (resultSet, i) -> {
                    long id = resultSet.getLong(1);
                    String text = resultSet.getString(2);
                    String status = resultSet.getString(3);
                    String date = resultSet.getString(4);
                    return new Task(id, text, status, date);
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
        String date = getCurrentDate();
        System.out.print("\n\n\n\n\n\n" + date + "\n\n\n\n\n\n");
        Task task = new Task(id, text, status, date);
        int rows = jdbcOperations.update(
                "INSERT INTO task (id, text, status, createdAT) VALUES (?, ?, ?, ?)",
                id, text, status, Timestamp.valueOf(date)
        );

        return task;  // or select from DB
    }

    @Override
    public Task deleteTask(String id) {
        return null;
    }
}
