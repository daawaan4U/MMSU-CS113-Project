package edu.project.services;

import edu.project.Context;
import edu.project.api.WeatherCurrentData;
import edu.project.api.WeatherForecast5Data;

public class WeatherService {
	public WeatherService(Context context) {
		context.store.addLocationListener(position -> {
			try {
				double lat = position.getLatitude();
				double lon = position.getLongitude();

				WeatherCurrentData currentData = context.weatherApi.getCurrentWeather(lat, lon);
				WeatherForecast5Data forecastData = context.weatherApi.getWeatherForecast(lat, lon);

				context.store.setWeatherCurrentData(currentData);
				context.store.setWeatherForecast5Data(forecastData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
