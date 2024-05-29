package edu.project.components.weathercards;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class WeatherGroup extends JPanel {
	public WeatherGroup() {
		setLayout(new GridLayout(1, 4, 8, 0));
		add(new WeatherWindCard("16"));
		add(new WeatherHumidityCard("50"));
		add(new WeatherRainRateCard("0"));
		add(new WeatherUVCard("5"));
	}
}