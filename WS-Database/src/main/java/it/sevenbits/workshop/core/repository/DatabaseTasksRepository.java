package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.web.service.ServiceCurrentDate;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.*;
import java.util.List;

public class DatabaseTasksRepository implements TaskRepository {
    private JdbcOperations jdbcOperations;
    public DatabaseTasksRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Task> getAllTasks() {
        return jdbcOperations.query(
                "SELECT id, text, status, createdAT, updateAT FROM task",
                (resultSet, i) -> {
                    String id = resultSet.getString(1);
                    String text = resultSet.getString(2);
                    String status = resultSet.getString(3);
                    String DateCreate = resultSet.getString(4);
                    String DateUpdate = resultSet.getString(5);
                    return new Task(id, text, status, DateCreate, DateUpdate);
                });
    }

    @Override
    public Task getTask(String id) throws IndexOutOfBoundsException {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT id, text, status, createdAT, updateAT FROM task WHERE id = ?",
                    (resultSet, i) -> {
                        String rowId = resultSet.getString(1);
                        String rowText = resultSet.getString(2);
                        String rowStatus = resultSet.getString(3);
                        String rowDateCreate = resultSet.getString(4);
                        String rowDateUpdate = resultSet.getString(5);
                        return new Task(rowId, rowText, rowStatus, rowDateCreate, rowDateUpdate);
                    },id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IndexOutOfBoundsException("Task not found");
        }
    }

    @Override
    public Task createTask(Task task) {
        String id = task.getId();
        String text = task.getText();
        String status = task.getStatus();
        String dateCreate = task.getCreatedAT();
        String dateUpdate = task.getUpdateAT();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            String sql = "INSERT INTO task (id, text, status, createdAT, updateAT) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, text);
            preparedStatement.setString(3, status);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(dateCreate));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(dateUpdate));
            return preparedStatement;
        };
        int rows = jdbcOperations.update(preparedStatementCreator);
        return task;
    }

    @Override
    public int deleteTask(String id) throws IndexOutOfBoundsException {
        try {
            PreparedStatementCreator preparedStatementCreator = connection -> {
                String sql = "DELETE FROM task WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, id);
                return preparedStatement;
            };
            return jdbcOperations.update(preparedStatementCreator);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IndexOutOfBoundsException("Task not found");
        }
    }

    //DO IT USE MAP
    @Override
    public Task updateTask(Task task) throws IndexOutOfBoundsException {
        try {
            task.setUpdateAT(ServiceCurrentDate.getCurrentDate());
            PreparedStatementCreator preparedStatementCreator = connection -> {
                String sql = "UPDATE task SET text = ?, status = ?, updateAT = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, task.getText());
                preparedStatement.setString(2, task.getStatus());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getUpdateAT()));
                preparedStatement.setString(4, task.getId());
                return preparedStatement;
            };
            int rows = jdbcOperations.update(preparedStatementCreator);
            return task;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IndexOutOfBoundsException("Task not found");
        }
    }
}
