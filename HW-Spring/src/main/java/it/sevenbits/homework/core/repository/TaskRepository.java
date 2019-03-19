package it.sevenbits.homework.core.repository;

import it.sevenbits.homework.core.model.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> getAllItems();
    Task getTask(String id);
    Task create(Task task);
}
