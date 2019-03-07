package it.sevenbits.practice.core.repository;

import it.sevenbits.practice.core.model.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> getAllItems();
    Task create(Task task);
}
