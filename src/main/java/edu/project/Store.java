package edu.project;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.SwingUtilities;
import org.jxmapviewer.viewer.GeoPosition;

import edu.project.api.WeatherCurrentData;
import edu.project.api.WeatherForecast5Data;

public class Store 
{
    public Store() 
    {
        setLocation(new GeoPosition(Config.MAP_INIT_LAT, Config.MAP_INIT_LON));
    }

    /* Location */

    private GeoPosition location = new GeoPosition(0, 0);

    /**
     * Get the current location.
     * 
     * @return The current GeoPosition.
     */
    public GeoPosition getLocation() 
    {
        return location;
    }

    /**
     * Set a new location and notify listeners if the location has changed.
     * 
     * @param position The new GeoPosition.
     */
    public void setLocation(GeoPosition position) 
    {
        if (Objects.equals(location, position))
            return;

        location = position;
        SwingUtilities.invokeLater(() -> 
        {
            locationListeners.forEach(listener -> listener.accept(position));
        });
    }

    private ArrayList<Consumer<GeoPosition>> locationListeners = new ArrayList<>();

    /**
     * Add a listener that will be notified when the location changes.
     * 
     * @param listener The Consumer to add as a listener.
     */
    public void addLocationListener(Consumer<GeoPosition> listener) 
    {
        locationListeners.add(listener);
    }

    /* Weather Current Data */

    private WeatherCurrentData weatherCurrentData;

    /**
     * Get the current weather data.
     * 
     * @return The current WeatherCurrentData.
     */
    public WeatherCurrentData getWeatherCurrentData() 
    {
        return weatherCurrentData;
    }

    /**
     * Set new weather current data and notify listeners if the data has changed.
     * 
     * @param data The new WeatherCurrentData.
     */
    public void setWeatherCurrentData(WeatherCurrentData data) 
    {
        if (Objects.equals(weatherCurrentData, data))
            return;

        weatherCurrentData = data;
        SwingUtilities.invokeLater(() -> 
        {
            weatherCurrentDataListeners.forEach(listener -> listener.accept(data));
        });
    }

    private ArrayList<Consumer<WeatherCurrentData>> weatherCurrentDataListeners = new ArrayList<>();

    /**
     * Add a listener that will be notified when the weather current data changes.
     * 
     * @param listener The Consumer to add as a listener.
     */
    public void addWeatherCurrentDataListener(Consumer<WeatherCurrentData> listener) 
    {
        weatherCurrentDataListeners.add(listener);
    }

    /* Weather Forecast Data */

    private WeatherForecast5Data weatherForecast5Data;

    /**
     * Get the 5-day weather forecast data.
     * 
     * @return The current WeatherForecast5Data.
     */
    public WeatherForecast5Data getWeatherForecast5Data() 
    {
        return weatherForecast5Data;
    }

    /**
     * Set new weather forecast data and notify listeners if the data has changed.
     * 
     * @param data The new WeatherForecast5Data.
     */
    public void setWeatherForecast5Data(WeatherForecast5Data data) 
    {
        if (Objects.equals(weatherForecast5Data, data))
            return;

        weatherForecast5Data = data;
        SwingUtilities.invokeLater(() -> 
        {
            weatherForecast5DataListeners.forEach(listener -> listener.accept(data));
        });
    }

    private ArrayList<Consumer<WeatherForecast5Data>> weatherForecast5DataListeners = new ArrayList<>();

    /**
     * Add a listener that will be notified when the weather forecast data changes.
     * 
     * @param listener The Consumer to add as a listener.
     */
    public void addWeatherForecast5DataListener(Consumer<WeatherForecast5Data> listener) 
    {
        weatherForecast5DataListeners.add(listener);
    }

    /* Zoom */

    /**
     * Zoom in and notify all zoom in listeners.
     */
    public void zoomIn() 
    {
        SwingUtilities.invokeLater(() -> 
        {
            zoomInListeners.forEach(listener -> listener.run());
        });
    }

    /**
     * Zoom out and notify all zoom out listeners.
     */
    public void zoomOut() 
    {
        SwingUtilities.invokeLater(() -> 
        {
            zoomOutListeners.forEach(listener -> listener.run());
        });
    }

    private ArrayList<Runnable> zoomInListeners = new ArrayList<>();
    private ArrayList<Runnable> zoomOutListeners = new ArrayList<>();

    /**
     * Add a listener that will be notified when zooming in.
     * 
     * @param listener The Runnable to add as a listener.
     */
    public void addZoomInListener(Runnable listener) 
    {
        zoomInListeners.add(listener);
    }

    /**
     * Add a listener that will be notified when zooming out.
     * 
     * @param listener The Runnable to add as a listener.
     */
    public void addZoomOutListener(Runnable listener) 
    {
        zoomOutListeners.add(listener);
    }
}
