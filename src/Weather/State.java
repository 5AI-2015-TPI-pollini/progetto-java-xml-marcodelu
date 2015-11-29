package Weather;

import Maps.Coordinate;

/**
 * Represents weather state
 *
 * @author Marco De Lucchi
 */
public class State {

    /**
     * Coordinate object for linking the state to its correct place
     */
    public Coordinate coordinate;
    private final Double temperature, temperatureMin, temperatureMax;
    private final String humidity, pressure, wind, description;

    /**
     * Inizialize class
     *
     * @param temperature
     * @param temperatureMin
     * @param temperatureMax
     * @param humidity
     * @param pressure
     * @param wind
     * @param description
     */
    public State(String temperature, String temperatureMin, String temperatureMax, String humidity, String pressure, String wind, String description) {
        this.temperature = toCelsius(Double.parseDouble(temperature));
        this.temperatureMin = toCelsius(Double.parseDouble(temperatureMin));
        this.temperatureMax = toCelsius(Double.parseDouble(temperatureMax));
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.description = description;
    }

    private Double toCelsius(Double input) {
        return input - 273.15;
    }

    @Override
    public String toString() {
        return "Temperature: " + temperature.toString().substring(0, 5) + "\n"
                + "Description: " + description + "\n"
                + //"Minimum: " + temperatureMin +
                //" Maximum: " + temperatureMax + "\n" +
                "Humidity: " + humidity + "%"
                + " Pressure: " + pressure + "hPa\n"
                + "Wind: " + wind;
    }

    /**
     * Get temperature
     *
     * @return String
     */
    public String getTemperature() {
        return "" + temperature.shortValue() + " °C";
    }

    /**
     * Get humidity in %
     *
     * @return String
     */
    public String getHumidity() {
        return humidity + " %";
    }

    /**
     * Get pressure in hPa
     *
     * @return String
     */
    public String getPressure() {
        return pressure + " hPa";
    }

    /**
     * Get wind
     *
     * @return String
     */
    public String getWind() {
        return wind;
    }

    /**
     * Get description
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get coordinate
     *
     * @return Coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Set Coordinate
     *
     * @param coordinate Coordinate of the place
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
