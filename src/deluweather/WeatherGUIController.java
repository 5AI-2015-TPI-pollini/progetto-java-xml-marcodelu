/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deluweather;

import Maps.NotFoundException;
import Weather.State;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class WeatherGUIController implements Initializable {

    Maps.Reader GMR;
    Weather.Reader OPW;
    
    @FXML
    private Text textTemperature, textDescription, textHumidity, textPressure, textWind, textPlace;
    @FXML
    private TextField textInput;
    @FXML
    private SplitPane mainPanel;
    
    private final static float dividerPosition = (float) 0.25;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainPanel.setDividerPosition(0, 1);
    }
    
    @FXML
    public void go(){
        try {
            mainPanel.setDividerPosition(0, dividerPosition);
            String address = textInput.getText();
            GMR = new Maps.Reader(address);
            OPW = new Weather.Reader(GMR.getCoordinate());
            printWeather();
        } catch (NotFoundException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid place");
            alert.setContentText(ex.toString() + "\n" + "Check and try again.");
            alert.showAndWait();
            mainPanel.setDividerPosition(0, 1);
        }
    }
    
    private void printWeather(){
        State weather = OPW.getState();
        textPlace.setText(weather.coordinate.getFormattedAddress());
        textTemperature.setText(weather.getTemperature());
        textDescription.setText(weather.getDescription());
        textHumidity.setText(weather.getHumidity());
        textPressure.setText(weather.getPressure());
        textWind.setText(weather.getWind());
    }
    
}
