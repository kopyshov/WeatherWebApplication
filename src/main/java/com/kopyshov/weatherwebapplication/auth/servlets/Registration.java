package com.kopyshov.weatherwebapplication.auth.servlets;

import com.kopyshov.weatherwebapplication.common.dao.UserDAO;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/registration")
public class Registration extends AuthServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        templateEngine.process("registration", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            String salt = BCrypt.gensalt();
            String hashPass = BCrypt.hashpw(password, salt);

            UserData user = new UserData(username, hashPass);
            UserDAO.INSTANCE.save(user);
            authService.openAccess(user, request, response);
            response.sendRedirect(request.getContextPath() + "/weather");
        } catch (PersistenceException e) {
            context.setVariable("error", "User with this login exists");
            templateEngine.process("registration", context, response.getWriter());
        }
    }
}
