package edu.project.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import edu.project.Context;

/**
 * Container for displaying weather data related UI
 */
public class WeatherIsland extends JPanel {
	public WeatherIsland(Context context) {
		// Add FlatLaf rounded borders
		putClientProperty(FlatClientProperties.STYLE, "border: 8,8,8,8,shade(@background,10%),,16");
		setOpaque(false);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		WeatherInfo weatherInfo = new WeatherInfo(context);

		// Maximize Width and Minimize Height
		weatherInfo.setMaximumSize(
				new Dimension(
						weatherInfo.getMaximumSize().width,
						weatherInfo.getPreferredSize().height));

		add(weatherInfo);

		// Add 8 units vertical gap
		add(Box.createVerticalStrut(8));

		WeatherCardGroup weatherGroup = new WeatherCardGroup(context);

		// Maximize Width and Minimize Height
		weatherGroup.setMaximumSize(
				new Dimension(
						weatherGroup.getMaximumSize().width,
						weatherGroup.getPreferredSize().height));

		add(weatherGroup);

		// Add 8 units vertical gap
		add(Box.createVerticalStrut(8));

		WeatherForecastPanel weatherForecastPanel = new WeatherForecastPanel(context);

		// Maximize Width and Height
		weatherForecastPanel.setMaximumSize(
				new Dimension(
						weatherForecastPanel.getMaximumSize().width,
						weatherForecastPanel.getMaximumSize().height));

		add(weatherForecastPanel);

		// Add 8 units vertical gap
		add(Box.createVerticalStrut(8));

		TimeForecast timeForecast = new TimeForecast(context);

		// Maximize Width and Height
		timeForecast.setMaximumSize(
				new Dimension(
						timeForecast.getMaximumSize().width,
						timeForecast.getMaximumSize().height));

		add(timeForecast);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		// Paint a rounded-rect for the background
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(getBackground());
		graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
		super.paintComponent(graphics2d);
	}
}
