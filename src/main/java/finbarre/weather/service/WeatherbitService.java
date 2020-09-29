package finbarre.weather.service;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.payload.OneDayForecast;

public interface WeatherbitService {
	Optional<OneDayForecast> getWeatherbitForecast(String location) throws JsonMappingException, JsonProcessingException;
	Optional<String> getFullWeatherbitForecast(String location) throws JsonMappingException, JsonProcessingException;
}
