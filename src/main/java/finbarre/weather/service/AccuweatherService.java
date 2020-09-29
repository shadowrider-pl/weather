package finbarre.weather.service;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.payload.OneDayForecast;

public interface AccuweatherService {

	Optional<OneDayForecast> getAccuweatherForecast(String location) throws JsonMappingException, JsonProcessingException;
	Optional<String> getAccuweatherFullForecast(String location) throws JsonMappingException, JsonProcessingException;

}
