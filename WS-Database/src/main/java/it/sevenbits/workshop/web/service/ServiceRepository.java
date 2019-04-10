package it.sevenbits.workshop.web.service;

import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.core.model.Meta;
import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.repository.TaskRepository;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;
import it.sevenbits.workshop.web.model.RequestUpdateTaskValues;
import java.util.*;

public class ServiceRepository {
    private final TaskRepository taskRepository;

    public ServiceRepository(TaskRepository tasksRepository){
        this.taskRepository = tasksRepository;
    }

    private String getNextId() {
        return UUID.randomUUID().toString();
    }

    public Map getAllTasks(RequestGetAllTasks requestBody) {
        int total = taskRepository.getCountTasks(requestBody).get(0);
        final int lastPage = total%requestBody.getSize() > 0? total/requestBody.getSize()+1:total/requestBody.getSize();
        final int firstPage = 1;
        String first = String.format("/tasks?status=%s&order=%s&page=%d&size=%d",
                requestBody.getStatus(),requestBody.getOrder(), firstPage, requestBody.getSize());
        String last = String.format("/tasks?status=%s&order=%s&page=%d&size=%d",
                requestBody.getStatus(),requestBody.getOrder(), lastPage, requestBody.getSize());
        String next = String.format("/tasks?status=%s&order=%s&page=%d&size=%d",
                requestBody.getStatus(),requestBody.getOrder(),
                requestBody.getPage()+1 > lastPage?lastPage:requestBody.getPage()+1, requestBody.getSize());
        String prev = String.format("/tasks?status=%s&order=%s&page=%d&size=%d",
                requestBody.getStatus(),requestBody.getOrder(),
                requestBody.getPage()-1 < firstPage?firstPage:requestBody.getPage()-1, requestBody.getSize());

        Meta meta = new Meta(total, requestBody.getPage(), requestBody.getSize(), next, prev, first, last);
        List listTask = taskRepository.getAllTasks(requestBody);
        Map <String, Object> resultMap = new HashMap<>();
        resultMap.put("_meta", meta);
        resultMap.put("tasks", listTask);
        return resultMap;
    }

    public Task getTask(String id) throws IndexOutOfBoundsException {
        return taskRepository.getTask(id);
    }

    public Task createTask(String text) {
        String id = getNextId();
        String status = EnumValues.EnumStatus.inbox.toString();
        String date = ServiceCurrentDate.getCurrentDate();
        Task task = new Task(id, text, status, date);
        return taskRepository.createTask(task);
    }

    public int deleteTask(String id) throws IndexOutOfBoundsException {
        return taskRepository.deleteTask(id);
    }

    public Task updateTask(String id, RequestUpdateTaskValues requestBody) throws IndexOutOfBoundsException {
        Task task = getTask((id));
        if (!requestBody.getText().equals("null")) {
            task.setText(requestBody.getText());
        }
        if (!requestBody.getStatus().equals("null")) {
            task.setStatus(requestBody.getStatus());
        }
        return taskRepository.updateTask(task);
    }

}
