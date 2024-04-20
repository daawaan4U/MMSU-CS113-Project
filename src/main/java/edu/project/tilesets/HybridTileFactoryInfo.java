package edu.project.tilesets;

import org.jxmapviewer.viewer.TileFactoryInfo;

public class HybridTileFactoryInfo extends TileFactoryInfo {

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
		return super.getTileUrl(x, y, MAX_ZOOM - zoom);
	}
}
