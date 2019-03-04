package it.sevenbits.homework.servlets;

import it.sevenbits.homework.repository.CookieRepository;
import it.sevenbits.homework.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ServletTasks extends HttpServlet {
    private TasksRepository repository = TasksRepository.getInstance();
    private CookieRepository cookieRep= CookieRepository.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UUID id = UUID.fromString(request.getHeader("Authorization"));
            cookieRep.getNameCookieUser(id);
        } catch (NullPointerException e){
            response.setStatus(401);
            return;
        }

        response.getWriter().write("[");
        Set<Map.Entry<UUID, String>> tasks = repository.getTasks();
        for (Map.Entry<UUID, String> task : tasks) {
            response.getWriter().write(String.format("{ \"id\":\"%s\", \"name\":\"%s\" }",
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
            UUID id = UUID.fromString(request.getHeader("Authorization"));
            cookieRep.getNameCookieUser(id);
        } catch (NullPointerException e){
            response.setStatus(401);
            return;
        }

        try {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            UUID index = repository.addTask(name);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{ \"id\":\"%s\", \"name\":\"%s\" }",
                    index, repository.getTask(index)));
            response.setStatus(201);
            response.setHeader("Location", String.format("item?id=%s",index));
        } catch (NumberFormatException e) {
            response.setStatus(400);
        }
    }
}