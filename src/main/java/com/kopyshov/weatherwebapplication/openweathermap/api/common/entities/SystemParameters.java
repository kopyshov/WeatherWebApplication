package com.kopyshov.weatherwebapplication.openweathermap.api.common.entities;

import com.google.gson.annotations.JsonAdapter;
import com.kopyshov.weatherwebapplication.openweathermap.api.common.entities.gson.SecondsDateTypeAdapter;
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
