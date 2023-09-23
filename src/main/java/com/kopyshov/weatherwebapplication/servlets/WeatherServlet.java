package com.kopyshov.weatherwebapplication.servlets;

import com.kopyshov.weatherwebapplication.auth.dao.UserDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.buisness.Location;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebServlet({"/weather"})
public class WeatherServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        if(user != null) {
            UserData userData = UserDAO.INSTANCE.findById(user.getId());
            if (userData != null) {
                Set<Location> added = userData.getAdded();
                System.out.println(added);
                context.setVariable("user", userData);
                context.setVariable("locations", added);
            }
        }
        templateEngine.process("weather", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
