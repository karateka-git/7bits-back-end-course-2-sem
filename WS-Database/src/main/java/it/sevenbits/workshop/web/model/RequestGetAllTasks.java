package it.sevenbits.workshop.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
    private String status = EnumValues.EnumStatus.inbox.toString();

    @EnumConstraint(enumClass = EnumValues.EnumOrder.class)
    private String order = EnumValues.EnumOrder.desc.toString();

    private int page = 1;

    @Min(10)
    @Max(50)
    private int size = 25;

    @JsonCreator
    public RequestGetAllTasks(
            @JsonProperty("status") String status,
            @JsonProperty("order") String order,
            @JsonProperty("page") int page,
            @JsonProperty("size") int size
    ) {
        this.status = status;
        this.order = order;
        this.page = page;
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public String getOrder() {
        return order;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }
}
