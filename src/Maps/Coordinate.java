package Maps;

/**
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class Coordinate {
    private Double longitude;
    private Double latitude;
    private String formattedAddress;

    public Coordinate(String latitude, String longitude, String formattedAddress) {
        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
        this.formattedAddress = formattedAddress;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    @Override
    public String toString(){
        return "Coordinate: longitude= " +longitude + ", latitude= "+latitude +"\n" +
                "Name: " + formattedAddress;
    }
    
    
    
}
