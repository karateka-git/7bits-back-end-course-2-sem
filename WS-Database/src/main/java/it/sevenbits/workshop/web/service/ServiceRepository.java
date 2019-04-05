package it.sevenbits.workshop.web.service;

import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.repository.TaskRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ServiceRepository {
    private final TaskRepository taskRepository;

    public ServiceRepository(TaskRepository tasksRepository){
        this.taskRepository = tasksRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public Task getTask(long id) throws IndexOutOfBoundsException {
        return taskRepository.getTask(id);
    }

    public Task createTask(String text) {
        long id = taskRepository.getNextId();
        String status = EnumValues.EnumStatus.inbox.toString();
        String date = ServiceCurrentDate.getCurrentDate();
        Task task = new Task(id, text, status, date);
        return taskRepository.createTask(task);
    }

    public int deleteTask(long id) throws IndexOutOfBoundsException {
        return taskRepository.deleteTask(id);
    }

    public Task updateTask(Task task) throws IndexOutOfBoundsException {
        return taskRepository.updateTask(task);
    }

}
