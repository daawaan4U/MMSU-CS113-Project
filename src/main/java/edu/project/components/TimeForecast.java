package edu.project.components;

import edu.project.Context;
import edu.project.api.WeatherForecast5Data;

import com.formdev.flatlaf.FlatClientProperties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class TimeForecast extends JPanel {

	// Formatter for displaying the hour in a 12-hour format with AM/PM
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h a")
			.withLocale(Locale.ENGLISH); // Abbreviate the hour
	private static final Color PANEL_COLOR = new Color(224, 224, 224); // Light gray color
	private static final int ARC_WIDTH = 20; // Adjust the arc width for rounded corners
	private static final int ARC_HEIGHT = 20; // Adjust the arc height for rounded corners
	private static final int SPACING = 10; // Horizontal spacing between each hour panel
	private static final int INTERVAL_HOURS = 3; // Interval between forecast data points

	private final JPanel contentPanel;
	private final List<HourForecast> hourForecasts = new ArrayList<>();

	/**
	 * Constructor for TimeForecast panel.
	 *
	 * @param context
	 *            The application context.
	 */
	public TimeForecast(Context context) {
		// Set the style properties for the panel
		putClientProperty(FlatClientProperties.STYLE,
				"border: 12,12,12,12,shade(@background,10%),,16");
		setOpaque(false);
		setLayout(new BorderLayout());

		// Main panel containing the forecast
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(false);

		// Title panel
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setOpaque(false);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		JLabel titleLabel = new JLabel("12-Hour Forecast");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		titlePanel.add(titleLabel, BorderLayout.NORTH);

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setForeground(new Color(0, 0, 0, 0.2f));
		separator.setPreferredSize(new Dimension(separator.getPreferredSize().width, 2));
		titlePanel.add(separator, BorderLayout.SOUTH);

		mainPanel.add(titlePanel, BorderLayout.NORTH);

		// Content panel to hold the forecast panels
		contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		contentPanel.setLayout(new GridLayout(1, 5, SPACING, 0));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		add(mainPanel, BorderLayout.CENTER);

		// Add listener to update the forecast data when new data is available
		context.store.addWeatherForecast5DataListener(this::updateForecastData);
	}

	/**
	 * Update the forecast data based on the provided weather data.
	 *
	 * @param forecastData
	 *            The new weather forecast data.
	 */
	private void updateForecastData(WeatherForecast5Data forecastData) {
		hourForecasts.clear();

		List<WeatherForecast5Data.WeatherList> weatherList = forecastData.list;
		int hourCount = Math.min(5, weatherList.size());
		LocalDateTime currentTime = LocalDateTime.now();

		for (int i = 0; i < hourCount; i++) {
			LocalDateTime forecastTime = currentTime.plusHours(i * INTERVAL_HOURS);
			String iconId = weatherList.get(i).weather.get(0).icon;
			float tempKelvin = weatherList.get(i).main.temp;
			float tempCelsius = tempKelvin - 273.15f; // Convert from Kelvin to Celsius

			hourForecasts.add(new HourForecast(forecastTime, tempCelsius, iconId));
		}

		addForecastPanels();
	}

	/**
	 * Add forecast panels to the content panel.
	 */
	private void addForecastPanels() {
		contentPanel.removeAll();

		for (HourForecast forecast : hourForecasts) {
			JPanel hourForecastPanel = new JPanel(new BorderLayout());
			hourForecastPanel.setOpaque(false);

			// Hour label panel
			JPanel hourPanel = new JPanel();
			hourPanel.setOpaque(false);
			JLabel hourLabel = new JLabel(forecast.time.format(timeFormatter));
			hourLabel.setFont(new Font(hourLabel.getFont().getName(), Font.BOLD, 14));
			hourLabel.setHorizontalAlignment(SwingConstants.CENTER);
			hourPanel.add(hourLabel);
			hourForecastPanel.add(hourPanel, BorderLayout.NORTH);

			// Weather icon panel
			JPanel iconPanel = createIconPanel(forecast.iconId);
			hourForecastPanel.add(iconPanel, BorderLayout.CENTER);

			// Temperature label panel
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

	/**
	 * Create an icon panel for the given weather icon ID.
	 *
	 * @param iconId
	 *            The ID of the weather icon.
	 * @return A JPanel containing the weather icon.
	 */
	private JPanel createIconPanel(String iconId) {
		BufferedImage image = WeatherIcons.getIcon(iconId);
		ImageIcon icon = new ImageIcon(image);

		JPanel iconPanel = new JPanel() {
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
		iconPanel.setPreferredSize(new Dimension(40, 40));
		iconPanel.setMaximumSize(new Dimension(40, 40));
		iconPanel.setOpaque(false);
		return iconPanel;
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

	/**
	 * A class representing the forecast for a specific hour.
	 */
	private static class HourForecast {
		private final LocalDateTime time;
		private final float temp;
		private final String iconId;

		/**
		 * Constructor for HourForecast.
		 *
		 * @param time
		 *            The time of the forecast.
		 * @param temp
		 *            The temperature in Celsius.
		 * @param iconId
		 *            The ID of the weather icon.
		 */
		public HourForecast(LocalDateTime time, float temp, String iconId) {
			this.time = time;
			this.temp = temp;
			this.iconId = iconId;
		}
	}
}
