package com.kopyshov.weatherwebapplication.openweathermap.api;

public class QueryConfigWeatherApi {
    public static final String API_URL = "https://api.openweathermap.org/";
    public static final String CITY_NAME_IS = "q=";
    public static final String QUESTION_MARK = "?";
    public static final String AND = "&";
    public static final String LIMIT = "limit=5";
    public static final String APP_ID_EQUALS = "appid=";
    public static final String APP_ID = System.getenv("WEATHER_APP_ID");


    public static final String QUERY_GEO_PART = "geo/1.0/";
    public static final String DIRECT_GEO = "direct";
    public static final String REVERSE_GEO = "reverse";

    public static final String WEATHER_QUERY_PART = "data/2.5/weather";
    public static final String CITY_ID_EQUALS = "id=";
    public static final String LATITUDE_EQUALS = "lat=";
    public static final String LONGITUDE_EQUALS = "lon=";
    public static final String METRIC_UNITS = "units=metric";
}
