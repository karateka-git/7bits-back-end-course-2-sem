package it.sevenbits.homework.servlets;

import it.sevenbits.homework.repository.CookieRepository;
import it.sevenbits.homework.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * catch NumberFormatException - if request.getParameter() return null - Bad Request;
 * catch NullPointerException - if repository.getTask(id) return null - Not Found;
 */
public class ServletTask extends HttpServlet {
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

        try {
            UUID id = UUID.fromString(request.getParameter("taskId"));
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{ \"id\":\"%s\", \"name\":\"%s\" }",
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
            UUID id = UUID.fromString(request.getHeader("Authorization"));
            cookieRep.getNameCookieUser(id);
        } catch (NullPointerException e){
            response.setStatus(401);
            return;
        }

        try {
            UUID id = repository.deletedTask(UUID.fromString(request.getParameter("taskId")));
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{ \"id\":\"%s\"}", id));
        } catch (NumberFormatException e) {
            response.setStatus(400);
        }  catch (NullPointerException e) {
            response.setStatus(404);
        }
    }
}
