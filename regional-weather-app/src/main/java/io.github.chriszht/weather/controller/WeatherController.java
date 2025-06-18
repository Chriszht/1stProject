package io.github.chriszht.weather.controller;

import io.github.chriszht.weather.model.WeatherResponse;
import io.github.chriszht.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam @NotBlank String location) {
        WeatherResponse response = weatherService.getWeather(location);
        return ResponseEntity.ok(response);
    }
}