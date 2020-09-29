package finbarre.weather.payload;

import lombok.Data;

@Data
public class Coordinates {
	double longitude;
	double latitude;
	long cityCode;
}
