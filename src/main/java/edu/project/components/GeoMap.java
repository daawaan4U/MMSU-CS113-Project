package edu.project.components;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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

/**
 * Custom map component using the JXMapViewer library
 */
public class GeoMap extends JXMapViewer {
	public GeoMap(Context context) {
		// Use Google Hybrid Map tile factory
		setTileFactory(new HybridTileFactory());

		// Add Pan Interaction
		MouseInputListener mouseInputListener = new PanMouseInputListener(this);
		addMouseListener(mouseInputListener);
		addMouseMotionListener(mouseInputListener);

		// Add Zoom Interaction with a zoom-out limiter
		addMouseWheelListener(new ZoomMouseWheelListenerCursor(this) {
			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) {
				// If wheel rotation indicates a zoom-out, check if can zoom-out before
				// proceeding
				if (evt.getWheelRotation() > 0 && !canZoomOut())
					return;
				super.mouseWheelMoved(evt);
			}
		});

		// Listen Zoom requests from store
		context.store.addZoomInListener(() -> {
			setZoom(getZoom() - 1);
		});
		context.store.addZoomOutListener(() -> {
			if (!canZoomOut())
				return;
			setZoom(getZoom() + 1);
		});

		// Add Focus on click
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocus();
				super.mouseClicked(e);
			}
		});

		// Initialize waypoint, the marker on the map
		WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
		HashSet<DefaultWaypoint> waypoints = new HashSet<>();
		DefaultWaypoint waypoint = new DefaultWaypoint();
		waypoints.add(waypoint);
		waypointPainter.setWaypoints(waypoints);
		waypointPainter.setRenderer(new GeoMapMarker());

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

		// Change marker location on location updates
		context.store.addLocationListener(position -> {
			waypoint.setPosition(position);
			repaint();
		});

		// Initialize map center & zoom from config
		setCenterPosition(context.store.getLocation());
		setZoom(Config.MAP_INIT_ZOOM);
	}

	/**
	 * Checks if user can still zoom out. This method exists for preventing the user
	 * from zooming out too much, causing the map to show empty spaces outside world
	 * tileset borders
	 */
	private boolean canZoomOut() {
		// Invert zoom when fetching tile since JXMapViewer uses an inverted zoom range
		// (0 - max zoom in) and slippy tile APIs use the standard zoom range (0 - max
		// zoom out)
		int zoom = getZoom();
		int maxZoom = getTileFactory().getInfo().getMaximumZoomLevel();
		int invertedZoom = maxZoom - zoom;

		// Set maximum zoom-out to zoom-level 4
		return (invertedZoom >= 4);
	}
}
