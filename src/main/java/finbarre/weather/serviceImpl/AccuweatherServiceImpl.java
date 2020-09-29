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
import finbarre.weather.service.AccuweatherService;
import finbarre.weather.service.CoordinatesService;

@Service
public class AccuweatherServiceImpl implements AccuweatherService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CoordinatesService coordinatesService;

	@Value("${apikey.accuweather}")
	private String accuweatherAPIKey;

	@Override
	public Optional<OneDayForecast> getAccuweatherForecast(String location)
			throws JsonMappingException, JsonProcessingException {

		Coordinates coordinates = coordinatesService.getCoordinates(location);

		if (coordinates == null)
			return Optional.empty();
		
		JsonNode root = getForecast(coordinates.getCityCode());

		OneDayForecast oneDayForecast = new OneDayForecast.Builder().description(root.at("/Headline/Text").asText())
				.minTemperature(convertToCelcius(root.at("/DailyForecasts/Temperature/Minimum/Value").asDouble()))
				.maxTemperature(convertToCelcius(root.at("/DailyForecasts/Temperature/Maximum/Value").asDouble()))
				.weatherService("accuweather.com").temperatureUnit("C").build();

		return Optional.ofNullable(oneDayForecast);
	}

	@Override
	public Optional<String> getAccuweatherFullForecast(String location)
			throws JsonMappingException, JsonProcessingException {

		Coordinates coordinates = coordinatesService.getCoordinates(location);

		if (coordinates == null)
			return Optional.empty();
		
		JsonNode root = getForecast(coordinates.getCityCode());

		return Optional.ofNullable(root.toString());
	}

	private JsonNode getForecast(long l) throws JsonProcessingException, JsonMappingException {
		String url = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + l + "?apikey=" + accuweatherAPIKey;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		return root;
	}

	private double convertToCelcius(double temp) {
		return Math.round(((temp - 32) / 1.8) * 100.0) / 100.0;
	}

}
