package com.kopyshov.weatherwebapplication.openweathermap.api.forecast;

import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.entities.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class RepresentationWeatherData {
    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private long visibility;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private Date dt;
    private SystemParameters sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;
}
