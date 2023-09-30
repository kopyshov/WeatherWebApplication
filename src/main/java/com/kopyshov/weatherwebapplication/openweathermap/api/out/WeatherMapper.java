package com.kopyshov.weatherwebapplication.openweathermap.api.out;

import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationGeoData;
import com.kopyshov.weatherwebapplication.openweathermap.api.in.dto.LocationWeatherData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherMapper {
    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "latitude", source = "lat"),
            @Mapping(target = "longitude", source = "lon"),
            @Mapping(target = "localName", source = ".", qualifiedByName = "getLocalName"),
            @Mapping(target = "country", source = "country"),
            @Mapping(target = "state", source = "state")
    })
    GeoData toDto(LocationGeoData entity);

    @Named("getLocalName")
    public static String getLocalName(LocationGeoData entity) {
        return entity.getLocalNames() != null ? entity.getLocalNames().get("ru") : entity.getName();
    }

    @Mappings({
            @Mapping(target = "main", source = "main"),
            @Mapping(target = "dt", source = "dt")
    })
    WeatherData toDto(LocationWeatherData entity);
}
