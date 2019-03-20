package it.sevenbits.database.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.database.core.repository.validation.StatusConstraint;

import javax.validation.constraints.NotNull;

public class Status {
    private enum enumStatus {inbox, done}
    @NotNull
    @StatusConstraint(enumClass = enumStatus.class)
    private String status;

    public Status() {
        this.status = enumStatus.inbox.toString();
    }

    public Status(@JsonProperty("status") String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
