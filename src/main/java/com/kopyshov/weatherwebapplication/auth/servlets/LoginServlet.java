package com.kopyshov.weatherwebapplication.auth.servlets;

import com.kopyshov.weatherwebapplication.auth.dao.UserDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet({"/login"})
public class LoginServlet extends AuthServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        templateEngine.process("login", context, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Optional<UserData> user = UserDAO.INSTANCE.find(username, password);
        if (user.isPresent()) {
            authService.openAccess(user.get(), request, response);
            response.sendRedirect(request.getContextPath() + "/weather");
        } else {
            context.setVariable("error", "Unknown user, try again");
            templateEngine.process("login", context, response.getWriter());
        }
    }

    public void destroy() {
    }
}
