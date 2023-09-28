package com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.entities;

import com.google.gson.annotations.JsonAdapter;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.gson.SecondsDateTypeAdapter;
import lombok.Data;

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
