package it.sevenbits.database.core.model;

public class Status {
    private String status;

    public Status(String status) {
        //this.status = EnumValues.EnumStatus.inbox.toString();
        this.status = EnumValues.EnumStatus.valueOf(status).toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
