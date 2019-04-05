package it.sevenbits.workshop.core.model;

import org.junit.Assert;
import org.junit.Test;


public class TestTask {
    @Test
    public void testInit() {
        String id = "deea44c7-a180-4898-9527-58db0ed34683";
        String text = "test text";
        String status = "inbox";
        String createdAT = "12-12-12 12:12:12";

        Task task = new Task(id,text,status,createdAT);

        Assert.assertEquals(id, task.getId());
        Assert.assertEquals(text, task.getText());
        Assert.assertEquals(status, task.getStatus());
        Assert.assertEquals(createdAT, task.getCreatedAT());
    }
}
