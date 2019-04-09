package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.service.TaskMapper;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;
import it.sevenbits.workshop.web.service.ServiceCurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Task> getAllTasks(RequestGetAllTasks requestBody) {
        int getOffset = (requestBody.getPage()-1)*requestBody.getSize();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            String sql;
            if (requestBody.getOrder().equals("asc")) {
                sql = "SELECT id, text, status, createdAT, updateAT FROM task " +
                        "where status=? " +
                        "order by createdAT asc " +
                        "limit ? offset ?";
            } else {
                sql = "SELECT id, text, status, createdAT, updateAT FROM task " +
                        "where status=? " +
                        "order by createdAT desc " +
                        "limit ? offset ?";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, requestBody.getStatus());
            preparedStatement.setInt(2, requestBody.getSize());
            preparedStatement.setInt(3, getOffset);
            return preparedStatement;
        };
        return jdbcOperations.query(preparedStatementCreator, taskMapper);
    }

    @Override
    public Task getTask(String id) throws IndexOutOfBoundsException {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT id, text, status, createdAT, updateAT FROM task WHERE id = ?",
                    taskMapper,id);
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
