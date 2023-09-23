package com.kopyshov.weatherwebapplication.openweathermap.api.geo;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.kopyshov.weatherwebapplication.openweathermap.api.QueryConfigWeatherApi.*;

public class GeoQueryBuilder {

    private final String baseUrl;
    public GeoQueryBuilder() {
        baseUrl = API_URL + QUERY_GEO_PART;
    }
    public String buildDirectGeoQuery(String cityName) {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append(DIRECT_GEO)
                .append(QUESTION_MARK)
                .append(CITY_NAME_IS).append(URLEncoder.encode(cityName, StandardCharsets.UTF_8)).append(AND)
               .append(LIMIT).append(AND)
               .append(APP_ID_EQUALS).append(APP_ID);
        return builder.toString();
    }

    public String buildReverseGeoQuery(String latitude, String longitude) {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append(REVERSE_GEO)
                .append(QUESTION_MARK)
                .append(LATITUDE_EQUALS).append(latitude).append(AND)
                .append(LONGITUDE_EQUALS).append(longitude).append(AND)
                .append(APP_ID_EQUALS).append(APP_ID);
        return builder.toString();
    }
}
