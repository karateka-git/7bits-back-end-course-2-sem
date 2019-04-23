package it.sevenbits.workshop.core.model;

/**
 * model for status
 */
public class Status {
    private String status;

    /**
     *
     * @param status - status enum
     */
    public Status(final String status) {
        this.status = EnumValues.EnumStatus.valueOf(status).toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
