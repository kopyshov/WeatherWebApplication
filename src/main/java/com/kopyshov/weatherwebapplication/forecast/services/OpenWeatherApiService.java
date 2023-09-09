package com.kopyshov.weatherwebapplication.forecast.services;

import com.google.gson.Gson;
import com.kopyshov.weatherwebapplication.forecast.services.dto.LocationDto;

import java.net.http.HttpResponse;

public class OpenWeatherApiService {
    private String weatherAppid = "ed4c24e83f1ec29a34997c12e2fc7604";

    private Gson gson = new Gson();

    private LocationDto readWeatherApiResponse(HttpResponse response) {
        return null;
    }
}
