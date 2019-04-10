package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Meta;
import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.service.TaskMapper;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;
import it.sevenbits.workshop.web.service.ServiceCurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

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
    public List<Integer> getCountTasks(RequestGetAllTasks requestBody) {
        String sql = String.format("SELECT count(*) as total FROM task " +
                "where status=%s ", requestBody.getStatus());
//        PreparedStatementSetter preparedStatementSetter = preparedStatement ->
//                preparedStatement.setString(1, requestBody.getStatus());
        return jdbcOperations.query(sql, (resultSet, i) -> resultSet.getInt(1));
    }

    @Override
    public List<Task> getAllTasks(RequestGetAllTasks requestBody) {
        String sql = String.format("SELECT id, text, status, createdAT, updateAT FROM task " +
                    "where status=? " +
                    "order by createdAT %s " +
                    "limit ? offset ?", requestBody.getOrder());

        int getOffset = (requestBody.getPage()-1)*requestBody.getSize();
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, requestBody.getStatus());
            preparedStatement.setInt(2, requestBody.getSize());
            preparedStatement.setInt(3, getOffset);
        };

        return jdbcOperations.query(sql, preparedStatementSetter, ((resultSet, i) -> {
            String idd = resultSet.getString(1);
            String text = resultSet.getString(2);
            String status = resultSet.getString(3);
            String DateCreate = resultSet.getString(4);
            String DateUpdate = resultSet.getString(5);
            return new Task(idd, text, status, DateCreate, DateUpdate);
        }));
    }

    @Override
    public Task getTask(String id) throws IndexOutOfBoundsException {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT id, text, status, createdAT, updateAT FROM task WHERE id = ?",
                    ((resultSet, i) -> {
                        String idd = resultSet.getString(1);
                        String text = resultSet.getString(2);
                        String status = resultSet.getString(3);
                        String DateCreate = resultSet.getString(4);
                        String DateUpdate = resultSet.getString(5);
                        return new Task(idd, text, status, DateCreate, DateUpdate);
                    })
                    , id);
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

        String sql = "INSERT INTO task (id, text, status, createdAT, updateAT) VALUES (?, ?, ?, ?, ?)";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, text);
            preparedStatement.setString(3, status);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(dateCreate));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(dateUpdate));
        };
        int rows = jdbcOperations.update(sql, preparedStatementSetter);
        return task;
    }

    @Override
    public int deleteTask(String id) throws IndexOutOfBoundsException {
        try {
            String sql = "DELETE FROM task WHERE id = ?";
            PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
                preparedStatement.setString(1, id);
            };
            return jdbcOperations.update(sql, preparedStatementSetter);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IndexOutOfBoundsException("Task not found");
        }
    }

    @Override
    public Task updateTask(Task task) throws IndexOutOfBoundsException {
        try {
            task.setUpdateAT(ServiceCurrentDate.getCurrentDate());

            String sql = "UPDATE task SET text = ?, status = ?, updateAT = ? WHERE id = ?";
            PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
                preparedStatement.setString(1, task.getText());
                preparedStatement.setString(2, task.getStatus());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getUpdateAT()));
                preparedStatement.setString(4, task.getId());
            };
            int rows = jdbcOperations.update(sql, preparedStatementSetter);
            return task;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IndexOutOfBoundsException("Task not found");
        }
    }
}
