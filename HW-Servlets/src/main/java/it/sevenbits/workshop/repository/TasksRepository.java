package it.sevenbits.workshop.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TasksRepository {
    private static TasksRepository instance;
    private static int counter = 0;
    private HashMap<Integer, String> tasks;

    private TasksRepository() {
        tasks = new HashMap<Integer, String>();
        addTask("tratatata");
    }

    public static TasksRepository getInstance() {
        if (instance == null) {
            instance = new TasksRepository();
        }
        return instance;
    }

    public void addTask(String name) {
        synchronized (this) {
            tasks.put(counter, name);
            counter++;
        }

    }

    public String getTask(int id) {
        return tasks.get(id);
    }

    public Set<Map.Entry<Integer, String>> getTasks() {
        return tasks.entrySet();
    }

    public void deletedTask(int id) {
        synchronized (this) {
            tasks.remove(id);
        }
    }

    public int getSize() {
        return tasks.size()-1;
    }
}
