package deluweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class DeluWeather {
    public static void main(String[] args) {
        try {
            System.out.println("Welcome! Please write the address:");
            //Read input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String address = br.readLine();
            
            //Create Object
            Maps.Reader GMR = new Maps.Reader(address);
            Weather.Reader OPW = new Weather.Reader(GMR.getCoordinate());
        } catch (IOException ex) {
            System.out.println("Something went wrong :-(");
            System.out.println("Here some data for nerds:");
            Logger.getLogger(DeluWeather.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
