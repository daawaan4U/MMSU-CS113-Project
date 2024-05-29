package edu.project.components;

import edu.project.Context;
import edu.project.api.WeatherForecast5Data;

import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimeForecast extends JPanel {

	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h a")
			.withLocale(Locale.ENGLISH); // Abbreviate the hour
	private static final Color PANEL_COLOR = new Color(224, 224, 224); // Light gray color
	private static final int ARC_WIDTH = 20; // Adjust the arc width for rounded corners
	private static final int ARC_HEIGHT = 20; // Adjust the arc height for rounded corners
	private static final int SPACING = 10; // Horizontal spacing between each hour panel
	private static final int INTERVAL_HOURS = 3;

	private final JPanel contentPanel;
	private final List<HourForecast> hourForecasts = new ArrayList<>();

	public TimeForecast(Context context) {
		putClientProperty(FlatClientProperties.STYLE,
				"border: 16,16,16,16,shade(@background,10%),,16");
		setOpaque(false);
		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(false);

		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setOpaque(false);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		JLabel titleLabel = new JLabel("5-Hour Forecast");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		titlePanel.add(titleLabel, BorderLayout.NORTH);

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setForeground(Color.BLACK);
		titlePanel.add(separator, BorderLayout.SOUTH);

		mainPanel.add(titlePanel, BorderLayout.NORTH);

		contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		contentPanel.setLayout(new GridLayout(1, 5, SPACING, 0));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		add(mainPanel, BorderLayout.CENTER);

		context.store.addWeatherForecast5DataListener(this::updateForecastData);
	}

	private void updateForecastData(WeatherForecast5Data forecastData) {
		hourForecasts.clear();

		List<WeatherForecast5Data.WeatherList> weatherList = forecastData.list;
		int hourCount = Math.min(5, weatherList.size());
		LocalDateTime currentTime = LocalDateTime.now();

		for (int i = 0; i < hourCount; i++) {
			LocalDateTime forecastTime = currentTime.plusHours(i * INTERVAL_HOURS);
			float tempKelvin = weatherList.get(i).main.temp;
			float tempCelsius = tempKelvin - 273.15f; // Convert from Kelvin to Celsius

			hourForecasts.add(new HourForecast(forecastTime, tempCelsius));
		}

		addForecastPanels();
	}

	private void addForecastPanels() {
		contentPanel.removeAll();

		for (HourForecast forecast : hourForecasts) {
			JPanel hourForecastPanel = new JPanel(new BorderLayout());
			hourForecastPanel.setOpaque(false);

			JPanel hourPanel = new JPanel();
			hourPanel.setOpaque(false);
			JLabel hourLabel = new JLabel(forecast.time.format(timeFormatter));
			hourLabel.setFont(new Font(hourLabel.getFont().getName(), Font.BOLD, 14));
			hourLabel.setHorizontalAlignment(SwingConstants.CENTER);
			hourPanel.add(hourLabel);
			hourForecastPanel.add(hourPanel, BorderLayout.NORTH);

			JPanel separatorPanel = createSeparatorPanel();
			hourForecastPanel.add(separatorPanel, BorderLayout.CENTER);

			JPanel temperaturePanel = new JPanel();
			temperaturePanel.setOpaque(false);
			JLabel tempLabel = new JLabel(String.format("%.0f°", forecast.temp));
			tempLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperaturePanel.add(tempLabel);
			hourForecastPanel.add(temperaturePanel, BorderLayout.SOUTH);

			contentPanel.add(hourForecastPanel);
		}

		revalidate();
		repaint();
	}

	private JPanel createSeparatorPanel() {
		ImageIcon icon = new ImageIcon("pic here");

		JPanel separatorPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				int x = (getWidth() - icon.getIconWidth()) / 2;
				int y = (getHeight() - icon.getIconHeight()) / 2;
				icon.paintIcon(this, g2d, x, y);
			}
		};
		separatorPanel.setPreferredSize(new Dimension(40, 40));
		separatorPanel.setMaximumSize(new Dimension(40, 40));
		return separatorPanel;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(PANEL_COLOR);
		g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT));
		g2d.dispose();
	}

	private static class HourForecast {
		private final LocalDateTime time;
		private final float temp;

		public HourForecast(LocalDateTime time, float temp) {
			this.time = time;
			this.temp = temp;
		}
	}
}
