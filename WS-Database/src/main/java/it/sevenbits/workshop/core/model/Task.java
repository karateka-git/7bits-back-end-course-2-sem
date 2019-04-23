package it.sevenbits.workshop.core.model;

/**
 * model task to store in the database
 */
public class Task {
    private final String id;

    private Status status;

    private String text;

    private String createdAT;

    private String updateAT;

    /**
     *
     * @param id - id
     * @param text - text
     * @param status - status
     * @param createdAT - created date
     */
    public Task(final String id, final String text, final String status, final String createdAT) {
        this.id = id;
        this.text = text;
        this.status = new Status(status);
        this.createdAT = createdAT;
        this.updateAT = createdAT;
    }

    /**
     *
     *  @param id - id
     * @param text - text
     * @param status - status
     * @param createdAT - created date
     * @param updateAT - update date
     */
    public Task(final String id, final String text, final String status,
                final String createdAT, final String updateAT) {
        this.id = id;
        this.text = text;
        this.status = new Status(status);
        this.createdAT = createdAT;
        this.updateAT = updateAT;
    }

    public String getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public void setText(final String text) {
        this.text = text;
    }
    public String getStatus() {
        return status.getStatus();
    }

    /**
     *
     * @param status - status
     */
    public void setStatus(final String status) {
        this.status.setStatus(status);
    }
    public String getCreatedAT() {
        return createdAT;
    }
    public void setCreatedAT(final String createdAT) {
        this.createdAT = createdAT;
    }
    public String getUpdateAT() {
        return updateAT;
    }
    public void setUpdateAT(final String updateAT) {
        this.updateAT = updateAT;
    }
}
