package Weather;

import Maps.Coordinate;

public class State {
    public Coordinate coordinate;
    private Double temperature, temperatureMin, temperatureMax;
    private String humidity, pressure;
    private String wind, description;

    public State(String temperature, String temperatureMin, String temperatureMax, String humidity, String pressure, String wind, String description) {
        this.temperature = toCelsius(Double.parseDouble(temperature));
        this.temperatureMin = toCelsius(Double.parseDouble(temperatureMin));
        this.temperatureMax = toCelsius(Double.parseDouble(temperatureMax));
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.description = description;
    }
    
    private Double toCelsius(Double input)
    {
        return input-273.15;
    }
    
    @Override
    public String toString(){
        return  "Temperature: " + temperature.toString().substring(0, 5) + "\n" +
                "Description: " + description + "\n" +
                //"Minimum: " + temperatureMin +
                //" Maximum: " + temperatureMax + "\n" +
                "Humidity: " + humidity + "%" +
                " Pressure: " + pressure + "hPa\n" +
                "Wind: " + wind;
    }

    public String getTemperature() {
        return ""+temperature.shortValue()+" °C";
    }

    public String getHumidity() {
        return humidity+" %";
    }

    public String getPressure() {
        return pressure+" hPa";
    }

    public String getWind() {
        return wind;
    }

    public String getDescription() {
        return description;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
