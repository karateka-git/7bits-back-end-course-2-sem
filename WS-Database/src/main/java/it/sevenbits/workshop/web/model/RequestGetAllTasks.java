package it.sevenbits.workshop.web.model;

import com.fasterxml.jackson.annotation.*;
import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.web.service.validation.EnumConstraint;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

public class RequestGetAllTasks {
    @EnumConstraint(enumClass = EnumValues.EnumStatus.class)
    private String status = "inbox";

    @EnumConstraint(enumClass = EnumValues.EnumOrder.class)
    private String order = "desc";

    private int page = 1;

    @Min(10)
    @Max(50)
    private int size = 10;

    @JsonCreator
    public RequestGetAllTasks(
            @JsonProperty(value = "status") String status,
            @JsonProperty(value = "order") String order,
            @JsonProperty(value = "page") Integer page,
            @JsonProperty(value = "size") Integer size
    ) {
        if (status!=null) {
            this.status = status;
        }
        if (order!=null) {
            this.order = order;
        }
        if (page!=null) {
            this.page = page;
        }
        if (size!=null) {
            this.size = size;
        }
    }

    public String getStatus() {
        return status;
    }

    public String getOrder() {
        return order;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
