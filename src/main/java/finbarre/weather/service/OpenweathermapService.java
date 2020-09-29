package finbarre.weather.service;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.payload.OneDayForecast;

public interface OpenweathermapService {

	Optional<OneDayForecast> getOpenweathermapForecast(String location) throws JsonMappingException, JsonProcessingException;
	Optional<String> getFullOpenweathermapForecast(String location) throws JsonMappingException, JsonProcessingException;

}
