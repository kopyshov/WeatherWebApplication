package com.kopyshov.weatherwebapplication.openweathermap.api.services;

import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import com.kopyshov.weatherwebapplication.openweathermap.api.currentweather.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.currentweather.RepresentationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.common.utils.WeatherDataMapper;
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
            context.setVariable("username", user.getUsername());
        }
        templateEngine.process("search", context, response.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserData user = (UserData) session.getAttribute("loggedUser");
        if (user != null) {
            context.setVariable("username", user.getUsername());
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
}
