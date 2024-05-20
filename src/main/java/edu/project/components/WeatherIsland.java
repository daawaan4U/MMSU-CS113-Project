package edu.project.components;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import edu.project.Context;

public class WeatherIsland extends JPanel {
	public WeatherIsland(Context context) {
		putClientProperty(FlatClientProperties.STYLE,
				"background: tint(@background,50%); border: 16,16,16,16,shade(@background,10%),,0");

		JLabel label = new JLabel("Hello World!");
		add(label);
	}
}
