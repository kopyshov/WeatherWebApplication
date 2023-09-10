package com.kopyshov.weatherwebapplication.openweathermap.api.forecast.utils;

import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.RepresentationWeatherData;

public class WeatherDataMapper {
    public static RepresentationWeatherData mapToWeatherNecessaryInfo(LocationWeatherData data) {
        return RepresentationWeatherData.builder()
                .name(data.getName())
                .dt(data.getDt())
                .weather(data.getWeather())
                .build();
    }
}
