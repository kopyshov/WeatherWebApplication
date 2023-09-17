package com.kopyshov.weatherwebapplication.openweathermap.api.forecast;

import com.google.gson.annotations.JsonAdapter;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.entities.*;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.entities.gson.SecondsDateTypeAdapter;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class LocationWeatherData {
    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private long visibility;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    @JsonAdapter(SecondsDateTypeAdapter.class)
    private Date dt;
    private SystemParameters sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;
}
