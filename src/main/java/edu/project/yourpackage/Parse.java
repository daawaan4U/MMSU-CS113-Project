package edu.project.yourpackage;

import com.google.gson.Gson;

public class Parse
 {
    private final String cod;
    private final double message;
    private final int cnt;
    private final List[] list;
    private final City city;

    public Parse(String cod, double message, int cnt, List[] list, City city)
     {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    public static Parse fromJson(String json)
     {
        Gson gson = new Gson();
        return gson.fromJson(json, Parse.class);
    }

    public String getCod()
     {
        return cod;
    }

    public double getMessage()
     {
        return message;
    }

    public int getCnt()
     {
        return cnt;
    }

    public List[] getList()
     {
        return list;
    }

    public City getCity()
     {
        return city;
    }

    public static class List
     {
        private final int dt;
        private final Main main;
        private final Weather[] weather;
        private final Clouds clouds;
        private final Wind wind;
        private final int visibility;
        private final double pop;
        private final Sys sys;
        private final String dt_txt;

        public List(int dt, Main main, Weather[] weather, Clouds clouds, Wind wind, int visibility, double pop, Sys sys, String dt_txt)
         {
            this.dt = dt;
            this.main = main;
            this.weather = weather;
            this.clouds = clouds;
            this.wind = wind;
            this.visibility = visibility;
            this.pop = pop;
            this.sys = sys;
            this.dt_txt = dt_txt;
        }

        public int getDt()
         {
            return dt;
        }

        public Main getMain()
         {
            return main;
        }

        public Weather[] getWeather()
         {
            return weather;
        }

        public Clouds getClouds()
         {
            return clouds;
        }

        public Wind getWind()
         {
            return wind;
        }

        public int getVisibility()
         {
            return visibility;
        }

        public double getPop()
         {
            return pop;
        }

        public Sys getSys()
         {
            return sys;
        }

        public String getDt_txt()
         {
            return dt_txt;
        }
    }

    public static class Main
     {
        private final double temp;
        private final double feels_like;
        private final double temp_min;
        private final double temp_max;
        private final int pressure;
        private final int sea_level;
        private final int grnd_level;
        private final int humidity;
        private final double temp_kf;

        public Main(double temp, double feels_like, double temp_min, double temp_max, int pressure, int sea_level, int grnd_level, int humidity, double temp_kf)
         {
            this.temp = temp;
            this.feels_like = feels_like;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
            this.pressure = pressure;
            this.sea_level = sea_level;
            this.grnd_level = grnd_level;
            this.humidity = humidity;
            this.temp_kf = temp_kf;
        }

        public double getTemp()
         {
            return temp;
        }

        public double getFeels_like()
         {
            return feels_like;
        }

        public double getTemp_min()
         {
            return temp_min;
        }

        public double getTemp_max()
         {
            return temp_max;
        }

        public int getPressure()
         {
            return pressure;
        }

        public int getSea_level()
         {
            return sea_level;
        }

        public int getGrnd_level()
         {
            return grnd_level;
        }

        public int getHumidity()
         {
            return humidity;
        }

        public double getTemp_kf()
         {
            return temp_kf;
        }
    }

    public static class Weather
     {
        private final int id;
        private final String main;
        private final String description;
        private final String icon;

        public Weather(int id, String main, String description, String icon)
         {
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }

        public int getId()
         {
            return id;
        }

        public String getMain()
         {
            return main;
        }

        public String getDescription()
         {
            return description;
        }

        public String getIcon()
         {
            return icon;
        }
    }

    public static class Clouds
     {
        private final int all;

        public Clouds(int all)
         {
            this.all = all;
        }

        public int getAll()
         {
            return all;
        }
    }

    public static class Wind
     {
        private final double speed;
        private final int deg;
        private final double gust;

        public Wind(double speed, int deg, double gust)
         {
            this.speed = speed;
            this.deg = deg;
            this.gust = gust;
        }

        public double getSpeed()
         {
            return speed;
        }

        public int getDeg()
         {
            return deg;
        }

        public double getGust()
         {
            return gust;
        }
    }

    public static class Sys
     {
        private final String pod;

        public Sys(String pod)
         {
            this.pod = pod;
        }

        public String getPod()
         {
            return pod;
        }
    }

    public static class City
     {
        private final int id;
        private final String name;
        private final Coord coord;
        private final String country;
        private final int population;
        private final int timezone;
        private final long sunrise;
        private final long sunset;

        public City(int id, String name, Coord coord, String country, int population, int timezone, long sunrise, long sunset)
         {
            this.id = id;
            this.name = name;
            this.coord = coord;
            this.country = country;
            this.population = population;
            this.timezone = timezone;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public int getId()
         {
            return id;
        }

        public String getName()
         {
            return name;
        }

        public Coord getCoord()
         {
            return coord;
        }

        public String getCountry()
         {
            return country;
        }

        public int getPopulation()
         {
            return population;
        }

        public int getTimezone()
         {
            return timezone;
        }

        public long getSunrise()
         {
            return sunrise;
        }

        public long getSunset()
         {
            return sunset;
        }
    }

    public static class Coord
     {
        private final double lat;
        private final double lon;

        public Coord(double lat, double lon)
         {
            this.lat = lat;
            this.lon = lon;
        }

        public double getLat()
         {
            return lat;
        }

        public double getLon()
         {
            return lon;
        }
    }
}
