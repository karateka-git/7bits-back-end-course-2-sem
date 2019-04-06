package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;

import java.util.List;

public interface TaskRepository {
    List<Task> getAllTasks(RequestGetAllTasks requestBody);
    Task getTask(String id) throws IndexOutOfBoundsException;
    Task createTask(Task task);
    int deleteTask(String id) throws IndexOutOfBoundsException;
    Task updateTask(Task task) throws IndexOutOfBoundsException;
}
