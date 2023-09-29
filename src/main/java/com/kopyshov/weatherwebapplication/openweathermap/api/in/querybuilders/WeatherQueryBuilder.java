package com.kopyshov.weatherwebapplication.openweathermap.api.in.querybuilders;

import static com.kopyshov.weatherwebapplication.openweathermap.api.QueryConfigWeatherApi.*;

public class WeatherQueryBuilder {
    private final String baseUrl;

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
}
