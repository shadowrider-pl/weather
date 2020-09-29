package finbarre.weather.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.service.AccuweatherService;
import finbarre.weather.service.OneDayFullForecastService;
import finbarre.weather.service.OpenweathermapService;
import finbarre.weather.service.WeatherbitService;

@Service
public class OneDayFullForecastServiceImpl implements OneDayFullForecastService {

	@Autowired
	private AccuweatherService accuweatherService;

	@Autowired
	private OpenweathermapService openweathermapService;

	@Autowired
	private WeatherbitService weatherbitService;

	private Optional<String> dayForecast;

	@Override
	public Optional<String> getOneDayFullForecastServiceForLocation(String location, int weatherService)
			throws JsonMappingException, JsonProcessingException {

		switch (weatherService) {
		case 1:
			dayForecast = accuweatherService.getAccuweatherFullForecast(location);
			break;

		case 2:
			dayForecast = openweathermapService.getFullOpenweathermapForecast(location);
			break;

		case 3:
			dayForecast = weatherbitService.getFullWeatherbitForecast(location);
			break;

		default:
			dayForecast = Optional.empty();
			break;
		}

		return dayForecast;
	}
}
