package deluweather;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        }
    }

    /**
     * Method for starting GUI
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("WeatherGUI.fxml"));
        primaryStage.setScene(new Scene(root, Color.TRANSPARENT));
        primaryStage.show();
    }
}
