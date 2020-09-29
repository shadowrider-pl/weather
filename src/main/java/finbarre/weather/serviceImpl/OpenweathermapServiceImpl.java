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
import finbarre.weather.service.OpenweathermapService;

@Service
public class OpenweathermapServiceImpl implements OpenweathermapService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CoordinatesService coordinatesService;

	@Value("${apikey.openweathermap}")
	private String openweathermapAPIKey;

	@Override
	public Optional<OneDayForecast> getOpenweathermapForecast(String location)
			throws JsonMappingException, JsonProcessingException {

		Coordinates coordinates = coordinatesService.getCoordinates(location);

		if (coordinates == null)
			return Optional.empty();
		
		JsonNode root = getForecast(coordinates);

		OneDayForecast oneDayForecast = new OneDayForecast.Builder()
				.description(root.get("daily").get(0).get("weather").get(0).get("main").asText())
				.minTemperature(root.get("daily").get(0).get("temp").get("min").asDouble())
				.maxTemperature(root.get("daily").get(0).get("temp").get("max").asDouble())
				.weatherService("openweathermap.org").temperatureUnit("C").build();

		return Optional.ofNullable(oneDayForecast);

	}

	@Override
	public Optional<String> getFullOpenweathermapForecast(String location)
			throws JsonMappingException, JsonProcessingException {

		Coordinates coordinates = coordinatesService.getCoordinates(location);

		if (coordinates == null)
			return Optional.empty();
		
		JsonNode root = getForecast(coordinates);
		
		return Optional.ofNullable(root.toString());
	}

	private JsonNode getForecast(Coordinates coordinates) throws JsonProcessingException, JsonMappingException {
		String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + coordinates.getLatitude() + "&lon="
				+ coordinates.getLongitude() + "&exclude=hourly&appid=" + openweathermapAPIKey + "&units=metric";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		return root;
	}

}
