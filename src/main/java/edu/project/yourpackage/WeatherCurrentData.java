package edu.project.yourpackage;

public class WeatherCurrentData {
    public String name;
    public Main main;
    public Wind wind;

    public static class Main {
        public float temp;
        public float pressure;
        public int humidity;
    }

    public static class Wind {
        public float speed;
        public int deg;
    }
}
