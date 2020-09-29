# Task

Company SUNSHINE wants to create application allowing checking current weather
conditions in specified place. Everyone knows that often weather forecast and even
temporary reading varies between providers. Therefore stakeholders have
decided that their app will allow switching between providers. User can
decide whether he trusts provider X more
than Y. Also user can display forecast from all providers at the same
time.

Objective
is to create backend system in Java that will allow fulfill business
goal. Communication must be REST based, authorisation is omitted. There
are no other
strict requirements, own invention and keeping the standards are
expected.

Use following weather providers:

OpenWeather - [https://openweathermap.org/api](https://openweathermap.org/api)

WeatherBit - [https://www.weatherbit.io/api](https://www.weatherbit.io/api)

AccuWeather - [https://developer.accuweather.com/](https://developer.accuweather.com)

# Solution

App is built using Java and SpringBoot framework.
It gets a one day forecast for given city and weather service.
application.properties is not included as it contains api keys. Its content should look like:

```sh
logging.level.finbarre=debug

apikey.accuweather=xxxxx
apikey.openweathermap=yyyyyyyyyy
apikey.weatherbit=zzzzzzzzz
```
Developers should get proper apikeys.
### Restpoints
Current restpoints can be checked at Swagger: http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

#### Getting forecast for city and weather service. 

```sh
http://localhost:8080/one-day-forecast/?location=LOCATION&weatherService=WEATHERSEVICECODE
```


where location is city name [String] and WEATHERSEVICECODE [int] is service chosen from below list:
* 1 - accuweather.com
* 2 - openweathermap.org
* 3 - weatherbit.io 


City must be in accuweather.com database. AccuweatherLatLonService gets cityCode used by accuweather.com and latitude, longitude for openweathermap.org and weatherbit.io.


Example for Warsaw and openweathermap.org:

```sh
http://localhost:8080/one-day-forecast/?location=Warsaw&weatherService=2
```


Result:

```sh
{
   "weatherService": "openweathermap.org",
   "description": "Rain",
   "minTemperature": 11.84,
   "maxTemperature": 15.47,
   "temperatureUnit": "C"
}
```

#### Getting a forecast for a given location from all services.
```sh
http://localhost:8080/one-day-forecast/all?location=LOCATION
```


Example:

```sh
http://localhost:8080/one-day-forecast/all?location=Tokyo
```

Result:

```sh
[
    {
        "weatherService": "accuweather.com",
        "description": "Pleasant this weekend",
        "minTemperature": 17.78,
        "maxTemperature": 25.0,
        "temperatureUnit": "C"
    },
    {
        "weatherService": "openweathermap.org",
        "description": "Clouds",
        "minTemperature": 17.39,
        "maxTemperature": 25.36,
        "temperatureUnit": "C"
    },
    {
        "weatherService": "weatherbit.io",
        "description": "Broken clouds",
        "minTemperature": 19.0,
        "maxTemperature": 26.4,
        "temperatureUnit": "C"
    }
]
```

#### Additional restpoints
App can forward a full forecast from the weather service as a string, which can be parsed by frontend and used for creating more complicated views. 

```sh
http://localhost:8080/one-day-forecast/full?location=LOCATION&weatherService=WEATHERSEVICECODE
```


Example:

```sh
http://localhost:8080/one-day-forecast/full?location=Warsaw&weatherService=1
```


Forwarding a full forecasts from all weather services

```sh
http://localhost:8080/one-day-forecast/full/all?location=LOCATION
```


Example:

```sh
http://localhost:8080/one-day-forecast/full/all?location=Warsaw
```


If the city name is not found or WEATHERSEVICECODE is not in {1,2,3}, the app returns 404 HTTP code.
