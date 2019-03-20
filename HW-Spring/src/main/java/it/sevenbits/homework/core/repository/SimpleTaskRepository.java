package it.sevenbits.homework.core.repository;

import it.sevenbits.homework.core.model.Task;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleTaskRepository implements TaskRepository {
    private CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();

    @Override
    public Task create(String text) {
        Task newTask = new Task(getNextID(), text);
        tasks.add(newTask);
        return newTask;
    }

    @Override
    public List<Task> getAllItems() {
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public Task getTask(String id) {
        for(Task task:tasks) {
            if (id.equals(task.getId().toString())) {
                return task;
            }
        }
        throw new IndexOutOfBoundsException("Task not found.");
    }

    @Override
    public Task deleteTask(String id) {
        for(Task task:tasks) {
            if (id.equals(task.getId().toString())) {
                tasks.remove(task);
                return task;
            }
        }
        throw new IndexOutOfBoundsException("Task not found.");
    }

    private UUID getNextID(){
        return UUID.randomUUID();
    }
}
