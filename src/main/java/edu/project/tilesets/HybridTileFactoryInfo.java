package edu.project.tilesets;

import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * Tile Factory metadata for Google Hybrid Maps Tileset
 */
public class HybridTileFactoryInfo extends TileFactoryInfo {

	// Maximum zoom available for the tileset
	private static final int MAX_ZOOM = 19;

	public HybridTileFactoryInfo() {
		super(
				"Google - Hybrid Satellite Imagery",
				0,
				MAX_ZOOM,
				MAX_ZOOM,
				256,
				true,
				true,
				"http://mt0.google.com/vt/lyrs=y&hl=en",
				"x",
				"y",
				"z");
	}

	@Override
	public String getTileUrl(int x, int y, int zoom) {
		// Invert zoom when fetching tile since JXMapViewer uses an inverted zoom range
		// (0 - max zoom in) and slippy tile APIs use the standard zoom range (0 - max
		// zoom out)
		return super.getTileUrl(x, y, MAX_ZOOM - zoom);
	}
}
