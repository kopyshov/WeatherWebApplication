package com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SecondsDateTypeAdapter extends TypeAdapter<Date> {
    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(TimeUnit.MILLISECONDS.toSeconds(date.getTime()));
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        long timeInSeconds = jsonReader.nextLong();
        long timeInMillis = TimeUnit.SECONDS.toMillis(timeInSeconds);
        return new Date(timeInMillis);
    }
}
