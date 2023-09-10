package com.kopyshov.weatherwebapplication.openweathermap.api;

import com.kopyshov.weatherwebapplication.common.BasicServlet;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.RepresentationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.utils.WeatherDataMapper;
import com.kopyshov.weatherwebapplication.openweathermap.api.geo.LocationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.services.OpenWeatherApiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/weather")
public class WeatherServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        templateEngine.process("weather", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String location = request.getParameter("location");
            List<LocationWeatherData> locations = OpenWeatherApiService.getWeatherDataByCityName(location);
            RepresentationWeatherData data = WeatherDataMapper.mapToWeatherNecessaryInfo(locations.get(0));
            context.setVariable("data", data);
            templateEngine.process("home", context, response.getWriter());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
