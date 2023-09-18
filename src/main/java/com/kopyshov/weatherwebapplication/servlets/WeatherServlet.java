package com.kopyshov.weatherwebapplication.servlets;

import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({"/weather"})
public class WeatherServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        if (user != null) {
            context.setVariable("username", user.getUsername());
        }
        templateEngine.process("weather", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        if (user != null) {
            context.setVariable("username", user.getUsername());
        }
        templateEngine.process("weather", context, response.getWriter());
    }
}
