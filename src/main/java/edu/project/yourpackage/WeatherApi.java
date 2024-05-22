//API key: 23c541de6a2105ebf03f39791a6b7cd1
package edu.project.yourpackage;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApi {
    private static final String API_KEY = "23c541de6a2105ebf03f39791a6b7cd1";
    private static final String CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String FORECAST_WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast";

    public WeatherCurrentData getCurrentWeather(String city) throws IOException {
        String urlString = CURRENT_WEATHER_URL + "?q=" + city + "&appid=" + API_KEY;
        return fetchWeatherData(urlString, WeatherCurrentData.class);
        
    }

    public WeatherForecast5Data getWeatherForecast(String city) throws IOException{
        String urlString = FORECAST_WEATHER_URL + "?q=" + city + "&appid=" + API_KEY;
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

        Gson gson = new Gson();
        return gson.fromJson(content.toString(), clazz);
    }
}
