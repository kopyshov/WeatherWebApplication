package com.kopyshov.weatherwebapplication.openweathermap.api.out;

import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.entities.Main;

import java.util.Date;

public record RepresentationWeatherData(
        Main main,
        Date dt
) {
}
