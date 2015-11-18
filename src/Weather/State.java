package Weather;

public class State {
    private Double temperature, temperatureMin, temperatureMax;
    private String humidity, pressure;
    private String wind;

    public State(String temperature, String temperatureMin, String temperatureMax, String humidity, String pressure, String wind) {
        this.temperature = toCelsius(Double.parseDouble(temperature));
        this.temperatureMin = toCelsius(Double.parseDouble(temperatureMin));
        this.temperatureMax = toCelsius(Double.parseDouble(temperatureMax));
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
    }
    
    private Double toCelsius(Double input)
    {
        return input-273.15;
    }
    
    @Override
    public String toString(){
        return  "Temperature: " + temperature + "\n" +
                "Minimum: " + temperatureMin +
                " Maximum: " + temperatureMax + "\n" +
                "Humidity: " + humidity +
                " Pressure: " + pressure + "\n" +
                "Wind: " + wind;
    }
}
