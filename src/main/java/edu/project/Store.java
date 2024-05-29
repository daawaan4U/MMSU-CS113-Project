package edu.project;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.SwingUtilities;
import org.jxmapviewer.viewer.GeoPosition;

import edu.project.api.WeatherCurrentData;
import edu.project.api.WeatherForecast5Data;

public class Store {
	public Store() {
		setLocation(new GeoPosition(Config.MAP_INIT_LAT, Config.MAP_INIT_LON));
	}

	/* location */

	private GeoPosition location = new GeoPosition(0, 0);

	public GeoPosition getLocation() {
		return location;
	}

	public void setLocation(GeoPosition position) {
		if (Objects.equals(location, position))
			return;

		location = position;
		SwingUtilities.invokeLater(() -> {
			locationListeners.forEach(listener -> listener.accept(position));
		});
	}

	private ArrayList<Consumer<GeoPosition>> locationListeners = new ArrayList<>();

	public void addLocationListener(Consumer<GeoPosition> listener) {
		locationListeners.add(listener);
	}

	/* weather current data */

	private WeatherCurrentData weatherCurrentData;

	public WeatherCurrentData getWeatherCurrentData() {
		return weatherCurrentData;
	}

	public void setWeatherCurrentData(WeatherCurrentData data) {
		if (Objects.equals(weatherCurrentData, data))
			return;

		weatherCurrentData = data;
		SwingUtilities.invokeLater(() -> {
			weatherCurrentDataListeners.forEach(listener -> listener.accept(data));
		});
	}

	private ArrayList<Consumer<WeatherCurrentData>> weatherCurrentDataListeners = new ArrayList<>();

	public void addWeatherCurrentDataListener(Consumer<WeatherCurrentData> listener) {
		weatherCurrentDataListeners.add(listener);
	}

	/* weather forecast data */

	private WeatherForecast5Data weatherForecast5Data;

	public WeatherForecast5Data getWeatherForecast5Data() {
		return weatherForecast5Data;
	}

	public void setWeatherForecast5Data(WeatherForecast5Data data) {
		if (Objects.equals(weatherForecast5Data, data))
			return;

		weatherForecast5Data = data;
		SwingUtilities.invokeLater(() -> {
			weatherForecast5DataListeners.forEach(listener -> listener.accept(data));
		});
	}

	private ArrayList<Consumer<WeatherForecast5Data>> weatherForecast5DataListeners = new ArrayList<>();

	public void addWeatherForecast5DataListener(Consumer<WeatherForecast5Data> listener) {
		weatherForecast5DataListeners.add(listener);
	}
}
