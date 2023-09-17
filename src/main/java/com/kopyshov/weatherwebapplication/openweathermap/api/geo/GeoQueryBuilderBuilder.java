package com.kopyshov.weatherwebapplication.openweathermap.api.geo;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.kopyshov.weatherwebapplication.openweathermap.api.common.ConfigWeatherApi.*;

public class GeoQueryBuilderBuilder {

    private final String baseUrl;
    public GeoQueryBuilderBuilder() {
        baseUrl = API_URL + QUERY_GEO_PART;
    }
    public String buildGeoQuery(String cityName) {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append(QUESTION_MARK)
                .append(CITY_NAME_IS).append(URLEncoder.encode(cityName, StandardCharsets.UTF_8)).append(AND)
               .append(LIMIT).append(AND)
               .append(APP_ID_EQUALS).append(APP_ID);
        return builder.toString();
    }
}
