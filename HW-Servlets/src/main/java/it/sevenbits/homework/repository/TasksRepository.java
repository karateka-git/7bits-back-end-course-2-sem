package it.sevenbits.homework.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TasksRepository {
    private static TasksRepository instance;
    private HashMap<UUID, String> tasks;

    private TasksRepository() {
        tasks = new HashMap<UUID, String>();
        addTask("tratatata");
    }

    public static TasksRepository getInstance() {
        if (instance == null) {
            instance = new TasksRepository();
        }
        return instance;
    }

    public UUID addTask(String name) {
        synchronized (this) {
            UUID index = UUID.randomUUID();
            tasks.put(index, name);
            return index;
        }
    }

    public String getTask(UUID id) throws NullPointerException {
        if (tasks.get(id).equals("null")){
            throw new NullPointerException("Object does not exist");
        } else {
            return tasks.get(id);
        }
    }

    public Set<Map.Entry<UUID, String>> getTasks() {
        return tasks.entrySet();
    }

    public UUID deletedTask(UUID id) throws NullPointerException {
        synchronized (this) {
            if (tasks.get(id).equals("null")){
                throw new NullPointerException("Object does not exist");
            } else {
                tasks.remove(id);
                return id;
            }
        }
    }
}
