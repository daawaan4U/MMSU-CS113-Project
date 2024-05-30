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

	/**
	 * Get the current weather data for a specific city.
	 *
	 * @param city
	 *            The name of the city.
	 * @return The current weather data for the specified city.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public WeatherCurrentData getCurrentWeather(String city) throws IOException {
		String urlString = CURRENT_WEATHER_URL + "?q=" + city + "&appid=" + Config.OPENWEATHERMAP_API_KEY;
		return fetchWeatherData(urlString, WeatherCurrentData.class);
	}

	/**
	 * Get the weather forecast data for a specific city.
	 *
	 * @param city
	 *            The name of the city.
	 * @return The weather forecast data for the specified city.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public WeatherForecast5Data getWeatherForecast(String city) throws IOException {
		String urlString = FORECAST_WEATHER_URL + "?q=" + city + "&appid=" + Config.OPENWEATHERMAP_API_KEY;
		return fetchWeatherData(urlString, WeatherForecast5Data.class);
	}

	/**
	 * Get the current weather data for a specific geographic location.
	 *
	 * @param lat
	 *            The latitude of the location.
	 * @param lon
	 *            The longitude of the location.
	 * @return The current weather data for the specified location.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public WeatherCurrentData getCurrentWeather(double lat, double lon) throws IOException {
		String urlString = CURRENT_WEATHER_URL + "?lat=" + lat + "&lon=" + lon + "&appid="
				+ Config.OPENWEATHERMAP_API_KEY;
		return fetchWeatherData(urlString, WeatherCurrentData.class);
	}

	/**
	 * Get the weather forecast data for a specific geographic location.
	 *
	 * @param lat
	 *            The latitude of the location.
	 * @param lon
	 *            The longitude of the location.
	 * @return The weather forecast data for the specified location.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public WeatherForecast5Data getWeatherForecast(double lat, double lon) throws IOException {
		String urlString = FORECAST_WEATHER_URL + "?lat=" + lat + "&lon=" + lon + "&appid="
				+ Config.OPENWEATHERMAP_API_KEY;
		return fetchWeatherData(urlString, WeatherForecast5Data.class);
	}

	/**
	 * Fetch weather data from a given URL and parse it into the specified class.
	 *
	 * @param urlString
	 *            The URL to fetch the weather data from.
	 * @param clazz
	 *            The class to parse the weather data into.
	 * @param <T>
	 *            The type of the weather data.
	 * @return The weather data parsed into the specified class.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
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
