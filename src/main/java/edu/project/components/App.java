package edu.project.components;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;

import edu.project.Config;
import edu.project.Context;

public class App extends JFrame {
	public App(Context context) {
		setTitle("Weather Forecast App");
		getContentPane().setMinimumSize(new Dimension(Config.WINDOW_INIT_WIDTH, Config.WINDOW_INIT_HEIGHT));
		getContentPane().setPreferredSize(new Dimension(Config.WINDOW_INIT_WIDTH, Config.WINDOW_INIT_HEIGHT));
		pack();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JLayeredPane layers = new JLayeredPane();
		add(layers);

		GeoMap geomap = new GeoMap(context);
		layers.add(geomap);
		layers.setLayer(geomap, JLayeredPane.DEFAULT_LAYER);
		layers.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				geomap.setBounds(0, 0, layers.getWidth(), layers.getHeight());
			}
		});

		WeatherIsland weatherIsland = new WeatherIsland(context);

		// Disable click-throughs to the overlapped map
		weatherIsland.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				e.consume();
			}
		});

		layers.add(weatherIsland);
		layers.setLayer(weatherIsland, JLayeredPane.MODAL_LAYER);
		weatherIsland.setBounds(
				Config.WINDOW_INIT_PADDING,
				Config.WINDOW_INIT_PADDING,
				(int) (Config.WINDOW_INIT_WIDTH * 0.4),
				Config.WINDOW_INIT_HEIGHT - Config.WINDOW_INIT_PADDING * 2);
	}
}
