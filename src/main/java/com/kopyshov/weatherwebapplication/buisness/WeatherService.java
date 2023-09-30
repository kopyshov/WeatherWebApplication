package com.kopyshov.weatherwebapplication.buisness;

import com.kopyshov.weatherwebapplication.common.entities.Location;
import com.kopyshov.weatherwebapplication.openweathermap.api.OpenWeatherApiService;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationWeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.GeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.WeatherData;
import com.kopyshov.weatherwebapplication.openweathermap.api.out.WeatherMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.kopyshov.weatherwebapplication.openweathermap.api.OpenWeatherApiService.getWeatherData;

public class WeatherService {
    public static Map<GeoData, WeatherData> fetchCurrentWeatherData(Set<Location> locations) throws IOException, InterruptedException {
        Map<GeoData, WeatherData> data = new HashMap<>();
        for (Location location : locations) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            List<LocationGeoData> locationGeoData = OpenWeatherApiService.getGeoData(latitude, longitude);
            List<LocationWeatherData> weatherData = getWeatherData(locationGeoData.get(0));
            GeoData geoData = WeatherMapper.INSTANCE.toDto(locationGeoData.get(0));
            WeatherData weatherDataLocation = WeatherMapper.INSTANCE.toDto(weatherData.get(0));
            data.put(geoData, weatherDataLocation);
        }
        return data;
    }
    public static Map<GeoData, WeatherData> fetchCurrentWeatherData(String locationName) throws IOException, InterruptedException {
        Map<GeoData, WeatherData> data = new HashMap<>();
        List<LocationGeoData> locations = OpenWeatherApiService.getGeoData(locationName);
        for (LocationGeoData locationGeoData : locations) {
            GeoData geoData = WeatherMapper.INSTANCE.toDto(locationGeoData);
            List<LocationWeatherData> weatherData = getWeatherData(locationGeoData);
            WeatherData currentWeather = WeatherMapper.INSTANCE.toDto(weatherData.get(0));
            data.put(geoData, currentWeather);
        }
        return data;
    }
}
