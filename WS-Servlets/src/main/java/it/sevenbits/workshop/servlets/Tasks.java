package it.sevenbits.workshop.servlets;

import it.sevenbits.workshop.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Tasks extends HttpServlet {
    private TasksRepository repository = TasksRepository.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("[");
        Set<Map.Entry<Integer, String>> tasks = repository.getTasks();
        for (Map.Entry<Integer, String> task : tasks) {
            response.getWriter().write(String.format("{ \"id\":\"%d\", \"name\":\"%s\" }",
                    task.getKey(), task.getValue()));
        }
        response.getWriter().write("]");
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            int index = repository.addTask(name);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{ \"id\":\"%d\", \"name\":\"%s\" }",
                    index, repository.getTask(index)));
            response.setStatus(201);
            response.setHeader("Location", "item?id=1");
        } catch (NumberFormatException e) {
            response.setStatus(400);
        }
    }
}