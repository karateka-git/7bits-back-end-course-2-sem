package it.sevenbits.database.core.model;

import java.util.UUID;

public class Task {
    private final long id;

    private Status status;

    private String text;

    private String createdAt;

    public Task(long id, String text, String status, String createdAt) {
        this.id = id;
        this.text = text;
        this.status = new Status(status);
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getStatus() {
        return status.getStatus();
    }
    public void setStatus(String status) {
        this.status.setStatus(status);
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
