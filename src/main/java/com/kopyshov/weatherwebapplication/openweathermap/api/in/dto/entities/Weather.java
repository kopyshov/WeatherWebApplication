package com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.entities;

import lombok.Data;

@Data
public class Weather {
    private Long id;
    private String main;
    private String description;
    private String icon;
}
