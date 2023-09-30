package com.kopyshov.weatherwebapplication.openweathermap.api.in.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LocationGeoData extends LocationData {
    private String name;
    @SerializedName("local_names")
    private Map<String, String> localNames;
    private double lat;
    private double lon;
    private String country;
    private String state;
}
