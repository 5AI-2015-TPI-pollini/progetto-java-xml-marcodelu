package Maps;

/**
 * Class used for storing and managing coordinate
 *
 * @author Marco De Lucchi
 */
public class Coordinate {

    private final Double longitude, latitude;
    private final String formattedAddress;

    /**
     * Create Coordinate object
     *
     * @param latitude latitute of the place
     * @param longitude longitude of the place
     * @param formattedAddress name of the place
     */
    public Coordinate(String latitude, String longitude, String formattedAddress) {
        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
        this.formattedAddress = formattedAddress;
    }

    /**
     *
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return formatted address
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    @Override
    public String toString() {
        return "Coordinate: longitude= " + longitude + ", latitude= " + latitude + "\n"
                + "Name: " + formattedAddress;
    }

}
