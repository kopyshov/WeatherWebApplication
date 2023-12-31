package com.kopyshov.weatherwebapplication.auth.servlets;

import com.kopyshov.weatherwebapplication.common.dao.UserDAO;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

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
        if (username.isBlank() && password.isBlank()) {
            context.setVariable("error", "Недопустимы пустые поля. Введите логин и пароль");
        }
        Optional<UserData> user = UserDAO.INSTANCE.find(username);
        if (user.isPresent()) {
            if(BCrypt.checkpw(password, user.get().getPassword())) {
                authService.openAccess(user.get(), request, response);
                response.sendRedirect(request.getContextPath() + "/weather");
            } else {
                context.setVariable("error", "Неверный пароль");
            }
        } else {
            context.setVariable("error", "Неизвестный пользователь. Попробуйте снова");
        }
        templateEngine.process("login", context, response.getWriter());
    }

    public void destroy() {
    }
}
