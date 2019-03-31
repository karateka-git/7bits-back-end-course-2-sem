package it.sevenbits.workshop.core.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class TestTask {
    @Test
    public void testInit() {
        long id = 1;
        String text = "test text";
        String status = "inbox";
        String createdAT = "12-12-12 12:12:12";

        Task task = new Task(id,text,status,createdAT);

        Assert.assertEquals(id, task.getId());
        Assert.assertEquals(text, task.getText());
        Assert.assertEquals(status, task.getStatus());
        Assert.assertEquals(createdAT, task.getCreatedAt());
    }
}
