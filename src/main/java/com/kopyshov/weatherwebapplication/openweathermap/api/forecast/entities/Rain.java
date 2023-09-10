package com.kopyshov.weatherwebapplication.openweathermap.api.forecast.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Rain {
    @SerializedName("1h")
    private double oneHour;
}
