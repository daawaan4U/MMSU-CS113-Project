package edu.project.tilesets;

import org.jxmapviewer.viewer.AbstractTileFactory;

import edu.project.Config;

/**
 * Tile Factory for Google Hybrid Maps Tileset
 */
public class HybridTileFactory extends AbstractTileFactory {
	public HybridTileFactory() {
		super(new HybridTileFactoryInfo());
		setThreadPoolSize(Config.MAP_THREAD_COUNT);
	}
}
