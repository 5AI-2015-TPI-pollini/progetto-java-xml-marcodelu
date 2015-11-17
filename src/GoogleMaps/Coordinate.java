package GoogleMaps;

/**
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class Coordinate {
    private String longitude;
    private String latitude;

    public Coordinate(String latitude, String longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return "latitute: " + latitude + "\n" + "longitude: " + longitude ;
    }
    
}
