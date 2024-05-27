package edu.project.components.WeatherCards;

import javax.swing.*;
import java.awt.*;

public class WeatherGroup extends JPanel {
	public WeatherGroup() {
		setLayout(new GridLayout());
		add(new WeatherWindCard("16"));
		add(new WeatherHumidityCard("50"));
		add(new WeatherRainRateCard("0"));
		add(new WeatherUVCard("5"));
	}
}