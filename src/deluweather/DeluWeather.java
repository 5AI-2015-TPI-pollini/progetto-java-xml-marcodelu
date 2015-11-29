package deluweather;

import Maps.NotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main Class
 *
 * @author Marco De Lucchi
 */
public class DeluWeather extends Application {

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception ex) {
            System.out.println("Something went wrong :-(");
            System.out.println("Can't start graphic.");
            Logger.getLogger(DeluWeather.class.getName()).log(Level.SEVERE, null, ex);
            noGraphic();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("WeatherGUI.fxml"));
        Scene scene = new Scene(root, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void noGraphic() {
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
        } catch (NotFoundException ex) {
            System.out.println("Sorry, nothing found. Please check the address and try again.");
            noGraphic();
        }
    }
}
