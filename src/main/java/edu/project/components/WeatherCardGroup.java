package edu.project.components;

import java.awt.GridLayout;

import javax.swing.JPanel;

import edu.project.Context;

public class WeatherCardGroup extends JPanel {
	public WeatherCardGroup(Context context) {
		setOpaque(false);

		// Set the layout manager to GridLayout with 1 row, 4 columns, 8px horizontal
		// gap, and 0px vertical gap
		setLayout(new GridLayout(1, 4, 8, 0));

		// Create WeatherCard instances for wind, humidity, rain rate, and cloud
		// coverage
		WeatherCard windCard = new WeatherCard("Wind", "m/s");
		WeatherCard humidityCard = new WeatherCard("Humidity", "%");
		WeatherCard rainRateCard = new WeatherCard("Rain Rate", "mm/h");
		WeatherCard pressureCard = new WeatherCard("Cloud", "%");

		add(windCard);
		add(humidityCard);
		add(rainRateCard);
		add(pressureCard);

		// Add a listener for current weather data updates
		context.store.addWeatherCurrentDataListener(data -> {

			// update weathercard value
			windCard.setValue(String.format("%.1f", data.wind.speed));
			humidityCard.setValue(String.format("%d", data.main.humidity));

			// Update the rain rate card value with the rain rate (default to 0 if null)
			rainRateCard.setValue(String.format("%.1f", data.rain != null ? data.rain.one_hour : 0));

			// Update the pressure card value with the cloud coverage percentage
			pressureCard.setValue(String.format("%d", data.clouds.all));
		});
	}
}