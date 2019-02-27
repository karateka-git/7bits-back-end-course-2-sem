package it.sevenbits.workshop.servlets;

import it.sevenbits.workshop.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Task extends HttpServlet {
    private TasksRepository repository = TasksRepository.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("taskId"));
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{ \"id\":\"%d\", \"name\":\"%s\" }",
                    id,repository.getTask(id)));
        } catch (ArrayIndexOutOfBoundsException e) {
            response.setStatus(404, "Task not found");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("taskId"));
        try {
            repository.deletedTask(Integer.parseInt(request.getParameter("taskId")));
            response.setStatus(200, "Deleted task id");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{ \"id\":\"%d\"}", id));
        } catch (NullPointerException e) {
            response.setStatus(404, "Task not found");
        }
    }
}
