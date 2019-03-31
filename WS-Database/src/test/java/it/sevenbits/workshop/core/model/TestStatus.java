package it.sevenbits.workshop.core.model;

import org.junit.Test;

public class TestStatus {
    @Test(expected = IllegalArgumentException.class)
    public void testExceptionStatus() {
        Status status = new Status("inboxx");
    }
}
