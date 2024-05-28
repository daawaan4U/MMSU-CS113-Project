package edu.project;

import edu.project.api.WeatherApi;

/**
 * Acts as the service locator for all the services in the project
 */
public class Context {
	public final Store store;
	public final WeatherApi weatherApi;

	public Context() {
		this.store = new Store();
		this.weatherApi = new WeatherApi();
	}
}
