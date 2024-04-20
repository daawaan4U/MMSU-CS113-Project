package edu.project.components;

import java.awt.Dimension;

import javax.swing.border.EmptyBorder;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import edu.project.Config;

public class GeoMap extends JXMapKit {
	public GeoMap() {
		// Use OpenStreetMap tileset with custom thread-pool size for fetching
		setDefaultProvider(DefaultProviders.OpenStreetMaps);
		DefaultTileFactory tileFactory = new DefaultTileFactory(new OSMTileFactoryInfo());
		tileFactory.setThreadPoolSize(Config.MAP_THREAD_COUNT);
		setTileFactory(tileFactory);

		JXMapViewer mainMap = getMainMap();
		mainMap.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add 10px inner-padding
		mainMap.setOverlayPainter(null); // Remove attribution painter set by JXMapKit

		JXMapViewer miniMap = getMiniMap();
		miniMap.setMinimumSize(new Dimension(150, 150)); // Resize to 150px
		miniMap.setPreferredSize(new Dimension(150, 150));

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
