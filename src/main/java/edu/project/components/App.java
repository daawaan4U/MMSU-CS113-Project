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

/**
 * Root Window for the application
 */
public class App extends JFrame {
	public App(Context context) {
		setTitle("Weather Forecast App");

		// Set content-pane minimum size & preferred size instead of overall window size
		// (which includes window header)
		getContentPane().setMinimumSize(new Dimension(Config.WINDOW_INIT_WIDTH, Config.WINDOW_INIT_HEIGHT));
		getContentPane().setPreferredSize(new Dimension(Config.WINDOW_INIT_WIDTH, Config.WINDOW_INIT_HEIGHT));
		pack();
		setMinimumSize(getSize());
		setPreferredSize(getSize());

		// Exit application on window close click
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Use layered layout to allow overlaying components (Map on the background,
		// Controls as overlay)
		JLayeredPane layers = new JLayeredPane();
		add(layers);

		// Initialize map
		GeoMap geomap = new GeoMap(context);
		layers.add(geomap);
		layers.setLayer(geomap, JLayeredPane.DEFAULT_LAYER);
		layers.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Resize map to fill all space on window resize
				super.componentResized(e);
				geomap.setBounds(0, 0, layers.getWidth(), layers.getHeight());
			}
		});

		// Initialize weather island
		WeatherIsland weatherIsland = new WeatherIsland(context);

		// Disable click-throughs to the overlapped map
		weatherIsland.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				e.consume();
			}
		});

		// Set weather island size
		layers.add(weatherIsland);
		layers.setLayer(weatherIsland, JLayeredPane.MODAL_LAYER);
		weatherIsland.setBounds(
				Config.WINDOW_INIT_PADDING,
				Config.WINDOW_INIT_PADDING,
				(int) (Config.WINDOW_INIT_WIDTH * 0.4),
				Config.WINDOW_INIT_HEIGHT - Config.WINDOW_INIT_PADDING * 2);

		// Initialize zoom buttons
		ZoomButtonGroup zoomButtonGroup = new ZoomButtonGroup(context);

		// Disable click-throughts to the overlapped map
		zoomButtonGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				e.consume();
			}
		});

		// Set zoom button group size
		layers.add(zoomButtonGroup);
		layers.setLayer(zoomButtonGroup, JLayeredPane.MODAL_LAYER + 1);
		layers.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				zoomButtonGroup.setBounds(
						layers.getWidth() - 50 - 16,
						layers.getHeight() - 100 - 16,
						50,
						100);
			}
		});
	}
}
