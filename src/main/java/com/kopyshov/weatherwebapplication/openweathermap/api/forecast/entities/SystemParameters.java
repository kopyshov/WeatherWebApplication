package com.kopyshov.weatherwebapplication.openweathermap.api.forecast.entities;

import com.google.gson.annotations.JsonAdapter;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.entities.gson.SecondsDateTypeAdapter;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class SystemParameters {
    private int type;
    private long id;
    private String country;
    @JsonAdapter(SecondsDateTypeAdapter.class)
    private Date sunrise;
    @JsonAdapter(SecondsDateTypeAdapter.class)
    private Date sunset;
}
