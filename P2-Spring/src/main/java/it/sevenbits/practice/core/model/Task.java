package it.sevenbits.practice.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Task {
    private final UUID id;
    @NotNull
    private final String text;

    @JsonCreator
    public Task(@JsonProperty("id") UUID id, @JsonProperty("text") String text) {
        this.id = id;
        this.text = text;
    }
    public UUID getId() {
        return id;
    }
    public String getText() {
        return text;
    }
}
