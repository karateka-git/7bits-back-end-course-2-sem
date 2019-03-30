package it.sevenbits.database.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.database.core.model.EnumValues;
import it.sevenbits.database.web.service.validation.EnumConstraint;

public class RequestUpdateTaskValues {

    private String text;

    @EnumConstraint(enumClass = EnumValues.EnumStatus.class)
    private String status;

    @JsonCreator
    public RequestUpdateTaskValues(@JsonProperty("status") String status, @JsonProperty("text") String text) {
        this.status = status;
        this.text = text;
    }

    public String getStatus() {
        return status == null? "null":status;
    }

    public String getText() {
        return text == null? "null":text;
    }
}
