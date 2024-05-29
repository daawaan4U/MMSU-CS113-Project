package edu.project.services;

import java.io.IOException;

import javax.swing.SwingWorker;

import edu.project.Context;
import edu.project.api.WeatherCurrentData;
import edu.project.api.WeatherForecast5Data;

/**
 * Service for handling weather data changes
 */
public class WeatherService {
	public WeatherService(Context context) {

		// Register listener for location changes
		context.store.addLocationListener(position -> {
			double lat = position.getLatitude();
			double lon = position.getLongitude();

			// Fetch weather current data from worker thread
			new SwingWorker<WeatherCurrentData, Void>() {
				@Override
				protected WeatherCurrentData doInBackground() throws IOException {
					return context.weatherApi.getCurrentWeather(lat, lon);
				}

				@Override
				protected void done() {
					try {
						context.store.setWeatherCurrentData(get());
					} catch (Exception exception) {
						exception.printStackTrace();
					}

				}
			}.execute();

			// Fetch weather forecast data from worker thread
			new SwingWorker<WeatherForecast5Data, Void>() {
				@Override
				protected WeatherForecast5Data doInBackground() throws IOException {
					return context.weatherApi.getWeatherForecast(lat, lon);
				}

				@Override
				protected void done() {
					try {
						context.store.setWeatherForecast5Data(get());
					} catch (Exception exception) {
						exception.printStackTrace();
					}

				}
			}.execute();
		});
	}
}
