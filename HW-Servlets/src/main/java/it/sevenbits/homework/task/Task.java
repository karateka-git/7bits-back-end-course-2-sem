package it.sevenbits.homework.task;

public class Task {
    private static int counter = 0;
    private int id;
    private String name;
    public Task(String name) {
        id = counter;
        this.name = name;
        counter++;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCounter() {
        return counter;
    }
}
