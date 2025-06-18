package io.github.chriszht.weather.service.impl;

import io.github.chriszht.weather.exception.WeatherException;
import io.github.chriszht.weather.model.WeatherResponse;
import io.github.chriszht.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherResponse getWeather(String location) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("q", location)
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric")
                    .toUriString();

            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new org.springframework.core.ParameterizedTypeReference<Map>() {}
            );

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new WeatherException("API returned status: " + responseEntity.getStatusCode());
            }

            Map<String, Object> response = responseEntity.getBody();

            if (response == null || !response.containsKey("main")) {
                throw new WeatherException("Weather data not found for location: " + location);
            }

            Map<String, Object> main = (Map<String, Object>) response.get("main");
            Map<String, Object> wind = (Map<String, Object>) response.get("wind");
            Map<String, Object> sys = (Map<String, Object>) response.get("sys");
            String name = (String) response.get("name");
            String country = (String) sys.get("country");

            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
            Map<String, Object> weather = weatherList.get(0);
            String description = (String) weather.get("description");
            String iconCode = (String) weather.get("icon");

            // 获取API返回的时间戳和时区偏移量（秒）
            long timestamp = Long.parseLong(response.get("dt").toString());
            int timezoneOffsetSeconds = Integer.parseInt(response.get("timezone").toString());

            // 使用时区偏移量计算当地时间
            ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(timestamp),
                    zoneOffset
            );

            String formattedTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            return new WeatherResponse(
                    name,
                    country,
                    Double.parseDouble(main.get("temp").toString()),
                    Double.parseDouble(main.get("feels_like").toString()),
                    Integer.parseInt(main.get("humidity").toString()),
                    Double.parseDouble(wind.get("speed").toString()),
                    description,
                    formattedTime,
                    getWeatherIconUrl(iconCode),
                    timezoneOffsetSeconds // 传递时区偏移量
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new WeatherException("Location not found: " + location);
        } catch (Exception e) {
            throw new WeatherException("Failed to fetch weather data: " + e.getMessage());
        }
    }

    private String getWeatherIconUrl(String iconCode) {
        return "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
    }
}