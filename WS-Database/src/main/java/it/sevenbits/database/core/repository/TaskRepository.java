package it.sevenbits.database.core.repository;

import it.sevenbits.database.core.model.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> getAllItems();
    Task getTask(String id);
    Task create(String text);
    Task deleteTask(String id);
}
