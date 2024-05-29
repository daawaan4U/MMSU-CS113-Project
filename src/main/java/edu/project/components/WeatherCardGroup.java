package edu.project.components;

import java.awt.GridLayout;

import javax.swing.JPanel;

import edu.project.Context;

public class WeatherCardGroup extends JPanel {
	public WeatherCardGroup(Context context) {
		setOpaque(false);
		setLayout(new GridLayout(1, 4, 8, 0));

		WeatherCard windCard = new WeatherCard("Wind", "m/s");
		WeatherCard humidityCard = new WeatherCard("Humidity", "%");
		WeatherCard rainRateCard = new WeatherCard("Rain Rate", "mm/h");
		WeatherCard pressureCard = new WeatherCard("Cloud", "%");

		add(windCard);
		add(humidityCard);
		add(rainRateCard);
		add(pressureCard);

		context.store.addWeatherCurrentDataListener(data -> {
			windCard.setValue(String.format("%.1f", data.wind.speed));
			humidityCard.setValue(String.format("%d", data.main.humidity));
			rainRateCard.setValue(String.format("%.1f", data.rain != null ? data.rain.one_hour : 0));
			pressureCard.setValue(String.format("%d", data.clouds.all));
		});
	}
}