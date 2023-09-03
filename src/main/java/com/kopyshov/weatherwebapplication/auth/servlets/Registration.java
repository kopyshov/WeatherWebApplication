package com.kopyshov.weatherwebapplication.auth.servlets;

import com.kopyshov.weatherwebapplication.auth.UserSessionRegistration;
import com.kopyshov.weatherwebapplication.auth.dao.UserDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class Registration extends BasicServlet implements UserSessionRegistration {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        templateEngine.process("registration", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserData user = new UserData(username, password);
            UserDAO.INSTANCE.save(user);
            registerUserSession(user, request, response);

            response.sendRedirect(request.getContextPath() + "/home");
        } catch (PersistenceException e) {
            context.setVariable("error", "User with this login exists");
            templateEngine.process("registration", context, response.getWriter());
        }
    }
}
