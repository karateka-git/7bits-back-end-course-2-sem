package it.sevenbits.workshop.core.model;

public class Task {
    private final long id;

    private Status status;

    private String text;

    private String createdAT;

    private String updateAT;

    public Task(long id, String text, String status, String createdAT) {
        this.id = id;
        this.text = text;
        this.status = new Status(status);
        this.createdAT = createdAT;
        this.updateAT = createdAT;
    }

    public Task(long id, String text, String status, String createdAT, String updateAT) {
        this.id = id;
        this.text = text;
        this.status = new Status(status);
        this.createdAT = createdAT;
        this.updateAT = updateAT;
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
    public String getCreatedAT() {
        return createdAT;
    }
    public void setCreatedAT(String createdAT) {
        this.createdAT = createdAT;
    }
    public String getUpdateAT() {
        return updateAT;
    }
    public void setUpdateAT(String updateAT) {
        this.updateAT = updateAT;
    }
}
