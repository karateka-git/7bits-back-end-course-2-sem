package it.sevenbits.homework.servlets;

import it.sevenbits.homework.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class Tasks extends HttpServlet {
    private TasksRepository repository = TasksRepository.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("[");
        for (int i = 0; i<repository.getSize(); i++) {
            response.getWriter().write(String.format("{ \"id\":\"%d\", \"name\":\"%s\" }",
                    repository.getTask(i).getID(),repository.getTask(i).getName()));
        }
        response.getWriter().write("]");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("application/x-www-form-urlencoded");
        request.setCharacterEncoding("UTF-8");

        response.setStatus(201, "Created task");
        response.setCharacterEncoding("UTF-8");
        BufferedReader reader = new BufferedReader(request.getReader());
        StringBuilder stringBuilder = new StringBuilder();
        while(reader.ready()) {
            stringBuilder.append(reader.readLine());
        }
        repository.addTask(stringBuilder.toString());

    }
}