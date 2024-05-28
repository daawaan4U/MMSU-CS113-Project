package edu.project.components;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;

import java.util.HashSet;

import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import edu.project.Config;
import edu.project.Context;
import edu.project.tilesets.HybridTileFactory;

public class GeoMap extends JXMapViewer {
	public GeoMap(Context context) {
		setTileFactory(new HybridTileFactory());

		// Add Pan Interaction
		MouseInputListener mouseInputListener = new PanMouseInputListener(this);
		addMouseListener(mouseInputListener);
		addMouseMotionListener(mouseInputListener);

		// Add Zoom Interaction
		MouseWheelListener mouseWheelListener = new ZoomMouseWheelListenerCursor(this);
		addMouseWheelListener(mouseWheelListener);

		WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
		HashSet<DefaultWaypoint> waypoints = new HashSet<>();
		DefaultWaypoint waypoint = new DefaultWaypoint();
		waypoints.add(waypoint);
		waypointPainter.setWaypoints(waypoints);

		try {
			waypointPainter.setRenderer(new GeoMapMarker());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Display overlay marker
		setOverlayPainter(waypointPainter);

		// Update location from application state on map click events
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Point point = event.getPoint();
				GeoPosition position = convertPointToGeoPosition(point);
				context.store.setLocation(position);
			}
		});

		context.store.addLocationListener(position -> {
			waypoint.setPosition(position);
			repaint();
		});

		setCenterPosition(context.store.getLocation());
		setZoom(Config.MAP_INIT_ZOOM);
	}
}
