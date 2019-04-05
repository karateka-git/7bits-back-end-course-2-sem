package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Task;

import java.util.List;

public interface TaskRepository {
    long getNextId();
    List<Task> getAllTasks();
    Task getTask(long id) throws IndexOutOfBoundsException;
    Task createTask(Task task);
    int deleteTask(long id) throws IndexOutOfBoundsException;
    Task updateTask(Task task) throws IndexOutOfBoundsException;
}
