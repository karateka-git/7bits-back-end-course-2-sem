package it.sevenbits.homework.repository;

import it.sevenbits.homework.task.Task;

import java.util.concurrent.CopyOnWriteArrayList;

public class TasksRepository {
    private static TasksRepository instance;
    private CopyOnWriteArrayList<Task> tasks;

    private TasksRepository() {
        tasks = new CopyOnWriteArrayList<Task>();
        addTask("tratatata");
    }

    public static TasksRepository getInstance() {
        if (instance == null) {
            instance = new TasksRepository();
        }
        return instance;
    }

    public void addTask(String name) {
        tasks.add(new Task(name));
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public int getSize() {
        return tasks.size();
    }
}
