package edu.project.components;

import java.awt.Font;

import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import edu.project.Context;

import java.awt.BorderLayout;

import java.util.Date;

public class WeatherInfo extends JPanel {
	public WeatherInfo(Context context) {
		setLayout(new BorderLayout());
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setOpaque(false);

		JLabel dateLabel = new JLabel("Saturday, May 4");
		dateLabel.setFont(new Font(dateLabel.getFont().getName(), Font.BOLD, 18));

		JLabel timeLabel = new JLabel("8:30");

		JLabel skyLabel = new JLabel("Clear Sky");
		Font font = skyLabel.getFont();
		skyLabel.setFont(new Font(font.getName(), font.getStyle(), 14));

		JLabel feelsLikeLabel = new JLabel("<html>Feels like <b>31°C</b></html>");
		Font feelsFont = feelsLikeLabel.getFont();
		feelsLikeLabel.setFont(new Font(feelsFont.getName(), feelsFont.getStyle(), 14));

		leftPanel.add(dateLabel);
		leftPanel.add(timeLabel);
		leftPanel.add(skyLabel);
		leftPanel.add(feelsLikeLabel);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setOpaque(false);

		JLabel locationLabel = new JLabel("Quiling Sur");
		locationLabel.setFont(new Font(locationLabel.getFont().getName(), Font.BOLD, 18));

		JLabel tempLabel = new JLabel("39°C");
		tempLabel.setFont(new Font(tempLabel.getFont().getName(), Font.BOLD, 25));

		JLabel highLowTempLabel = new JLabel("H: 40°C / L: 32°C");
		highLowTempLabel.setFont(
				new Font(highLowTempLabel.getFont().getName(), Font.BOLD, highLowTempLabel.getFont().getSize()));

		rightPanel.add(locationLabel);
		rightPanel.add(tempLabel);
		rightPanel.add(highLowTempLabel);

		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);

		context.store.addWeatherCurrentDataListener(data -> {
			// leftpanel
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d"); // Example format: "Saturday, May 4"
			dateLabel.setText(dateFormat.format(new Date(data.dt * 1000L)));

			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a"); // Example format: "8:30 PM"
			timeLabel.setText(timeFormat.format(new Date()));

			feelsLikeLabel.setText(
					"<html>Feels like <b>" + String.format("%.2f", data.main.feels_like - 273.15f) + "°C</b></html>");

			// right panel
			locationLabel.setText(data.name);
			tempLabel.setText(String.format("%.2f°C", data.main.temp - 273.15f));
			highLowTempLabel.setText(
					String.format("H: %.2f°C / L: %.2f°C", data.main.temp_max - 273.15f, data.main.temp_min - 273.15f));

		});

		context.store.addWeatherForecast5DataListener(data -> {
			// Get the weather description
			String description = data.list.get(0).weather.get(0).description;

			// Capitalize the first letter
			String capitalizedDescription = description.substring(0, 1).toUpperCase() + description.substring(1);

			// Set the capitalized description to the skyLabel
			skyLabel.setText(capitalizedDescription);
		});

	}
}
