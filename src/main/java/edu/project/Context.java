package edu.project;

import edu.project.yourpackage.WeatherApi;
import edu.project.yourpackage.WeatherCurrentData;
import edu.project.yourpackage.WeatherForecast5Data;
import java.io.IOException;  // <-- Make sure this import is present
import org.jxmapviewer.viewer.GeoPosition;

public class Context {
    public Store store;
    private WeatherApi weatherApi;

    public Context() {
        this.store = new Store();
        this.weatherApi = new WeatherApi();
    }

    public WeatherCurrentData getCurrentWeather(String city) throws IOException {
        return weatherApi.getCurrentWeather(city);
    }

    public WeatherForecast5Data getWeatherForecast(String city) throws IOException {
        return weatherApi.getWeatherForecast(city);
    }
}
