package edu.project.components;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.border.EmptyBorder;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import edu.project.Config;
import edu.project.tilesets.HybridTileFactory;

public class GeoMap extends JXMapKit {
	public GeoMap() {
		setDefaultProvider(DefaultProviders.Custom);
		setTileFactory(new HybridTileFactory());

		JXMapViewer mainMap = getMainMap();
		mainMap.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add 10px inner-padding
		mainMap.setOverlayPainter(null); // Remove attribution painter set by JXMapKit
		for (MouseListener listener : mainMap.getMouseListeners()) {
			// Remove zoom-by-clicking feature set by JXMapKit
			if (listener instanceof CenterMapListener)
				mainMap.removeMouseListener(listener);
		}

		JXMapViewer miniMap = getMiniMap();
		miniMap.setMinimumSize(new Dimension(150, 150)); // Resize to 150px
		miniMap.setPreferredSize(new Dimension(150, 150));

		// Configure overlay marker
		WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<DefaultWaypoint>();
		HashSet<DefaultWaypoint> waypoints = new HashSet<DefaultWaypoint>();
		waypoints.add(waypoint);
		waypointPainter.setWaypoints(waypoints);

		try {
			waypointPainter.setRenderer(new GeoMapMarker());
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}

		// Display overlay marker and listen to click events
		mainMap.setOverlayPainter(waypointPainter);
		mainMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Point point = event.getPoint();
				GeoPosition position = mainMap.convertPointToGeoPosition(point);
				setMarkedLocation(position);
			}
		});

		reset();
	}

	/**
	 * Resets the position and zoom of the map to its initial values
	 */
	public void reset() {
		GeoPosition position = new GeoPosition(Config.MAP_INIT_LAT, Config.MAP_INIT_LON);
		setCenterPosition(position);
		setMarkedLocation(position);
		setZoom(Config.MAP_INIT_ZOOM);
	}

	private DefaultWaypoint waypoint = new DefaultWaypoint();

	/**
	 * Sets the marker location to the passed geolocation {@code position}
	 */
	public void setMarkedLocation(GeoPosition position) {
		waypoint.setPosition(position);
		getMainMap().repaint();

		for (Consumer<GeoPosition> listener : markedLocationListeners) {
			listener.accept(position);
		}
	}

	private List<Consumer<GeoPosition>> markedLocationListeners = new ArrayList<Consumer<GeoPosition>>();

	/**
	 * Adds a listener for changes in the marker location
	 */
	public void addMarkedLocationListener(Consumer<GeoPosition> listener) {
		markedLocationListeners.add(listener);
	}
}
