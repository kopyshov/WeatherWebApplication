package com.kopyshov.weatherwebapplication.servlets;

import com.kopyshov.weatherwebapplication.auth.dao.UserDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.buisness.Location;
import com.kopyshov.weatherwebapplication.buisness.LocationDAO;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import com.kopyshov.weatherwebapplication.openweathermap.api.currentweather.LocationWeatherData;
import com.kopyshov.weatherwebapplication.buisness.RepresentationWeatherData;
import com.kopyshov.weatherwebapplication.buisness.utils.WeatherDataMapper;
import com.kopyshov.weatherwebapplication.openweathermap.api.OpenWeatherApiService;
import com.kopyshov.weatherwebapplication.openweathermap.api.geo.LocationGeoData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

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
            List<LocationWeatherData> locations = OpenWeatherApiService.getWeatherDataByCityName(location);
            List<RepresentationWeatherData> data = WeatherDataMapper.mapToWeatherNecessaryInfo(locations);
            context.setVariable("data", data);
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

        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        try {
            LocationGeoData[] locationsByCoordinates = OpenWeatherApiService.getLocationsByCoordinates(latitude, longitude);
            LocationGeoData foundedLocation = locationsByCoordinates[0];

            Location location = new Location();
            location.setName(foundedLocation.getName());
            location.setLatitude(foundedLocation.getLat().toString());
            location.setLongitude(foundedLocation.getLon().toString());

            UserDAO.INSTANCE.addLocationToUser(user, location);

            response.sendRedirect(request.getContextPath() + "/weather");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
