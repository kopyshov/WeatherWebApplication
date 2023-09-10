package com.kopyshov.weatherwebapplication.openweathermap.api.services;

import com.google.gson.Gson;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.forecast.WeatherQueryBuilder;
import com.kopyshov.weatherwebapplication.openweathermap.api.geo.GeoQueryBuilder;
import com.kopyshov.weatherwebapplication.openweathermap.api.geo.LocationGeoData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherApiService {
    private static final Gson gson = new Gson();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static LocationGeoData[] getLocationsByCityName(String locationName) throws IOException, InterruptedException {
        URI uri = buildUriRequestByCityName(locationName);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        LocationGeoData[] locationGeoData = gson.fromJson(body, LocationGeoData[].class);
        return locationGeoData;
    }


    public static URI buildUriRequestByCityName(String location) {
        GeoQueryBuilder builder = new GeoQueryBuilder();
        String geoQuery = builder.buildGeoQuery(location);
        return URI.create(geoQuery);
    }

    public static URI buildUriRequestByCoordinates(String lan, String lon) {
        WeatherQueryBuilder builder = new WeatherQueryBuilder();
        String weatherQuery = builder.buildWeatherQuery(lan, lon);
        return URI.create(weatherQuery);
    }

    public static List<LocationWeatherData> getWeatherDataByCityName(String cityName) throws IOException, InterruptedException {
        List<LocationWeatherData> weatherData = new ArrayList<>();
        LocationGeoData[] locations = getLocationsByCityName(cityName);
        for (LocationGeoData loc : locations) {
            String latitude = loc.getLat().toString();
            String longitude = loc.getLon().toString();
            URI uri = buildUriRequestByCoordinates(latitude, longitude);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LocationWeatherData locationWeatherData = gson.fromJson(response.body(), LocationWeatherData.class);
            weatherData.add(locationWeatherData);
        }
        return weatherData;
    }
}
