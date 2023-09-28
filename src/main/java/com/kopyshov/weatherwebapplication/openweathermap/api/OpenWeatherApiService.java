package com.kopyshov.weatherwebapplication.openweathermap.api;

import com.google.gson.Gson;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.querybuilders.WeatherQueryBuilder;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.querybuilders.GeoQueryBuilder;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationGeoData;

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
        return gson.fromJson(body, LocationGeoData[].class);
    }


    public static URI buildUriRequestByCityName(String location) {
        GeoQueryBuilder builder = new GeoQueryBuilder();
        String geoQuery = builder.buildDirectGeoQuery(location);
        return URI.create(geoQuery);
    }

    public static LocationGeoData[] getLocationsByCoordinates(String latitude, String longitude) throws IOException, InterruptedException {
        URI uri = buildUriRequestByCoordinates(latitude, longitude);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        return gson.fromJson(body, LocationGeoData[].class);
    }

    public static URI buildUriRequestByCoordinates(String latitude, String longitude) {
        GeoQueryBuilder builder = new GeoQueryBuilder();
        String geoQuery = builder.buildReverseGeoQuery(latitude, longitude);
        return URI.create(geoQuery);
    }

    public static List<LocationWeatherData> getWeatherDataByCityName(String cityName) throws IOException, InterruptedException {
        List<LocationWeatherData> weatherData = new ArrayList<>();
        LocationGeoData[] locations = getLocationsByCityName(cityName);
        for (LocationGeoData loc : locations) {
            double latitude = Math.round(loc.getLat() * 100.0) / 100.0;
            double longitude = Math.round(loc.getLon() * 100.0) / 100.0;
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
    public static URI buildUriRequestByCoordinates(double lan, double lon) {
        WeatherQueryBuilder builder = new WeatherQueryBuilder();
        String weatherQuery = builder.buildWeatherQuery(lan, lon);
        return URI.create(weatherQuery);
    }
}
