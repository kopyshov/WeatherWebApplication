package com.kopyshov.weatherwebapplication.openweathermap.api.query;

public class AbstractQuery {
    protected String API_URL = "https://api.openweathermap.org/";
    protected String CITY_NAME_IS = "q=";
    protected String QUESTION_MARK = "?";
    protected String AND = "&";
    protected String LIMIT = "limit=5";
    protected String APP_ID_EQUALS = "appid=";
    protected String APP_ID = "ed4c24e83f1ec29a34997c12e2fc7604";

    protected String baseUrl;
}
