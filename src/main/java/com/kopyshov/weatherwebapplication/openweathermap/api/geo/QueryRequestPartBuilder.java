package com.kopyshov.weatherwebapplication.openweathermap.api.geo;

import org.apache.commons.lang3.StringUtils;

public final class QueryRequestPartBuilder {
    public static String byCityName(String cityName, String countryCode) {
        StringBuilder builder = new StringBuilder();
        builder.append("q=").append(cityName);
        if (StringUtils.isNotBlank(countryCode)) {
            builder.append(',').append(countryCode);
        }
        return builder.toString();
    }
}
