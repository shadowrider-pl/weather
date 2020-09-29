package finbarre.weather.payload;

import java.io.Serializable;

import lombok.Data;


@Data
public class OneDayForecast implements Serializable {
	
	String weatherService;
	String description;
	double minTemperature;
	double maxTemperature;
	String temperatureUnit;

	public static final class Builder {

		String weatherService;
		String description;
		double minTemperature;
		double maxTemperature;
		String temperatureUnit;

        public Builder weatherService(String weatherService) {
           this.weatherService = weatherService;
           return this;
        }

        public Builder minTemperature(double minTemperature) {
           this.minTemperature = minTemperature;
           return this;
        }

        public Builder maxTemperature(double d) {
           this.maxTemperature = d;
           return this;
        }

        public Builder description(String description) {
           this.description = description;
           return this;
        }

        public Builder temperatureUnit(String temperatureUnit) {
           this.temperatureUnit = temperatureUnit;
           return this;
        }
        public OneDayForecast build() {
        	OneDayForecast oneDayForecast = new OneDayForecast();
        	oneDayForecast.weatherService = this.weatherService;
        	oneDayForecast.minTemperature = this.minTemperature;
        	oneDayForecast.maxTemperature = this.maxTemperature;
        	oneDayForecast.description = this.description;
        	oneDayForecast.temperatureUnit = this.temperatureUnit;
            return oneDayForecast;
        }
	}
}
