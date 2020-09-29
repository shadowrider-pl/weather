package finbarre.weather.serviceImpl;

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
import finbarre.weather.service.AccuweatherLatLonService;

@Service
public class AccuweatherLatLonServiceImpl implements AccuweatherLatLonService{


	@Autowired
	private RestTemplate restTemplate;

	@Value("${apikey.accuweather}")
	private String accuweatherAPIKey;
	
	@Override
	public Coordinates getCoordinatesByCityName(String location) throws JsonMappingException, JsonProcessingException {
		
		Coordinates coordinates=new Coordinates();
		String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey="+accuweatherAPIKey+"&q=" + location;
		
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		
		if(response.getBody().equals("[]")) return null;
		
		coordinates.setLatitude(root.get(0).get("GeoPosition").get("Latitude").asDouble());
		coordinates.setLongitude(root.get(0).get("GeoPosition").get("Longitude").asDouble());
		coordinates.setCityCode(root.get(0).get("Key").asLong());
		
		return coordinates;
	}

}
