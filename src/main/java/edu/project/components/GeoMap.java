package edu.project.components;

import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import edu.project.Config;

public class GeoMap extends JXMapViewer {

	public GeoMap() {
		// Enable panning interaction using mouse movements
		MouseInputListener panMouse = new PanMouseInputListener(this);
		addMouseListener(panMouse);
		addMouseMotionListener(panMouse);

		// Enable zooming interaction using the mouse wheel
		ZoomMouseWheelListenerCursor zoomWheel = new ZoomMouseWheelListenerCursor(this);
		addMouseWheelListener(zoomWheel);
	}

	public void setup() {
		setTileFactory(
				new DefaultTileFactory(
						new OSMTileFactoryInfo("", "https://b.tile.openstreetmap.fr/hot/")));

		reset();
	}

	/**
	 * Resets the position and zoom of the map to its initial values
	 */
	public void reset() {
		setCenterPosition(new GeoPosition(Config.MAP_INIT_LAT, Config.MAP_INIT_LON));
		setZoom(Config.MAP_INIT_ZOOM);
	}
}
