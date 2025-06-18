package io.github.chriszht.weather.service;

import io.github.chriszht.weather.model.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeather(String location);
}