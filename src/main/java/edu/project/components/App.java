package edu.project.components;

import javax.swing.JFrame;

import edu.project.Config;

public class App extends JFrame {
	public App() {
		setTitle("Weather Forecast App");
		setSize(Config.WINDOW_INIT_WIDTH, Config.WINDOW_INIT_HEIGHT);

		add(new GeoMap());
	}
}
