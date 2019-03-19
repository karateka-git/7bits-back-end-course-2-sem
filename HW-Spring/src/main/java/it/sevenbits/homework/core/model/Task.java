package it.sevenbits.homework.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.homework.core.repository.validation.StatusConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

public class Task {
    @Null
    private final UUID id;

    private Status status;

    @NotNull
    private final String text;

    @JsonCreator
    public Task(@JsonProperty("id") UUID id, @JsonProperty("text") String text) {
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
    public String getStatus() {
        return status.getStatus();
    }
}
