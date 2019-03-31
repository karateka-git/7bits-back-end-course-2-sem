package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> getAllItems();
    Task getTask(long id) throws IndexOutOfBoundsException;
    Task create(String text);
    Task deleteTask(long id) throws IndexOutOfBoundsException;
    Task updateTask(Task task) throws IndexOutOfBoundsException;
}
