package com.kopyshov.weatherwebapplication;

import com.kopyshov.weatherwebapplication.common.BasicServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet({"/home"})
public class HomeServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object username = request.getAttribute("username");
        if (Objects.nonNull(username)) {
            username = username.toString();
            context.setVariable("user", username);
            templateEngine.process("home", context, response.getWriter());
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
