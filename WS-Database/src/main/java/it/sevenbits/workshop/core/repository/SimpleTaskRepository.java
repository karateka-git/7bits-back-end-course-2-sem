package it.sevenbits.workshop.core.repository;

import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleTaskRepository implements TaskRepository {
    private CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();

    /**
     * @param requestBody - model for request getAllTasks
     * @return int - number of tasks
     */
    @Override
    public int getCountTasks(final RequestGetAllTasks requestBody) {
        return tasks.size();
    }

    /**
     * @param requestBody - model for request getAllTasks
     * @return List<Task> - list tasks
     */
    @Override
    public List<Task> getAllTasks(final RequestGetAllTasks requestBody) {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * @param id - UUID task
     * @return - task
     * @throws IndexOutOfBoundsException - exception task not found
     */
    @Override
    public Task getTask(final String id) throws IndexOutOfBoundsException {
        for (Task task:tasks) {
            if (id.equals(task.getId())) {
                return task;
            }
        }
        throw new IndexOutOfBoundsException("Task not found.");
    }

    /**
     * @param task - task for create
     * @return - created task
     */
    @Override
    public Task createTask(final Task task) {
        tasks.add(task);
        return task;
    }

    /**
     * @param id - UUID task
     * @return int - number deleted of tasks
     * @throws IndexOutOfBoundsException - exception task not found
     */
    @Override
    public int deleteTask(final String id) throws IndexOutOfBoundsException {
        for (Task task:tasks) {
            if (id.equals(task.getId())) {
                tasks.remove(task);
                return 1;
            }
        }
        throw new IndexOutOfBoundsException("Task not found.");
    }

    /**
     * @param task - task for update
     * @return - updated task
     * @throws IndexOutOfBoundsException - exception task not found
     */
    @Override
    public Task updateTask(final Task task) throws IndexOutOfBoundsException {
        for (Task updateTask:tasks) {
            if (task.getId().equals(updateTask.getId())) {
                tasks.remove(updateTask);
                tasks.add(task);
                return task;
            }
        }
        throw new IndexOutOfBoundsException("Task not found.");
    }
}
