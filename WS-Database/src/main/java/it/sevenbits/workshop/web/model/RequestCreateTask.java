package it.sevenbits.workshop.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * model for create a task
 */
public class RequestCreateTask {
    @NotNull
    private final String text;

    /**
     *
     * @param text - text for task
     */
    @JsonCreator
    public RequestCreateTask(final @JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
