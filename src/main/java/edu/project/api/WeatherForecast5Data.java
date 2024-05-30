
package edu.project.api;

import java.util.List;

/**
 * @seen http://api.openweathermap.org/data/2.5/forecast
 */
public class WeatherForecast5Data {
	public List<WeatherList> list;

	public static class WeatherList {
		public Main main;
		public List<Weather> weather;
		public Wind wind;
		public String dt_txt;

		public static class Main {
			public float temp;
			public float pressure;
			public int humidity;
		}

		public static class Weather {
			public String description;
			public String icon;
		}

		public static class Wind {
			public float speed;
			public int deg;
		}
	}
}
