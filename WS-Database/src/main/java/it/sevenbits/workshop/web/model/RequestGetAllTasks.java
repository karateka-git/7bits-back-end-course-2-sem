package it.sevenbits.workshop.web.model;

import com.fasterxml.jackson.annotation.*;
import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.web.service.validation.EnumConstraint;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * model for request get all tasks
 */
public class RequestGetAllTasks {
    @EnumConstraint(enumClass = EnumValues.EnumStatus.class)
    private String status = "inbox";

    @EnumConstraint(enumClass = EnumValues.EnumOrder.class)
    private String order = "desc";

    private int page = 1;

    @Min(10)
    @Max(50)
    private int size = 10;

    /**
     *
     * @param status - status
     * @param order - order for pagination
     * @param page - page for pagination
     * @param size - size for pagination
     */
    @JsonCreator
    public RequestGetAllTasks(
            final @JsonProperty(value = "status") String status,
            final @JsonProperty(value = "order") String order,
            final @JsonProperty(value = "page") Integer page,
            final @JsonProperty(value = "size") Integer size
    ) {
        if (status != null) {
            this.status = status;
        }
        if (order != null) {
            this.order = order;
        }
        if (page != null) {
            this.page = page;
        }
        if (size != null) {
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
