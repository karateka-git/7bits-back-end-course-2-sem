package it.sevenbits.workshop.core.service;

import it.sevenbits.workshop.core.model.Task;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
            String id = resultSet.getString(1);
            String text = resultSet.getString(2);
            String status = resultSet.getString(3);
            String DateCreate = resultSet.getString(4);
            String DateUpdate = resultSet.getString(5);
            return new Task(id, text, status, DateCreate, DateUpdate);
    }
}
