package edu.project.components;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import edu.project.Context;

import edu.project.components.weathercards.WeatherGroup;

import java.awt.BorderLayout;

public class WeatherIsland extends JPanel {
	public WeatherIsland(Context context) {
		putClientProperty(FlatClientProperties.STYLE,
				"background: tint(@background,50%); border: 8,8,8,8,shade(@background,10%),,16");
		setOpaque(false);

		setLayout(new FlowLayout(FlowLayout.CENTER));
   
    WeatherInfo weatherInfo = new WeatherInfo(context);
		
    add(weatherInfo);
		add(new WeatherGroup());
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(getBackground());
		graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16 * 2, 16 * 2);
		super.paintComponent(graphics2d);
	}
}
