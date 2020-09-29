package finbarre.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.payload.Coordinates;

public interface AccuweatherLatLonService {
	Coordinates getCoordinatesByCityName(String location) throws JsonMappingException, JsonProcessingException;
}
