package it.sevenbits.database.core.model;

public class Status {
    private String status;

    public Status() {
        this.status = EnumValues.EnumStatus.inbox.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
