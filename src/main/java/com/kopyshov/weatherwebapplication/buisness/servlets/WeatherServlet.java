package com.kopyshov.weatherwebapplication.buisness.servlets;

import com.kopyshov.weatherwebapplication.buisness.WeatherService;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import com.kopyshov.weatherwebapplication.common.dao.LocationDAO;
import com.kopyshov.weatherwebapplication.common.dao.UserDAO;
import com.kopyshov.weatherwebapplication.common.entities.Location;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.GeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.WeatherData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet({"/weather"})
public class WeatherServlet extends BasicServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = (Long) request.getSession().getAttribute("loggedUser");
        if(userId != null) {
            UserData user = UserDAO.INSTANCE.findById(userId);
            context.setVariable("user", user);
            Set<Location> addedLocations = user.getAdded();
            try {
                Map<GeoData, WeatherData> data = WeatherService.fetchCurrentWeatherData(addedLocations);
                context.setVariable("data", data);
            } catch (InterruptedException e) {
                context.setVariable("error", "OpenWeather is not working");
            }
        }
        templateEngine.process("weather", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = (Long) request.getSession().getAttribute("loggedUser");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        LocationDAO.INSTANCE.removeLocationFromUser(userId, latitude, longitude);
        response.sendRedirect(request.getContextPath() + "/weather");
    }
}
