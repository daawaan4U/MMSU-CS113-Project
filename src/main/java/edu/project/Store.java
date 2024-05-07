package edu.project;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

import org.jxmapviewer.viewer.GeoPosition;

/**
 * Acts as the container for the application state and provides a
 * reactive interface for notifying any listeners (e.g. UI components) for any
 * changes.
 */
public class Store {

	public Store() {
		setLocation(new GeoPosition(Config.MAP_INIT_LAT, Config.MAP_INIT_LON));
	}

	/* Location */

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
}
