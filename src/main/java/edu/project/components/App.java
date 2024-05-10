package edu.project.components;

import javax.swing.JFrame;

import edu.project.Config;
import edu.project.Context;

public class App extends JFrame {
	public App(Context context) {
		setTitle("Weather Forecast App");
		setSize(Config.WINDOW_INIT_WIDTH, Config.WINDOW_INIT_HEIGHT);

		add(new GeoMap(context));
	}
}
