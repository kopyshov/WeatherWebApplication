package com.kopyshov.weatherwebapplication.openweathermap.api.forecast.utils;

import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.RepresentationWeatherData;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataMapper {
    public static List<RepresentationWeatherData> mapToWeatherNecessaryInfo(List<LocationWeatherData> data) {
        List<RepresentationWeatherData> dataList = new ArrayList<>();
        for (LocationWeatherData dt : data) {
            dataList.add(RepresentationWeatherData.builder()
                    .name(dt.getName())
                    .dt(dt.getDt())
                    .weather(dt.getWeather())
                    .build());
        }
        return dataList;
    }
}
