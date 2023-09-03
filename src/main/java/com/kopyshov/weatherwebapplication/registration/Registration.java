package com.kopyshov.weatherwebapplication.registration;

import com.kopyshov.weatherwebapplication.auth.UserSessionRegistration;
import com.kopyshov.weatherwebapplication.auth.dao.UserDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class Registration extends BasicServlet implements UserSessionRegistration {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        templateEngine.process("registration", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserData user = new UserData();
        user.setUsername(username);
        user.setPassword(password);

        UserDAO.INSTANCE.save(user);

        registerUserSession(user, request, response);

        response.sendRedirect(request.getContextPath() + "/home");
    }
}
