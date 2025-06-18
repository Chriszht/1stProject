package io.github.chriszht.weather.model;

public class WeatherResponse {
    private String city;
    private String country;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private double windSpeed;
    private String description;
    private String formattedTime;
    private String weatherIconUrl;
    private int timezoneOffsetSeconds; // 新增字段

    public WeatherResponse(String city, String country, double temperature, double feelsLike, int humidity, double windSpeed, String description, String formattedTime, String weatherIconUrl, int timezoneOffsetSeconds) {
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.description = description;
        this.formattedTime = formattedTime;
        this.weatherIconUrl = weatherIconUrl;
        this.timezoneOffsetSeconds = timezoneOffsetSeconds; // 初始化新增字段
    }

    // getter、setter 方法（若需要）
    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getCountry() {return country;}

    public void setCountry(String country) {this.country = country;}

    public double getTemperature() {return temperature;}

    public void setTemperature(double temperature) {this.temperature = temperature;}

    public double getFeelsLike() {return feelsLike;}

    public void setFeelsLike(double feelsLike) {this.feelsLike = feelsLike;}

    public int getHumidity() {return humidity;}

    public void setHumidity(int humidity) {this.humidity = humidity;}

    public double getWindSpeed() {return windSpeed;}

    public void setWindSpeed(double windSpeed) {this.windSpeed = windSpeed;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getFormattedTime() {return formattedTime;}

    public void setFormattedTime(String formattedTime) {this.formattedTime = formattedTime;}

    public String getWeatherIconUrl() {return weatherIconUrl;}

    public void setWeatherIconUrl(String weatherIconUrl) {this.weatherIconUrl = weatherIconUrl;}

    public int getTimezoneOffsetSeconds() {
        return timezoneOffsetSeconds;
    }

    public void setTimezoneOffsetSeconds(int timezoneOffsetSeconds) {
        this.timezoneOffsetSeconds = timezoneOffsetSeconds;
    }
}