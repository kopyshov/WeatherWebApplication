package com.kopyshov.weatherwebapplication.openweathermap.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.querybuilders.GeoQueryBuilder;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.querybuilders.WeatherQueryBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class OpenWeatherApiService {
    private static final Gson gson = new Gson();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static List<LocationGeoData> getGeoData(String locationName) throws IOException, InterruptedException {
        URI uri = buildUriRequestByCityName(locationName);
        return requestGeoDataToApi(uri);
    }
    public static List<LocationGeoData> getGeoData(double latitude, double longitude) throws IOException, InterruptedException {
        URI uri = buildUriRequestLocationByCoordinates(latitude, longitude);
        return requestGeoDataToApi(uri);
    }
    public static LocationWeatherData getWeatherData(LocationGeoData location) throws IOException, InterruptedException {
        double latitude = location.getLat();
        double longitude = location.getLon();
        URI uri = buildUriRequestByCoordinates(latitude, longitude);
        return requestWeatherDataToApi(uri);
    }
    public static List<LocationGeoData> requestGeoDataToApi(URI uri) throws InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        Type listType = new TypeToken<List<LocationGeoData>>(){}.getType();
        return gson.fromJson(body, listType);
    }
    private static LocationWeatherData requestWeatherDataToApi(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        Type listType = new TypeToken<LocationWeatherData>(){}.getType();
        return gson.fromJson(body, listType);
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

    private static URI buildUriRequestLocationByCoordinates(Double latitude, Double longitude) {
        GeoQueryBuilder builder = new GeoQueryBuilder();
        String geoQuery = builder.buildReverseGeoQuery(latitude.toString(), longitude.toString());
        return URI.create(geoQuery);
    }
}
