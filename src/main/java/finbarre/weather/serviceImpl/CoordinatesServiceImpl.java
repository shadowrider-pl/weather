package finbarre.weather.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.payload.Coordinates;
import finbarre.weather.service.AccuweatherLatLonService;
import finbarre.weather.service.CoordinatesService;

@Service
public class CoordinatesServiceImpl implements CoordinatesService {
	
	@Autowired
	private AccuweatherLatLonService accuweatherLatLonService;


	public Coordinates getCoordinates(String location) throws JsonMappingException, JsonProcessingException {
		
		Coordinates coordinates = accuweatherLatLonService.getCoordinatesByCityName(location);
		
		return coordinates;
	}
}
