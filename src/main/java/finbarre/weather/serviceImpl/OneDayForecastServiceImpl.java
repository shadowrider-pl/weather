package finbarre.weather.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.payload.OneDayForecast;
import finbarre.weather.service.AccuweatherService;
import finbarre.weather.service.OneDayForecastService;
import finbarre.weather.service.OpenweathermapService;
import finbarre.weather.service.WeatherbitService;

@Service
public class OneDayForecastServiceImpl implements OneDayForecastService {

	private final Logger log = LoggerFactory.getLogger(OneDayForecastServiceImpl.class);

	@Autowired
	private AccuweatherService accuweatherService;

	@Autowired
	private OpenweathermapService openweathermapService;

	@Autowired
	private WeatherbitService weatherbitService;

	private Optional<OneDayForecast> dayForecast;

	@Override
	public Optional<OneDayForecast> getOneDayForecastServiceForLocation(String location, int weatherService)
			throws JsonMappingException, JsonProcessingException {

		log.debug("OneDayForecastService request to getOneDayForecastServiceForLocation " + location + " and service "
				+ weatherService);

		switch (weatherService) {
		case 1:
			dayForecast = accuweatherService.getAccuweatherForecast(location);
			break;

		case 2:
			dayForecast = openweathermapService.getOpenweathermapForecast(location);
			break;

		case 3:
			dayForecast = weatherbitService.getWeatherbitForecast(location);
			break;

		default:
			dayForecast=Optional.empty();
			break;
		}

		return dayForecast;
	}

	@Override
	public List<OneDayForecast> getAllOneDayForecastServicesForLocation(String location) throws JsonMappingException, JsonProcessingException {
		
		log.debug("OneDayForecastService request to getAllOneDayForecastServicesForLocation " + location);
		
		List<OneDayForecast> forecasts=new ArrayList<>();
		forecasts.add(accuweatherService.getAccuweatherForecast(location).map(forecast -> forecast)
				.orElse(null));
		forecasts.add(openweathermapService.getOpenweathermapForecast(location).map(forecast -> forecast)
				.orElse(null));
		forecasts.add(weatherbitService.getWeatherbitForecast(location).map(forecast -> forecast)
				.orElse(null));
		
		return forecasts;
	}
	
	@Override
	public List<String> getAllFullOneDayForecastServicesForLocation(String location) throws JsonMappingException, JsonProcessingException {
		
		log.debug("OneDayForecastService request to getAllFullOneDayForecastServicesForLocation " + location);
		
		List<String> forecasts=new ArrayList<>();
		forecasts.add(accuweatherService.getAccuweatherFullForecast(location).map(forecast -> forecast)
				.orElse(null));
		forecasts.add(openweathermapService.getFullOpenweathermapForecast(location).map(forecast -> forecast)
				.orElse(null));
		forecasts.add(weatherbitService.getFullWeatherbitForecast(location).map(forecast -> forecast)
				.orElse(null));
		
		return forecasts;
	}
}
