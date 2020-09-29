package finbarre.weather.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import finbarre.weather.payload.OneDayForecast;
import finbarre.weather.service.OneDayForecastService;
import finbarre.weather.service.OneDayFullForecastService;

@RestController
@RequestMapping("/one-day-forecast")

public class OneDayForecastResource {

	private final Logger log = LoggerFactory.getLogger(OneDayForecastResource.class);

	private final OneDayForecastService oneDayForecastService;
	private final OneDayFullForecastService oneDayFullForecastService;

	public OneDayForecastResource(OneDayForecastService oneDayForecastService,
			OneDayFullForecastService oneDayFullForecastService) {
		this.oneDayForecastService = oneDayForecastService;
		this.oneDayFullForecastService = oneDayFullForecastService;
	}

	@GetMapping
	public ResponseEntity<OneDayForecast> getOneDayForecastForLocation(@RequestParam String location,
			@RequestParam int weatherService) throws JsonMappingException, JsonProcessingException {
		log.debug("REST request to get OneDayForecast for " + location + " and weatherService " + weatherService);
		Optional<OneDayForecast> oneDayForecast = oneDayForecastService.getOneDayForecastServiceForLocation(location,
				weatherService);

		return oneDayForecast.map(forecast -> ResponseEntity.ok().body(forecast))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/all")
	public ResponseEntity<List<OneDayForecast>> getAllOneDayForecastsForLocation(@RequestParam String location)
			throws JsonMappingException, JsonProcessingException {
		log.debug("REST request to get all OneDayForecasts for " + location);
		List<OneDayForecast> oneDayForecasts = oneDayForecastService.getAllOneDayForecastServicesForLocation(location);

		return ResponseEntity.ok().body(oneDayForecasts);
	}

	@GetMapping("/full")
	public ResponseEntity<String> getOneDayFullForecastForLocation(@RequestParam String location,
			@RequestParam int weatherService) throws JsonMappingException, JsonProcessingException {
		
		log.debug("REST request to get full OneDayForecast for " + location);
		Optional<String> oneDayForecast = oneDayFullForecastService.getOneDayFullForecastServiceForLocation(location,
				weatherService);
		
		return oneDayForecast.map(forecast -> ResponseEntity.ok().body(forecast))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/full/all")
	public ResponseEntity<List<String>> getAllOneDayFullForecastsForLocation(@RequestParam String location)
			throws JsonMappingException, JsonProcessingException {
		log.debug("REST request to get all full OneDayForecasts for " + location);
		List<String> oneDayForecasts = oneDayForecastService.getAllFullOneDayForecastServicesForLocation(location);

		return ResponseEntity.ok().body(oneDayForecasts);
	}

}
