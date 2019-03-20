package it.sevenbits.homework.core.model;

import java.util.UUID;

public class Task {
    private final UUID id;

    private Status status;

    private String text;

    public Task(UUID id, String text) {
        this.id = id;
        this.text = text;
        this.status = new Status();
    }

    public UUID getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public void patchText(String text) {
        this.text = text;
    }
    public String getStatus() {
        return status.getStatus();
    }
    public void patchStatus(String status) {
        this.status.setStatus(status);
    }
}
