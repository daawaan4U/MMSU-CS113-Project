package edu.project.tilesets;

import org.jxmapviewer.viewer.AbstractTileFactory;

import edu.project.Config;

public class HybridTileFactory extends AbstractTileFactory {
	public HybridTileFactory() {
		super(new HybridTileFactoryInfo());
		setThreadPoolSize(Config.MAP_THREAD_COUNT);
	}
}
