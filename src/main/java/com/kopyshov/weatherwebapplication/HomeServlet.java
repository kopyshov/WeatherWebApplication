package com.kopyshov.weatherwebapplication;

import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

@WebServlet({"/home"})
public class HomeServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        if (Objects.nonNull(user)) {
            String username = user.getUsername();
            context.setVariable("username", username);
            templateEngine.process("weather", context, response.getWriter());
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
