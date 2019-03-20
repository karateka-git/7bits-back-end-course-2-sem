package it.sevenbits.homework.web.controllers;


import it.sevenbits.homework.web.model.RequestCreateTask;
import it.sevenbits.homework.web.model.RequestUpdateTaskValues;
import it.sevenbits.homework.core.model.Task;
import it.sevenbits.homework.core.repository.TaskRepository;
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

    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
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
    public ResponseEntity getTask(@PathVariable("taskID") String uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskRepository.getTask(uuid));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{taskID}",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity updateStatus(@PathVariable("taskID") String uuid,
                                       @Valid @RequestBody RequestUpdateTaskValues requestBody) {
        try {
            Task task = taskRepository.getTask(uuid);
            if (!requestBody.getText().equals("null")) {
                task.patchText(requestBody.getText());
            }
            if (!requestBody.getStatus().equals("null")) {
                task.patchStatus(requestBody.getStatus());
            }
            return ResponseEntity.status(HttpStatus.OK).body(task);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/{taskID}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTask(@PathVariable("taskID") String uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskRepository.deleteTask(uuid));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

