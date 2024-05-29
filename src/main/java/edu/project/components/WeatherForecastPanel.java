package edu.project.components;

import edu.project.Context;
import edu.project.api.WeatherForecast5Data;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherForecastPanel extends JPanel {
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE")
			.withLocale(Locale.ENGLISH);
	private static final Color PANEL_COLOR = new Color(169, 169, 169); // Light gray color
	private static final int ARC_WIDTH = 20;
	private static final int ARC_HEIGHT = 20;
	private static final int SPACING = 20;

	private final JPanel contentPanel;
	private final List<DayForecast> dayForecasts = new ArrayList<>();

	public WeatherForecastPanel(Context context) {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 19));
		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(false);

		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setOpaque(false);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		JLabel titleLabel = new JLabel("5-Day Forecast");
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
		dayForecasts.clear();

		List<WeatherForecast5Data.WeatherList> weatherList = forecastData.list;
		int dayCount = Math.min(5, weatherList.size() / 8);
		LocalDate currentDate = LocalDate.now();

		for (int i = 0; i < dayCount; i++) {
			LocalDate forecastDate = currentDate.plusDays(i);
			float sumTemp = 0;
			for (int j = i * 8; j < (i + 1) * 8 && j < weatherList.size(); j++) {
				sumTemp += weatherList.get(j).main.temp;
			}
			float averageTemp = (sumTemp / 8) - 273.15f;

			dayForecasts.add(new DayForecast(forecastDate, averageTemp));
		}

		addForecastPanels();
	}

	private void addForecastPanels() {
		contentPanel.removeAll();

		for (DayForecast forecast : dayForecasts) {
			JPanel dayForecastPanel = new JPanel(new BorderLayout());
			dayForecastPanel.setOpaque(false);

			JPanel dayPanel = new JPanel();
			dayPanel.setOpaque(false);
			JLabel dayLabel = new JLabel(forecast.date.format(dateFormatter));
			dayLabel.setFont(new Font(dayLabel.getFont().getName(), Font.BOLD, 14));
			dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
			dayPanel.add(dayLabel);
			dayForecastPanel.add(dayPanel, BorderLayout.NORTH);

			JPanel separatorPanel = createSeparatorPanel();
			dayForecastPanel.add(separatorPanel, BorderLayout.CENTER);

			JPanel temperaturePanel = new JPanel();
			temperaturePanel.setOpaque(false);
			JLabel tempLabel = new JLabel(String.format("%.0f°", forecast.averageTemp));
			tempLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperaturePanel.add(tempLabel);
			dayForecastPanel.add(temperaturePanel, BorderLayout.SOUTH);

			contentPanel.add(dayForecastPanel);
		}

		revalidate();
		repaint();
	}

	private JPanel createSeparatorPanel() {
		ImageIcon icon = new ImageIcon("path/to/your/image.png");

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

	private static class DayForecast {
		private final LocalDate date;
		private final float averageTemp;

		public DayForecast(LocalDate date, float averageTemp) {
			this.date = date;
			this.averageTemp = averageTemp;
		}
	}
}
