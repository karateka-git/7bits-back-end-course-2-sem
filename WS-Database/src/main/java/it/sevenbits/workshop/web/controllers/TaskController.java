package it.sevenbits.workshop.web.controllers;


import it.sevenbits.workshop.web.model.RequestCreateTask;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;
import it.sevenbits.workshop.web.model.RequestUpdateTaskValues;
import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.web.service.ServiceRepository;
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
    private final ServiceRepository serviceRepository;

    public TaskController(ServiceRepository serviceRepository){
        this.serviceRepository = serviceRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List> getAllTasks(@Valid @ModelAttribute() RequestGetAllTasks requestBody) {
        List answer = serviceRepository.getAllTasks(requestBody);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @RequestMapping(value = "/{taskID}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getTask(@PathVariable("taskID") String id) {
        try {
            Task task = serviceRepository.getTask((id));
            return ResponseEntity.status(HttpStatus.OK).body(task);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@Valid @RequestBody RequestCreateTask requestBody) {
        Task createdTask = serviceRepository.createTask(requestBody.getText());
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build().toUri();
        ResponseEntity.status(HttpStatus.CREATED);
        return ResponseEntity.created(location).body(createdTask);
    }

    @RequestMapping(value = "/{taskID}",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity updateTask(@PathVariable("taskID") String id,
                                     @Valid @RequestBody RequestUpdateTaskValues requestBody) {
        try {
            Task task = serviceRepository.updateTask(id, requestBody);
            return ResponseEntity.status(HttpStatus.OK).body(task);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }

    }

    @RequestMapping(value = "/{taskID}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTask(@PathVariable("taskID") String id) {
        try {
            int answer = serviceRepository.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }
}

