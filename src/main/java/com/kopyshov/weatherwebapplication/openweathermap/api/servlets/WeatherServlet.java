package com.kopyshov.weatherwebapplication.openweathermap.api.servlets;

import com.kopyshov.weatherwebapplication.BasicServlet;
import com.kopyshov.weatherwebapplication.common.dao.UserDAO;
import com.kopyshov.weatherwebapplication.common.entities.Location;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.openweathermap.api.OpenWeatherApiService;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.entities.Coordinates;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.RepresentationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.WeatherMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet({"/weather"})
public class WeatherServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<RepresentationGeoData, LocationWeatherData> weatherData = new HashMap<>();
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        if (user != null) {
            UserData userData = UserDAO.INSTANCE.findById(user.getId());
            Set<Location> added = userData.getAdded();
            for (Location location : added) {
                try {
                    LocationGeoData locationGeoData =
                            OpenWeatherApiService.getLocationsByCoordinates(location.getLatitude(),
                                    location.getLongitude());
                    LocationWeatherData weatherDataForLocation = OpenWeatherApiService.getWeatherDataForLocation(locationGeoData);
                    RepresentationGeoData geoData = WeatherMapper.INSTANCE.toDto(locationGeoData);
                    weatherData.put(geoData, weatherDataForLocation);
                } catch (Exception e) {
                    e.printStackTrace(); //здесь может быть не доступен сервис
                }
            }
            context.setVariable("user", userData);
            context.setVariable("data", weatherData);
        }
        templateEngine.process("weather", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        Coordinates coordinates = new Coordinates(latitude, longitude);
        //LocationDAO.INSTANCE.removeLocationFromUser(user, coordinates);
        response.sendRedirect(request.getContextPath() + "/weather");
    }
}
