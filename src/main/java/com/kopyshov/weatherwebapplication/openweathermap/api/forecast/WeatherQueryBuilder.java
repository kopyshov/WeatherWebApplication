package com.kopyshov.weatherwebapplication.openweathermap.api.forecast;

import com.kopyshov.weatherwebapplication.openweathermap.api.query.AbstractQuery;

public class WeatherQueryBuilder extends AbstractQuery {
    private static final String WEATHER_QUERY_PART = "data/2.5/weather";
    private static final String CITY_ID_EQUALS = "id=";
    private static final String LATITUDE_EQUALS = "lat=";
    private static final String LONGITUDE_EQUALS = "lon=";
    private static final String METRIC_UNITS = "units=metric";

    public WeatherQueryBuilder() {
        baseUrl = API_URL + WEATHER_QUERY_PART;
    }

    public String buildWeatherQuery(double lat, double lon) {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append(QUESTION_MARK)
               .append(LATITUDE_EQUALS).append(lat).append(AND)
               .append(LONGITUDE_EQUALS).append(lon).append(AND)
               .append(APP_ID_EQUALS).append(APP_ID).append(AND)
               .append(METRIC_UNITS);
        return builder.toString();
    }

    public String buildWeatherQuery(String id) {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append(QUESTION_MARK)
                .append(CITY_ID_EQUALS).append(id).append(AND)
                .append(APP_ID_EQUALS).append(APP_ID).append(AND)
                .append(METRIC_UNITS);
        return builder.toString();
    }
}
