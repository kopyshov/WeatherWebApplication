package com.kopyshov.weatherwebapplication.openweathermap.api.servlets;

import com.kopyshov.weatherwebapplication.BasicServlet;
import com.kopyshov.weatherwebapplication.common.dao.LocationDAO;
import com.kopyshov.weatherwebapplication.common.entities.Location;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.openweathermap.api.OpenWeatherApiService;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.RepresentationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.RepresentationWeatherData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet({"/search"})
public class SearchServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        if (user != null) {
            context.setVariable("user", user);
        }
        try {
            String location = request.getParameter("location");
            Map<RepresentationGeoData, RepresentationWeatherData> weatherData = OpenWeatherApiService.getWeatherDataByCityName(location);
            context.setVariable("data", weatherData);
            templateEngine.process("search", context, response.getWriter());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        if (user == null) {
            templateEngine.process("login", context, response.getWriter());
            return;
        }

        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        try {
            LocationGeoData locationByCoordinates = OpenWeatherApiService.getLocationsByCoordinates(latitude, longitude);

            Location location = new Location();
            location.setName(locationByCoordinates.getName());
            location.setLatitude(locationByCoordinates.getLat());
            location.setLongitude(locationByCoordinates.getLon());

            LocationDAO.INSTANCE.addLocationToUser(user, location);

            response.sendRedirect(request.getContextPath() + "/weather");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
