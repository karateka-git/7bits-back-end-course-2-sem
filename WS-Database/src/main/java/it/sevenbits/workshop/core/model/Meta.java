package it.sevenbits.workshop.core.model;

/**
 * model for Meta
 */
public class Meta {
    private int total;
    private int page;
    private int size;
    private String next;
    private String prev;
    private String first;
    private String last;

    /**
     *
     * @param total - total
     * @param page - page
     * @param size - size
     * @param next - next
     * @param prev - prev
     * @param first - first
     * @param last - last
     */
    public Meta(final int total, final int page, final int size,
                final String next, final String prev, final String first, final String last) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.next = next;
        this.prev = prev;
        this.first = first;
        this.last = last;
    }
    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getNext() {
        return next;
    }

    public String getPrev() {
        return prev;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }
}
