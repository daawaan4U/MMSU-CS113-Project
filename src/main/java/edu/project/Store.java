package edu.project;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.SwingUtilities;
import org.jxmapviewer.viewer.GeoPosition;

import edu.project.api.WeatherCurrentData;
import edu.project.api.WeatherForecast5Data;

/**
 * Application State & Event delegation container with a Pub-Sub API for
 * notifying & listening
 * changes
 */
public class Store {
	public Store() {
		setLocation(new GeoPosition(Config.MAP_INIT_LAT, Config.MAP_INIT_LON));
	}

	private GeoPosition location = new GeoPosition(0, 0);

	/**
	 * Gets current location
	 */
	public GeoPosition getLocation() {
		return location;
	}

	/**
	 * Sets current location and notifies listeners for changes
	 */
	public void setLocation(GeoPosition position) {
		// Skip function if input data is the same
		if (Objects.equals(location, position))
			return;

		// Update data and notify listeners about the change
		location = position;
		SwingUtilities.invokeLater(() -> {
			locationListeners.forEach(listener -> listener.accept(position));
		});
	}

	// List of listeners for location changes
	private ArrayList<Consumer<GeoPosition>> locationListeners = new ArrayList<>();

	/**
	 * Adds a listener for location changes
	 */
	public void addLocationListener(Consumer<GeoPosition> listener) {
		locationListeners.add(listener);
	}

	private WeatherCurrentData weatherCurrentData;

	/**
	 * Gets last fetched weather current data
	 */
	public WeatherCurrentData getWeatherCurrentData() {
		return weatherCurrentData;
	}

	/*
	 * Sets the new fetched weather current data and notifies listeners for changes
	 */
	public void setWeatherCurrentData(WeatherCurrentData data) {
		// Skip function if input data is the same
		if (Objects.equals(weatherCurrentData, data))
			return;

		// Update data and notify listeners about the change
		weatherCurrentData = data;
		SwingUtilities.invokeLater(() -> {
			weatherCurrentDataListeners.forEach(listener -> listener.accept(data));
		});
	}

	// List of listeners for weather current data changes
	private ArrayList<Consumer<WeatherCurrentData>> weatherCurrentDataListeners = new ArrayList<>();

	/**
	 * Adds a listener for weather current data changes
	 */
	public void addWeatherCurrentDataListener(Consumer<WeatherCurrentData> listener) {
		weatherCurrentDataListeners.add(listener);
	}

	private WeatherForecast5Data weatherForecast5Data;

	/**
	 * Gets last fetched weather forecast data
	 */
	public WeatherForecast5Data getWeatherForecast5Data() {
		return weatherForecast5Data;
	}

	/**
	 * Sets the last fetched weather forecast data
	 */
	public void setWeatherForecast5Data(WeatherForecast5Data data) {
		// Skip function if input data is the same
		if (Objects.equals(weatherForecast5Data, data))
			return;

		// Update data and notify listeners about the change
		weatherForecast5Data = data;
		SwingUtilities.invokeLater(() -> {
			weatherForecast5DataListeners.forEach(listener -> listener.accept(data));
		});
	}

	// List of listeners for weather forecast data changes
	private ArrayList<Consumer<WeatherForecast5Data>> weatherForecast5DataListeners = new ArrayList<>();

	/**
	 * Adds a listener for weather forecast data changes
	 */
	public void addWeatherForecast5DataListener(Consumer<WeatherForecast5Data> listener) {
		weatherForecast5DataListeners.add(listener);
	}

	/**
	 * Triggers a Zoom-In request for the map
	 */
	public void zoomIn() {
		// Notify listeners about a Zoom-In request
		SwingUtilities.invokeLater(() -> {
			zoomInListeners.forEach(listener -> listener.run());
		});
	}

	/**
	 * Triggers a Zoom-Out request for the map
	 */
	public void zoomOut() {
		// Notify listeners about a Zoom-Out request
		SwingUtilities.invokeLater(() -> {
			zoomOutListeners.forEach(listener -> listener.run());
		});
	}

	// List of listeners for zoom-in & zoom-out requests
	private ArrayList<Runnable> zoomInListeners = new ArrayList<>();
	private ArrayList<Runnable> zoomOutListeners = new ArrayList<>();

	/**
	 * Adds a listener for Zoom-In requests for the map
	 */
	public void addZoomInListener(Runnable listener) {
		zoomInListeners.add(listener);
	}

	/**
	 * Adds a listener for Zoom-Out requests for the map
	 */
	public void addZoomOutListener(Runnable listener) {
		zoomOutListeners.add(listener);
	}
}
