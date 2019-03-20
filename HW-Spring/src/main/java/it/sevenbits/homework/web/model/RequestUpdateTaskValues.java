package it.sevenbits.homework.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.homework.core.model.EnumValues;
import it.sevenbits.homework.web.service.validation.EnumConstraint;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
