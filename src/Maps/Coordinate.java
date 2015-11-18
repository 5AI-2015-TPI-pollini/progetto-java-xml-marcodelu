package Maps;

/**
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class Coordinate {
    private Double longitude;
    private Double latitude;

    public Coordinate(String latitude, String longitude) {
        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString(){
        return "Coordinate: longitude= " +longitude + ", latitude: "+latitude;
    }
    
    
    
}
