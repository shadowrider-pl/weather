package finbarre.weather.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.payload.OneDayForecast;

public interface OneDayForecastService {

	Optional<OneDayForecast> getOneDayForecastServiceForLocation(String location, int weatherService) throws JsonMappingException, JsonProcessingException;

	List<OneDayForecast> getAllOneDayForecastServicesForLocation(String location) throws JsonMappingException, JsonProcessingException;

	List<String> getAllFullOneDayForecastServicesForLocation(String location)
			throws JsonMappingException, JsonProcessingException;


}
