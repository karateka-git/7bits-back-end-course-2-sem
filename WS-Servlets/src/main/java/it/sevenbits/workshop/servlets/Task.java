package it.sevenbits.workshop.servlets;

import it.sevenbits.workshop.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * catch NumberFormatException - if request.getParameter() return null - Bad Request;
 * catch NullPointerException - if repository.getTask(id) return null - Not Found;
 */
public class Task extends HttpServlet {
    private TasksRepository repository = TasksRepository.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("taskId"));
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{ \"id\":\"%d\", \"name\":\"%s\" }",
                    id,repository.getTask(id)));
        } catch (NumberFormatException e) {
            response.setStatus(400);
        } catch (NullPointerException e) {
            response.setStatus(404);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("taskId"));
            repository.deletedTask(Integer.parseInt(request.getParameter("taskId")));
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{ \"id\":\"%d\"}", id));
        } catch (NumberFormatException e) {
            response.setStatus(400);
        }  catch (NullPointerException e) {
            response.setStatus(404);
        }
    }
}
