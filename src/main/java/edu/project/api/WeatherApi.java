package edu.project.api;

import com.google.gson.Gson;

import edu.project.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApi {
	private static final String CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
	private static final String FORECAST_WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast";

	public WeatherCurrentData getCurrentWeather(String city) throws IOException {
		String urlString = CURRENT_WEATHER_URL + "?q=" + city + "&appid=" + Config.OPENWEATHERMAP_API_KEY;
		return fetchWeatherData(urlString, WeatherCurrentData.class);
	}

	public WeatherForecast5Data getWeatherForecast(String city) throws IOException {
		String urlString = FORECAST_WEATHER_URL + "?q=" + city + "&appid=" + Config.OPENWEATHERMAP_API_KEY;
		return fetchWeatherData(urlString, WeatherForecast5Data.class);
	}

	public WeatherCurrentData getCurrentWeather(double lat, double lon) throws IOException {
		String urlString = CURRENT_WEATHER_URL + "?lat=" + lat + "&lon=" + lon + "&appid="
				+ Config.OPENWEATHERMAP_API_KEY;
		return fetchWeatherData(urlString, WeatherCurrentData.class);
	}

	public WeatherForecast5Data getWeatherForecast(double lat, double lon) throws IOException {
		String urlString = FORECAST_WEATHER_URL + "?lat=" + lat + "&lon=" + lon + "&appid="
				+ Config.OPENWEATHERMAP_API_KEY;
		return fetchWeatherData(urlString, WeatherForecast5Data.class);
	}

	private <T> T fetchWeatherData(String urlString, Class<T> clazz) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		StringBuilder content;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			String inputLine;
			content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
		}

		conn.disconnect();

		String json = content.toString();
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
}
