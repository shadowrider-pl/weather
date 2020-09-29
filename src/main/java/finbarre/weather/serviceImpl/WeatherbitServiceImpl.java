package finbarre.weather.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import finbarre.weather.payload.Coordinates;
import finbarre.weather.payload.OneDayForecast;
import finbarre.weather.service.CoordinatesService;
import finbarre.weather.service.WeatherbitService;

@Service
public class WeatherbitServiceImpl implements WeatherbitService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CoordinatesService coordinatesService;
	
    @Value("${apikey.weatherbit}")
    private String weatherbitAPIKey;
	
	@Override
	public Optional<OneDayForecast> getWeatherbitForecast(String location) throws JsonMappingException, JsonProcessingException {

		Coordinates coordinates = coordinatesService.getCoordinates(location);

		if (coordinates == null)
			return Optional.empty();
		
		JsonNode root = getForecast(coordinates);

		OneDayForecast oneDayForecast = new OneDayForecast.Builder()
				.description(root.get("data").get(0).get("weather").get("description").asText())
				.minTemperature(root.get("data").get(0).get("min_temp").asDouble())
				.maxTemperature(root.get("data").get(0).get("max_temp").asDouble())
				.weatherService("weatherbit.io")
				.temperatureUnit("C")
				.build();
		
		return Optional.ofNullable(oneDayForecast);
	}

	@Override
	public Optional<String> getFullWeatherbitForecast(String location)
			throws JsonMappingException, JsonProcessingException {

		Coordinates coordinates = coordinatesService.getCoordinates(location);

		if (coordinates == null)
			return Optional.empty();
		
		JsonNode root = getForecast(coordinates);
		
		return Optional.ofNullable(root.toString());
	}

	private JsonNode getForecast(Coordinates coordinates) throws JsonProcessingException, JsonMappingException {
		String url="https://api.weatherbit.io/v2.0/forecast/daily?lat="+coordinates.getLatitude()+"&lon="+coordinates.getLongitude()+"&key="+weatherbitAPIKey;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode root = mapper.readTree(response.getBody());
		return root;
	}

}
