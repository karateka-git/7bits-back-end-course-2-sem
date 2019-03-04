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

    public int addTask(String name) {
        synchronized (this) {
            int index = counter;
            tasks.put(index, name);
            counter++;
            return index;
        }

    }

    public String getTask(int id) throws NullPointerException {
        if (tasks.get(id).equals("null")){
            throw new NullPointerException("Object does not exist");
        } else {
            return tasks.get(id);
        }
    }

    public Set<Map.Entry<Integer, String>> getTasks() {
        return tasks.entrySet();
    }

    public void deletedTask(int id) throws NullPointerException {
        synchronized (this) {
            if (tasks.get(id).equals("null")){
                throw new NullPointerException("Object does not exist");
            } else {
                tasks.remove(id);
            }
        }
    }

    public int getSize() {
        return tasks.size()-1;
    }
}
