package edu.project;

/**
 * Utility class to organize all constants used throughout the project in one
 * location
 */
public class Config {
	public static final String OPENWEATHERMAP_API_KEY = "23c541de6a2105ebf03f39791a6b7cd1";

	// Custom window initial properties
	public static final int WINDOW_INIT_WIDTH = 960;
	public static final int WINDOW_INIT_HEIGHT = 640;
	public static final int WINDOW_INIT_PADDING = 16;

	// MMSU Batac Campus Coordinates
	public static final double MAP_INIT_LAT = 18.057784;
	public static final double MAP_INIT_LON = 120.548036;
	public static final int MAP_INIT_ZOOM = 4;

	// Number of threads for fetching tiles
	public static final int MAP_THREAD_COUNT = 8;
}
