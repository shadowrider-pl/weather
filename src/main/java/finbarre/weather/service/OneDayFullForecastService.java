package finbarre.weather.service;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface OneDayFullForecastService {

	Optional<String> getOneDayFullForecastServiceForLocation(String location, int weatherService) throws JsonMappingException, JsonProcessingException;

}
