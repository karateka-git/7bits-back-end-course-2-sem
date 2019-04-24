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
import java.util.Map;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final ServiceRepository serviceRepository;

    /**
     *
     * @param serviceRepository service repository for taskController
     */
    public TaskController(final ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     *
     * @param requestBody - model for validation a request
     * @return - ResponseEntity<Map> response
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map> getAllTasks(final @Valid @ModelAttribute() RequestGetAllTasks requestBody) {
        Map answer = serviceRepository.getAllTasks(requestBody);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    /**
     *
     * @param id - UUID task
     * @return - ResponseEntity
     */
    @RequestMapping(value = "/{taskID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getTask(final @PathVariable("taskID") String id) {
        try {
            Task task = serviceRepository.getTask((id));
            return ResponseEntity.status(HttpStatus.OK).body(task);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    /**
     *
     * @param requestBody - model for validation a request
     * @return - ResponseEntity
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(final @Valid @RequestBody RequestCreateTask requestBody) {
        Task createdTask = serviceRepository.createTask(requestBody.getText());
        URI location = UriComponentsBuilder.fromPath("/tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build().toUri();
        ResponseEntity.status(HttpStatus.CREATED);
        return ResponseEntity.created(location).body(createdTask);
    }

    /**
     *
     * @param id - UUID task
     * @param requestBody - model for validation a request
     * @return - ResponseEntity
     */
    @RequestMapping(value = "/{taskID}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity updateTask(final @PathVariable("taskID") String id,
                                     final @Valid @RequestBody RequestUpdateTaskValues requestBody) {
        try {
            Task task = serviceRepository.updateTask(id, requestBody);
            return ResponseEntity.status(HttpStatus.OK).body(task);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }

    }

    /**
     *
     * @param id - UUID task
     * @return - ResponseEntity
     */
    @RequestMapping(value = "/{taskID}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTask(final @PathVariable("taskID") String id) {
        try {
            int answer = serviceRepository.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

}

