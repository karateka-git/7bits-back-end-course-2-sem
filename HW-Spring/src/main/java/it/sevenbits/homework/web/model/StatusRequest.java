package it.sevenbits.homework.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.homework.core.model.EnumValues;
import it.sevenbits.homework.core.model.Status;
import it.sevenbits.homework.core.repository.validation.EnumConstraint;

import javax.validation.constraints.NotNull;
import java.util.EnumSet;
import java.util.Set;

public class StatusRequest {

    @EnumConstraint(enumClass = EnumValues.EnumStatus.class)
    private String status;

    public StatusRequest(@JsonProperty("status") String status) {
        this.status = status;

    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
