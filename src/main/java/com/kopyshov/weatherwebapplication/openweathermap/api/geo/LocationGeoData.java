package com.kopyshov.weatherwebapplication.openweathermap.api.geo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

@Data
public class LocationGeoData {
    private String name;
    @SerializedName("local_names")
    private Map<String, String> localNames;
    private Double lat;
    private Double lon;
    private String country;
    private String state;
}
