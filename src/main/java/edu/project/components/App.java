package edu.project.components;

import javax.swing.JFrame;

import edu.project.Config;
import edu.project.Store;

public class App extends JFrame {
	public App(Store store) {
		setTitle("Weather Forecast App");
		setSize(Config.WINDOW_INIT_WIDTH, Config.WINDOW_INIT_HEIGHT);

		add(new GeoMap(store));
	}
}
