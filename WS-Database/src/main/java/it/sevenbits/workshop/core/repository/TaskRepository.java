package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;

import java.util.List;

/**
 * interface repository task
 */
public interface TaskRepository {
    /**
     *
     * @param requestBody - model for request getAllTasks
     * @return int - number of tasks
     */
    List<Integer> getCountTasks(RequestGetAllTasks requestBody);

    /**
     *
     * @param requestBody - model for request getAllTasks
     * @return List<Task> - list tasks
     */
    List<Task> getAllTasks(RequestGetAllTasks requestBody);

    /**
     *
     * @param id - UUID task
     * @return - task
     * @throws IndexOutOfBoundsException - exception task not found
     */
    Task getTask(String id) throws IndexOutOfBoundsException;

    /**
     *
     * @param task - task for create
     * @return - created task
     */
    Task createTask(Task task);

    /**
     *
     * @param id - UUID task
     * @return int - number deleted of tasks
     * @throws IndexOutOfBoundsException - exception task not found
     */
    int deleteTask(String id) throws IndexOutOfBoundsException;

    /**
     *
     * @param task - task for update
     * @return - updated task
     * @throws IndexOutOfBoundsException - exception task not found
     */
    Task updateTask(Task task) throws IndexOutOfBoundsException;
}
