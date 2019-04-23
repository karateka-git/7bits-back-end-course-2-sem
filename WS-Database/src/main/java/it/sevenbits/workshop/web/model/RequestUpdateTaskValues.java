package it.sevenbits.workshop.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.web.service.validation.EnumConstraint;

/**
 * model for update a task
 */
public class RequestUpdateTaskValues {

    private String text;

    @EnumConstraint(enumClass = EnumValues.EnumStatus.class)
    private String status;

    /**
     *
     * @param status - status for update
     * @param text - text for update
     */
    @JsonCreator
    public RequestUpdateTaskValues(final @JsonProperty("status") String status,
                                   final @JsonProperty("text") String text) {
        this.status = status;
        this.text = text;
    }

    public String getStatus() {
        return status == null ? "null" : status;
    }

    public String getText() {
        return text == null ? "null" : text;
    }
}
