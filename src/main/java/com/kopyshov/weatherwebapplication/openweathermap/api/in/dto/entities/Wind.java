package com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.entities;

import lombok.Data;

@Data
public class Wind {
    private Double speed;
    private Integer deg;
    private Double gust;
}
