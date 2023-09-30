package com.kopyshov.weatherwebapplication.openweathermap.api.out;

public record GeoData(
        String name,
        String localName,
        String country,
        String state,
        String latitude,
        String longitude
) {
}
