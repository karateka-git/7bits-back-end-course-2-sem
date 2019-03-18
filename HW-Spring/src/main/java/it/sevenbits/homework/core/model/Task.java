package it.sevenbits.homework.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.homework.core.repository.validation.StatusConstraint;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.UUID;

public class Task {
    private final UUID id;
    private enum enumStatus {inbox, done}
    @StatusConstraint(enumClass=enumStatus.class)
    private String status;
    @NotNull
    private final String text;

    @JsonCreator
    public Task(@JsonProperty("id") UUID id, @JsonProperty("text") String text, @JsonProperty("status") String status) {
        this.id = id;
        this.text = text;
        this.status = status;

    }
    public UUID getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public String getStatus() {
        return status.toString();
    }
}
