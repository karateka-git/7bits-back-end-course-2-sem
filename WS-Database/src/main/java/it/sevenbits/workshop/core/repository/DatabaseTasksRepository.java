package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.core.model.Task;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.*;
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
    public Task getTask(long id) {
        return jdbcOperations.queryForObject(
                "SELECT id, text, status, createdAT FROM task WHERE id = ?",
                (resultSet, i) -> {
                    long rowId = resultSet.getLong(1);
                    String rowText = resultSet.getString(2);
                    String rowStatus = resultSet.getString(3);
                    String rowDate = resultSet.getString(4);
                    return new Task(rowId, rowText, rowStatus, rowDate);
                },
                id);
    }

    @Override
    public Task create(String text) {
        long id = getNextId();
        String status = EnumValues.EnumStatus.inbox.toString();
        String date = getCurrentDate();
        Task task = new Task(id, text, status, date);

        PreparedStatementCreator preparedStatementCreator = connection -> {
            String sql = "INSERT INTO task (id, text, status, createdAT) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, text);
            preparedStatement.setString(3, status);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(date));
            return preparedStatement;
        };
        int rows = jdbcOperations.update(preparedStatementCreator);
        return task;
    }

    @Override
    public Task deleteTask(long id) {
        Task task = getTask(id);
        PreparedStatementCallback preparedStatementCallback = preparedStatement -> {
            preparedStatement.setLong(1, id);
            return preparedStatement.execute();
        };
        jdbcOperations.execute(
                "DELETE FROM task WHERE id = ?",
                preparedStatementCallback
        );
        return task;
    }

    @Override
    public Task updateTask(Task task) {
        task.setCreatedAt(getCurrentDate());
        PreparedStatementCreator preparedStatementCreator = connection -> {
            String sql = "UPDATE task SET text = ?, status = ?, createdAT = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, task.getText());
            preparedStatement.setString(2, task.getStatus());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getCreatedAt()));
            preparedStatement.setLong(4, task.getId());
            return preparedStatement;
        };
        int rows = jdbcOperations.update(preparedStatementCreator);
        return task;
    }
}
