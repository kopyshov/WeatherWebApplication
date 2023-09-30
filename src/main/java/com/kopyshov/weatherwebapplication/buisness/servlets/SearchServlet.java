package com.kopyshov.weatherwebapplication.buisness.servlets;

import com.kopyshov.weatherwebapplication.buisness.WeatherService;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import com.kopyshov.weatherwebapplication.common.dao.LocationDAO;
import com.kopyshov.weatherwebapplication.common.dao.UserDAO;
import com.kopyshov.weatherwebapplication.common.entities.Location;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.openweathermap.api.OpenWeatherApiService;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.GeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.WeatherData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet({"/search"})
public class SearchServlet extends BasicServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = (Long) request.getSession().getAttribute("loggedUser");
        if(userId != null) {
            UserData user = UserDAO.INSTANCE.findById(userId);
            context.setVariable("user", user);
        }
        try {
            String location = request.getParameter("location");
            Map<GeoData, WeatherData> weatherData = WeatherService.fetchCurrentWeatherData(location);
            context.setVariable("data", weatherData);
            templateEngine.process("search", context, response.getWriter());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = (Long) request.getSession().getAttribute("loggedUser");
        if (userId == null) {
            templateEngine.process("login", context, response.getWriter());
            return;
        }

        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        try {
            List<LocationGeoData> locationByCoordinates = OpenWeatherApiService.getGeoData(latitude, longitude);
            LocationGeoData locationGeoData = locationByCoordinates.get(0);
            Location location = new Location();
            location.setName(locationGeoData.getName());
            location.setLatitude(locationGeoData.getLat());
            location.setLongitude(locationGeoData.getLon());

            LocationDAO.INSTANCE.addLocationToUser(userId, location);

            response.sendRedirect(request.getContextPath() + "/weather");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
