package it.sevenbits.workshop.core.service;

import it.sevenbits.workshop.core.model.Task;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(final ResultSet resultSet, final int i) throws SQLException {
            String id = resultSet.getString("id");
            String text = resultSet.getString("text");
            String status = resultSet.getString("status");
            String dateCreate = resultSet.getString("createdAT");
            String dateUpdate = resultSet.getString("updateAT");
            return new Task(id, text, status, dateCreate, dateUpdate);
    }
}
