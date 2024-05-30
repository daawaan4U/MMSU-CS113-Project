package edu.project.components;

import edu.project.Context;
import edu.project.api.WeatherForecast5Data;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * The WeatherForecastPanel class displays a 5-day weather forecast in a
 * graphical user interface.
 */
public class WeatherForecastPanel extends JPanel {

	// Formatter for displaying the day of the week
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE")
			.withLocale(Locale.ENGLISH);
	private static final Color PANEL_COLOR = new Color(224, 224, 224); // Light gray color
	private static final int ARC_WIDTH = 16; // Arc width for rounded corners
	private static final int ARC_HEIGHT = 16; // Arc height for rounded corners
	private static final int SPACING = 10; // Horizontal spacing between each day's forecast panel

	/**
	 * Constructor for WeatherForecastPanel.
	 *
	 * @param context
	 *            The application context.
	 */
	public WeatherForecastPanel(Context context) {
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

		JLabel titleLabel = new JLabel("5-Day Forecast");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		titlePanel.add(titleLabel, BorderLayout.NORTH);

		// Separator line under the title
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setForeground(new Color(0, 0, 0, 0.2f));
		separator.setPreferredSize(new Dimension(separator.getPreferredSize().width, 2));
		titlePanel.add(separator, BorderLayout.SOUTH);

		mainPanel.add(titlePanel, BorderLayout.NORTH);

		// Content panel to hold the forecast panels
		JPanel contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		contentPanel.setLayout(new GridLayout(1, 5, SPACING, 0));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		add(mainPanel, BorderLayout.CENTER);

		// Add listener to update the forecast data when new data is available
		context.store.addWeatherForecast5DataListener(this::updateForecast);
	}

	/**
	 * Update the forecast data based on the provided weather data.
	 *
	 * @param forecastData
	 *            The new weather forecast data.
	 */
	private void updateForecast(WeatherForecast5Data forecastData) {
		// Get the content panel to update
		JPanel contentPanel = (JPanel) ((JPanel) getComponent(0)).getComponent(1);
		contentPanel.removeAll();

		// List of weather data for the forecast
		List<WeatherForecast5Data.WeatherList> weatherList = forecastData.list;

		// Limit the forecast to 5 days, with each day having 8 data points (3-hour
		// intervals)
		int dayCount = Math.min(5, weatherList.size() / 8);
		LocalDate currentDate = LocalDate.now();

		for (int i = 0; i < dayCount; i++) {
			LocalDate forecastDate = currentDate.plusDays(i);

			// Create a panel for each day's forecast
			JPanel dayForecastPanel = new JPanel(new BorderLayout());
			dayForecastPanel.setOpaque(false);

			// Create and add the day label
			JPanel dayPanel = new JPanel();
			dayPanel.setOpaque(false);
			JLabel dayLabel = new JLabel(forecastDate.format(dateFormatter));
			dayLabel.setFont(new Font(dayLabel.getFont().getName(), Font.BOLD, 14));
			dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
			dayPanel.add(dayLabel);
			dayForecastPanel.add(dayPanel, BorderLayout.NORTH);

			// Get the iconId for the day (assuming the first entry of the day has the
			// relevant icon)
			String iconId = weatherList.get(i * 8).weather.get(0).icon;

			// Add the icon panel with the weather icon
			JPanel iconPanel = createIconPanel(iconId);
			dayForecastPanel.add(iconPanel, BorderLayout.CENTER);

			// Calculate the average temperature for the day, converting from Kelvin to
			// Celsius
			float sumTemp = 0;
			for (int j = i * 8; j < (i + 1) * 8 && j < weatherList.size(); j++) {
				sumTemp += weatherList.get(j).main.temp;
			}
			float averageTemp = (sumTemp / 8) - 273.15f;

			// Create and add the temperature label
			JPanel temperaturePanel = new JPanel();
			temperaturePanel.setOpaque(false);
			JLabel tempLabel = new JLabel(String.format("%.1f°C", averageTemp));
			tempLabel.setFont(new Font("Arial", Font.BOLD, 13));
			tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperaturePanel.add(tempLabel);
			dayForecastPanel.add(temperaturePanel, BorderLayout.SOUTH);

			// Add the day forecast panel to the content panel
			contentPanel.add(dayForecastPanel);
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

				// Draw the image at the center of the panel
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
}
