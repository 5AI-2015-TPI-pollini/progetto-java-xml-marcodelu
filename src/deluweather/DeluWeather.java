package deluweather;

import GoogleMaps.Reader;
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
            System.out.println("Benvenuto! Inserisci l'indirizzo:");
            //Leggo input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String address = br.readLine();
            Reader GMR = new Reader(address);
        } catch (IOException ex) {
            Logger.getLogger(DeluWeather.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
