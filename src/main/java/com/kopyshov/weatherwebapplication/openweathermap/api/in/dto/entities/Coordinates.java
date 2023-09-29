package com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinates {
    private String lon;
    private String lat;

}
