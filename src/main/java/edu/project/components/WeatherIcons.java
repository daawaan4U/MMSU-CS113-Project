package edu.project.components;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * An image provider for OpenWeatherMap's icons
 * 
 * @see https://openweathermap.org/weather-conditions
 */
public class WeatherIcons {

	/**
	 * Key-Value cache for the icons
	 */
	private static Map<String, BufferedImage> icons = new HashMap<>();

	/**
	 * List of icon IDs
	 */
	private static String[] iconIds = new String[] {
			// Day-time icons
			"01d", "02d", "03d", "04d", "09d", "10d", "11d", "13d", "50d",

			// Night-time icons
			"01n", "02n", "03n", "04n", "09n", "10n", "11n", "13n", "50n"
	};

	static {
		// Load resource image for each icon ID
		for (String id : iconIds) {
			try {
				String path = "/images/weather/" + id + "@2x.png";
				URL url = WeatherIcons.class.getResource(path);
				BufferedImage image = ImageIO.read(url);
				icons.put(id, image);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	/**
	 * Gets the corresponding {@code BufferedImage} for the {@code iconId}
	 */
	public static BufferedImage getIcon(String iconId) {
		return icons.get(iconId);
	}
}
