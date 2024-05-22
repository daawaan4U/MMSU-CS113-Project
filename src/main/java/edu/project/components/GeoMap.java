package edu.project.components;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.border.EmptyBorder;
import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import edu.project.Config;
import edu.project.Context;
import edu.project.tilesets.HybridTileFactory;
import edu.project.yourpackage.WeatherCurrentData;
import edu.project.yourpackage.WeatherForecast5Data;

public class GeoMap extends JXMapKit {

	private Context context;
	private static final int MAP_INIT_ZOOM = 10;

	public GeoMap(Context context) {
		this.context = context;
		setDefaultProvider(DefaultProviders.Custom);
		setTileFactory(new HybridTileFactory());

		JXMapViewer mainMap = getMainMap();
		mainMap.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainMap.setOverlayPainter(null);
		for (MouseListener listener : mainMap.getMouseListeners()) {
			if (listener instanceof CenterMapListener)
				mainMap.removeMouseListener(listener);
		}

		JXMapViewer miniMap = getMiniMap();
		miniMap.setMinimumSize(new Dimension(150, 150));
		miniMap.setPreferredSize(new Dimension(150, 150));

		WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<DefaultWaypoint>();
		HashSet<DefaultWaypoint> waypoints = new HashSet<DefaultWaypoint>();
		DefaultWaypoint waypoint = new DefaultWaypoint();
		waypoints.add(waypoint);
		waypointPainter.setWaypoints(waypoints);
		try {
			waypointPainter.setRenderer(new GeoMapMarker());
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}

		mainMap.setOverlayPainter(waypointPainter);

		mainMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Point point = event.getPoint();
				GeoPosition position = mainMap.convertPointToGeoPosition(point);
				context.store.setLocation(position);

				// Fetch weather data for the new location
				try {
					String city = "Batac,PH"; // You can change this to use actual city name from position
					WeatherCurrentData currentData = context.getCurrentWeather(city);
					WeatherForecast5Data forecastData = context.getWeatherForecast(city);

					// Update UI with the fetched weather data
					System.out.println("City: " + currentData.name);
					System.out.println("Temperature: " + (currentData.main.temp - 273.15) + "°C");
					System.out.println("Weather: " + forecastData.list.get(0).weather.get(0).description);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		context.store.addLocationListener(position -> {
			waypoint.setPosition(position);
			getMainMap().repaint();
		});

		setCenterPosition(context.store.getLocation());
		setZoom(Config.MAP_INIT_ZOOM);
	}
}