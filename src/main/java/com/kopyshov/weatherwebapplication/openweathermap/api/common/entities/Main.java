package com.kopyshov.weatherwebapplication.openweathermap.api.common.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Main {
    private Double temp;
    @SerializedName("feels_like")
    private Double feelsLike;
    @SerializedName("temp_min")
    private Double tempMin;
    @SerializedName("temp_max")
    private Double tempMax;
    private Integer pressure;
    private Integer humidity;
    @SerializedName("sea_level")
    private Integer seaLevel;
    @SerializedName("grnd_level")
    private Integer groundLevel;
}
