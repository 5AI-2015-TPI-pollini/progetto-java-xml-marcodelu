package Weather;

import Maps.Coordinate;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import Http.Connection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.System.exit;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * Handle Open Weather request
 *
 * @author Marco De Lucchi
 */
public class Reader {

    private final Coordinate CoordinatePlace;
    private State WeatherNow = null;
    private static final String OPENWEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?mode=xml";
    private String apikey = "";
    private Connection OpenWeather;

    /**
     * Retrieve weather from Open Weather
     *
     * @param CoordinatePlace Coordinate of the place where you want to check
     * weather
     */
    public Reader(Coordinate CoordinatePlace) {
        this.CoordinatePlace = CoordinatePlace;
        readSettings();
        interpreter();
    }

    /**
     * Get the correct url for Open Weather from the coordinate object
     *
     * @return String correct address
     */
    private String getValidUrl() {
        return OPENWEATHER_URL
                + "&lat=" + CoordinatePlace.getLatitude()
                + "&lon=" + CoordinatePlace.getLongitude()
                + "&appid=" + apikey;
    }

    /**
     * Convert XML data for final user
     */
    private void interpreter() {
        try {
            OpenWeather = new Connection(getValidUrl());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(OpenWeather.getConnection());

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            XPathExpression tempExp = xpath.compile("/current/temperature/@value");
            XPathExpression minExp = xpath.compile("/current/temperature/@min");
            XPathExpression maxExp = xpath.compile("/current/temperature/@max");
            XPathExpression humidityExp = xpath.compile("/current/humidity/@value");
            XPathExpression pressureExp = xpath.compile("/current/pressure/@value");
            XPathExpression windExp = xpath.compile("/current/wind/speed/@name");
            XPathExpression weatherExp = xpath.compile("/current/weather/@value");

            WeatherNow = new State((String) tempExp.evaluate(doc, XPathConstants.STRING),
                    (String) minExp.evaluate(doc, XPathConstants.STRING),
                    (String) maxExp.evaluate(doc, XPathConstants.STRING),
                    (String) humidityExp.evaluate(doc, XPathConstants.STRING),
                    (String) pressureExp.evaluate(doc, XPathConstants.STRING),
                    (String) windExp.evaluate(doc, XPathConstants.STRING),
                    (String) weatherExp.evaluate(doc, XPathConstants.STRING)
            );
            WeatherNow.setCoordinate(CoordinatePlace);
            System.out.println(WeatherNow);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            System.out.println("There was a problem retrieving data from Open Weather. Sorry!");
            System.out.println("Here some data for nerds:");
            Logger.getLogger(Weather.Reader.class.getName()).log(Level.SEVERE, null, ex);
            exit(2);
        }
    }

    /**
     * Read api key from configuration file
     */
    private void readSettings() {
        try (BufferedReader br = new BufferedReader(new FileReader("weather.config"))) {
            apikey = br.readLine();
        } catch (FileNotFoundException ex) {
            configurationFileMissing();
        } catch (IOException ex) {
            exit(4);
        }
    }

    /**
     * Handle the exception about missing configuration file
     */
    private void configurationFileMissing() {
        TextInputDialog dialog = new TextInputDialog("Open Weather API");
        dialog.setTitle("Open Weather API");
        dialog.setHeaderText("Can't find weather configuration. \n"
                + "I need the Api Key for retrieving data from OpenWeather.\n"
                + "Please check DeluWeather's documentation and provide a valid key.");
        dialog.setContentText("Api Key:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            apikey = result.get();
        }
        writeSettings();
    }

    /**
     * Write api key
     */
    private void writeSettings() {
        try (PrintWriter pw = new PrintWriter("weather.config", "UTF-8")) {
            pw.print(apikey);
            pw.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            exit(5);
        }
    }

    /**
     * Get the state object representing weather
     *
     * @return State actual weather
     */
    public State getState() {
        return WeatherNow;
    }
}
