package edu.project.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import edu.project.Context;
import java.awt.BorderLayout;

public class WeatherIsland extends JPanel {
	public WeatherIsland(Context context) {
		putClientProperty(FlatClientProperties.STYLE,
				"background: tint(@background,50%); border: 16,16,16,16,shade(@background,10%),,16");
		setOpaque(false);

		setLayout(new BorderLayout());

		WeatherInfo weatherInfo = new WeatherInfo(context);
		add(weatherInfo, BorderLayout.CENTER);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(getBackground());
		graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16 + 12, 16 + 12);
		super.paintComponent(graphics2d);
	}
}
