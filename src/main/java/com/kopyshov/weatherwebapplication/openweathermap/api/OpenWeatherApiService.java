package com.kopyshov.weatherwebapplication.openweathermap.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.querybuilders.GeoQueryBuilder;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.querybuilders.WeatherQueryBuilder;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.RepresentationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.RepresentationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.WeatherMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenWeatherApiService {
    private static final Gson gson = new Gson();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static Map<RepresentationGeoData, RepresentationWeatherData> getWeatherDataByCityName(String cityName) throws IOException, InterruptedException {
        Map<RepresentationGeoData, RepresentationWeatherData> weatherData = new HashMap<>();
        List<LocationGeoData> locations = getLocationsByCityName(cityName);
        for (LocationGeoData locationGeoData : locations) {
            LocationWeatherData locationWeatherData = getWeatherDataForLocation(locationGeoData);
            RepresentationGeoData geoData = WeatherMapper.INSTANCE.toDto(locationGeoData);
            RepresentationWeatherData currentWeather = WeatherMapper.INSTANCE.toDto(locationWeatherData);
            weatherData.put(geoData, currentWeather);
        }
        return weatherData;
    }

    private static List<LocationGeoData> getLocationsByCityName(String locationName) throws IOException, InterruptedException {
        URI uri = buildUriRequestByCityName(locationName);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        Type listType = new TypeToken<ArrayList<LocationGeoData>>(){}.getType();
        return gson.fromJson(body, listType);
    }

    public static LocationWeatherData getWeatherDataForLocation(LocationGeoData location) throws IOException, InterruptedException {
        double latitude = location.getLat(); //Math.round(location.getLat() * 100.0) / 100.0;
        double longitude = location.getLon(); // Math.round(location.getLon() * 100.0) / 100.0;
        URI uri = buildUriRequestByCoordinates(latitude, longitude);
        HttpRequest requestToApi = HttpRequest.newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(requestToApi, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), LocationWeatherData.class);
    }

    public static LocationGeoData getLocationsByCoordinates(double latitude, double longitude) throws IOException, InterruptedException {
        URI uri = buildUriRequestByCoordinates(latitude, longitude);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        Type listType = new TypeToken<ArrayList<LocationGeoData>>(){}.getType();
        List<LocationGeoData> locationGeoData = gson.fromJson(body, listType);
        return locationGeoData.get(0);
    }

    public static URI buildUriRequestByCoordinates(double lan, double lon) {
        WeatherQueryBuilder builder = new WeatherQueryBuilder();
        String weatherQuery = builder.buildWeatherQuery(lan, lon);
        return URI.create(weatherQuery);
    }

    private static URI buildUriRequestByCityName(String location) {
        GeoQueryBuilder builder = new GeoQueryBuilder();
        String geoQuery = builder.buildDirectGeoQuery(location);
        return URI.create(geoQuery);
    }

    private static URI buildUriRequestByCoordinates(String latitude, String longitude) {
        GeoQueryBuilder builder = new GeoQueryBuilder();
        String geoQuery = builder.buildReverseGeoQuery(latitude, longitude);
        return URI.create(geoQuery);
    }
}
