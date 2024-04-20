package edu.project.components;

import java.awt.Dimension;

import javax.swing.border.EmptyBorder;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import edu.project.Config;
import edu.project.tilesets.HybridTileFactory;

public class GeoMap extends JXMapKit {
	public GeoMap() {
		setDefaultProvider(DefaultProviders.Custom);
		setTileFactory(new HybridTileFactory());

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
