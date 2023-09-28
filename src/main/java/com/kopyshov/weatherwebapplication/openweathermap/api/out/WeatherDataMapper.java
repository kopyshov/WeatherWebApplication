package com.kopyshov.weatherwebapplication.openweathermap.api.out;

import com.kopyshov.weatherwebapplication.openweathermap.api.out.RepresentationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationWeatherData;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataMapper {
    public static List<RepresentationWeatherData> mapToWeatherNecessaryInfo(List<LocationWeatherData> data) {
        List<RepresentationWeatherData> dataList = new ArrayList<>();
        for (LocationWeatherData dt : data) {
            dataList.add(RepresentationWeatherData.builder()
                    .coord(dt.getCoord())
                    .name(dt.getName())
                    .dt(dt.getDt())
                    .weather(dt.getWeather())
                    .build());
        }
        return dataList;
    }
}
