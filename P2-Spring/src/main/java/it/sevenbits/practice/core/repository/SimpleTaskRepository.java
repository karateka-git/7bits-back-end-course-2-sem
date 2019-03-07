package it.sevenbits.practice.core.repository;

import it.sevenbits.practice.core.model.Task;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleTaskRepository implements TaskRepository {
    private CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();

    @Override
    public List<Task> getAllItems() {
        return Collections.unmodifiableList(tasks);
    }
    @Override
    public Task create(Task task) {
        Task newTask = new Task(getNextID(), task.getText());
        tasks.add(newTask);
        return newTask;
    }
    private UUID getNextID(){
        return UUID.randomUUID(); //it's very simplified version of id generator
    }
}
