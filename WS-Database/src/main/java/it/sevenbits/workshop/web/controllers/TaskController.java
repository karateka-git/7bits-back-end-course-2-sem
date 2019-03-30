package it.sevenbits.workshop.web.controllers;


import it.sevenbits.workshop.web.model.RequestCreateTask;
import it.sevenbits.workshop.web.model.RequestUpdateTaskValues;
import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.repository.TaskRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository tasksRepository){
        this.taskRepository = tasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Task> list() {
        return taskRepository.getAllItems();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Task> create(@Valid @RequestBody RequestCreateTask requestBody) {
        Task createdTask = taskRepository.create(requestBody.getText());
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build().toUri();
        return ResponseEntity.created(location).body(createdTask);
    }

    @RequestMapping(value = "/{taskID}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getTask(@PathVariable("taskID") long id) {
        try {
            Task task = taskRepository.getTask((id));
            return ResponseEntity.status(HttpStatus.OK).body(task);
        } catch (IncorrectResultSizeDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{taskID}",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity updateTask(@PathVariable("taskID") long id,
                                     @Valid @RequestBody RequestUpdateTaskValues requestBody) {
        try {
            Task task = taskRepository.getTask((id));
            if (!requestBody.getText().equals("null")) {
                task.setText(requestBody.getText());
            }
            if (!requestBody.getStatus().equals("null")) {
                task.setStatus(requestBody.getStatus());
            }
            taskRepository.updateTask(task);
            return ResponseEntity.status(HttpStatus.OK).body(task);
        } catch (IncorrectResultSizeDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/{taskID}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTask(@PathVariable("taskID") long id) {
        try {
            Task task = taskRepository.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).body(task);
        } catch (IncorrectResultSizeDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

