package it.sevenbits.workshop.web.controllers;

import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.repository.TaskRepository;
import it.sevenbits.workshop.web.model.RequestCreateTask;
import it.sevenbits.workshop.web.model.RequestUpdateTaskValues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.*;


public class TestTaskController {
    private TaskRepository mockTaskRepository;
    private TaskController taskController;

    @Before
    public void setup() {
        mockTaskRepository = mock(TaskRepository.class);
        taskController = new TaskController(mockTaskRepository);
    }

    @Test
    public void testGetAllItems() {
        List<Task> mockTasks = mock(List.class);
        when(mockTaskRepository.getAllItems()).thenReturn(mockTasks);

        ResponseEntity<List<Task>> answer =  taskController.getAllItems();
        verify(mockTaskRepository, times(1)).getAllItems();
        Assert.assertEquals(HttpStatus.OK, answer.getStatusCode());
        Assert.assertSame(mockTasks, answer.getBody());
    }

    @Test
    public void testGetTask() {
        long taskId = 1;
        Task mockTask = mock(Task.class);
        when(mockTaskRepository.getTask(anyLong())).thenReturn(mockTask);

        ResponseEntity answer = taskController.getTask(taskId);
        verify(mockTaskRepository, times(1)).getTask(taskId);
        Assert.assertEquals(HttpStatus.OK, answer.getStatusCode());
        Assert.assertSame(mockTask, answer.getBody());
    }

    @Test
    public void testGetTaskException() {
        long taskId = 1;
        String stringException = "Task not found";
        when(mockTaskRepository.getTask(anyLong())).thenThrow(new IndexOutOfBoundsException(stringException));
        ResponseEntity answer = taskController.getTask(taskId);
        Assert.assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
        Assert.assertEquals(stringException, answer.getBody());
    }

    @Test
    public void testCreateTask() throws URISyntaxException {
        long mockId = 1;
        String mockText="mockText";
        URI location = new URI(String.format("/tasks/%d", mockId));
        RequestCreateTask requestBody = mock(RequestCreateTask.class);
        when(requestBody.getText()).thenReturn(mockText);

        Task mockTask = mock(Task.class);
        when(mockTask.getId()).thenReturn(mockId);
        when(mockTaskRepository.create(mockText)).thenReturn(mockTask);
        ResponseEntity answer = taskController.create(requestBody);

        Assert.assertEquals(HttpStatus.CREATED, answer.getStatusCode());
        Assert.assertSame(mockTask, answer.getBody());
        Assert.assertEquals(location, answer.getHeaders().getLocation());
    }

    @Test
    public void testUpdateTask() {
        long mockId = 1;
        String mockText="initText";
        String mockStatus="inbox";
        RequestUpdateTaskValues requestBody = mock(RequestUpdateTaskValues.class);
        when(requestBody.getText()).thenReturn(mockText);
        when(requestBody.getStatus()).thenReturn(mockStatus);

        Task mockTask = mock(Task.class);

        when(mockTaskRepository.getTask(mockId)).thenReturn(mockTask);
        when(mockTaskRepository.updateTask(mockTask)).thenReturn(mockTask);
        ResponseEntity answer = taskController.updateTask(mockId, requestBody);

        Assert.assertEquals(HttpStatus.OK, answer.getStatusCode());
        Assert.assertSame(mockTask, answer.getBody());
    }

    @Test
    public void testUpdateTaskException() {
        String stringException = "Task not found";
        RequestUpdateTaskValues requestBody = mock(RequestUpdateTaskValues.class);
        when(mockTaskRepository.getTask(anyLong())).thenThrow(new IndexOutOfBoundsException(stringException));
        ResponseEntity answer = taskController.updateTask(anyLong(), requestBody);
        Assert.assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
        Assert.assertEquals(stringException, answer.getBody());
    }

    @Test
    public void testDeleteTask() {
        Task mockTask = mock(Task.class);

        when(mockTaskRepository.getTask(anyLong())).thenReturn(mockTask);
        when(mockTaskRepository.deleteTask(anyLong())).thenReturn(mockTask);
        ResponseEntity answer = taskController.deleteTask(anyLong());

        Assert.assertEquals(HttpStatus.OK, answer.getStatusCode());
        Assert.assertSame(mockTask, answer.getBody());
    }

    @Test
    public void testDeleteTaskException() {
        String stringException = "Task not found";
        when(mockTaskRepository.deleteTask(anyLong())).thenThrow(new IndexOutOfBoundsException(stringException));
        when(mockTaskRepository.getTask(anyLong())).thenThrow(new IndexOutOfBoundsException(stringException));
        ResponseEntity answer = taskController.deleteTask(anyLong());
        Assert.assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
        Assert.assertEquals(stringException, answer.getBody());
    }
}
