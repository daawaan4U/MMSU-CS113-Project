

package edu.project.api;

import com.google.gson.annotations.SerializedName;
/**
 *  @seen http://api.openweathermap.org/data/2.5/weather
 */
public class WeatherCurrentData {
	public String name;
	public Main main;
	public Wind wind;
	public Clouds clouds;
	public Rain rain;
	public Sys sys;
	public int visibility;
	public long dt;

	public static class Main {
		public float temp;
		public float feels_like;
		public float temp_min;
		public float temp_max;
		public float pressure;
		public int humidity;
		public float sea_level;
		public float grnd_level;
	}

	public static class Wind {
		public float speed;
		public int deg;
		public float gust;
	}

	public static class Clouds {
		public int all;
	}

	public static class Rain {
		@SerializedName("1h")
		public float one_hour;
	}

	public static class Sys {
		public long sunrise;
		public long sunset;
	}
}
