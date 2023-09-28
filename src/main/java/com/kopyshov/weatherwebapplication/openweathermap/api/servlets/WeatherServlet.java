package com.kopyshov.weatherwebapplication.openweathermap.api.servlets;

import com.kopyshov.weatherwebapplication.common.dao.UserDAO;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.common.entities.Location;
import com.kopyshov.weatherwebapplication.common.dao.LocationDAO;
import com.kopyshov.weatherwebapplication.BasicServlet;
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
                context.setVariable("user", userData);
                context.setVariable("locations", added);
            }
        }
        templateEngine.process("weather", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        String locationId = request.getParameter("location");
        LocationDAO.INSTANCE.removeLocationFromUser(user, locationId);
        response.sendRedirect(request.getContextPath() + "/weather");
    }
}
