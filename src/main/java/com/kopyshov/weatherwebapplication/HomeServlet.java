package com.kopyshov.weatherwebapplication;

import com.kopyshov.weatherwebapplication.common.BasicServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"", "/", "/home"})
public class HomeServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getAttribute("username").toString();
        if (username.equals("null")){
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            context.setVariable("user", username);
            templateEngine.process("home", context, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
