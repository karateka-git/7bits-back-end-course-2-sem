package it.sevenbits.homework.servlets;

import it.sevenbits.homework.repository.CookieRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class ServletCookie extends HttpServlet {
    private CookieRepository cookieRep= CookieRepository.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            UUID id = cookieRep.addCookieUser(name);

            response.setStatus(201);
            Cookie cookie = new Cookie(name, id.toString());
            response.addCookie(cookie);
        } catch (NumberFormatException e) {
            response.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Cookie[] cookies = request.getCookies();
            UUID id = UUID.fromString(cookies[0].getValue());
            String name = cookieRep.getNameCookieUser(id);

            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("Current User is %s", name));

        } catch (NullPointerException e) {
            response.setStatus(404);
        }
    }
}


