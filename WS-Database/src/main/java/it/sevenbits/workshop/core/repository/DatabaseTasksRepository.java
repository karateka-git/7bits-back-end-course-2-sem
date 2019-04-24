package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.service.TaskMapper;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;
import it.sevenbits.workshop.web.service.ServiceCurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.*;
import java.util.List;

/**
 * DatabaseTasksRepository
 */
public class DatabaseTasksRepository implements TaskRepository {
    private JdbcOperations jdbcOperations;

    /**
     *
     * @param jdbcOperations - JdbcOperations
     */
    public DatabaseTasksRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public int getCountTasks(final RequestGetAllTasks requestBody) {
        String sql = String.format("SELECT count(*) as total FROM task " +
                "where status='%s' ", requestBody.getStatus());
        return jdbcOperations.query(sql, (resultSet, i) -> resultSet.getInt(1)).get(0);
    }

    @Override
    public List<Task> getAllTasks(final RequestGetAllTasks requestBody) {
        String sql = String.format("SELECT id, text, status, createdAT, updateAT FROM task " +
                    "where status=? " +
                    "order by createdAT %s " +
                    "limit ? offset ?", requestBody.getOrder());

        int getOffset = (requestBody.getPage() - 1) * requestBody.getSize();
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, requestBody.getStatus());
            preparedStatement.setInt(2, requestBody.getSize());
            preparedStatement.setInt(3, getOffset);
        };

        return jdbcOperations.query(sql, preparedStatementSetter, (resultSet, i) -> taskMapper.mapRow(resultSet, i));
    }

    @Override
    public Task getTask(final String id) throws IndexOutOfBoundsException {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT id, text, status, createdAT, updateAT FROM task WHERE id = ?",
                    (resultSet, i) -> taskMapper.mapRow(resultSet, i),
                    id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IndexOutOfBoundsException("Task not found");
        }
    }

    @Override
    public Task createTask(final Task task) {
        String id = task.getId();
        String text = task.getText();
        String status = task.getStatus();
        String dateCreate = task.getCreatedAT();
        String dateUpdate = task.getUpdateAT();

        String sql = "INSERT INTO task (id, text, status, createdAT, updateAT) VALUES (?, ?, ?, ?, ?)";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, text);
            preparedStatement.setString(3, status);
            preparedStatement.setString(4, dateCreate);
            preparedStatement.setString(5, dateUpdate);
        };
        int rows = jdbcOperations.update(sql, preparedStatementSetter);
        return task;
    }

    @Override
    public int deleteTask(final String id) throws IndexOutOfBoundsException {

        String sql = "DELETE FROM task WHERE id = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, id);
        };
        int rows = jdbcOperations.update(sql, preparedStatementSetter);
        if (rows == 0) {
            throw new IndexOutOfBoundsException("Task not found");
        }
        return rows;
    }

    @Override
    public Task updateTask(final Task task) throws IndexOutOfBoundsException {

        task.setUpdateAT(ServiceCurrentDate.getCurrentDate());

        String sql = "UPDATE task SET text = ?, status = ?, updateAT = ? WHERE id = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, task.getText());
            preparedStatement.setString(2, task.getStatus());
            preparedStatement.setString(3, task.getUpdateAT());
            preparedStatement.setString(4, task.getId());
        };
        int rows = jdbcOperations.update(sql, preparedStatementSetter);
        if (rows == 0) {
            throw new IncorrectResultSizeDataAccessException(0);
        }
        return task;

    }
}
