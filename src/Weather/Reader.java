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
import static java.lang.System.exit;
/**
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class Reader {
    private Coordinate CoordinatePlace;
    private State WeatherNow = null;
    private static final String OPENWEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?mode=xml";
    private String apikey = "";
    
    public Reader (Coordinate CoordinatePlace){
        this.CoordinatePlace = CoordinatePlace;
        readSettings();
        interpreter();
    }
    
    private String getValidUrl(){
        return OPENWEATHER_URL + 
                "&lat=" + CoordinatePlace.getLatitude() +
                "&lon=" + CoordinatePlace.getLongitude() +
                "&appid=" + apikey;
    }
    
    private void interpreter(){
        try {
            Connection OpenWeather = new Connection (getValidUrl());
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
            
            WeatherNow = new State((String)tempExp.evaluate(doc, XPathConstants.STRING),
                (String)minExp.evaluate(doc, XPathConstants.STRING),
                (String)maxExp.evaluate(doc, XPathConstants.STRING),
                (String)humidityExp.evaluate(doc, XPathConstants.STRING),
                (String)pressureExp.evaluate(doc, XPathConstants.STRING),
                (String)windExp.evaluate(doc, XPathConstants.STRING),
                (String)weatherExp.evaluate(doc, XPathConstants.STRING)
                );
            System.out.println(WeatherNow);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            System.out.println("There was a problem retrieving data from Open Weather. Sorry!");
            System.out.println("Here some data for nerds:");
            Logger.getLogger(Weather.Reader.class.getName()).log(Level.SEVERE, null, ex);
            exit(2);
        }
    }
    
    private void readSettings(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("weatherconfig.txt"));
            apikey = br.readLine();
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Weather configuration file is missing!"+"\n"+"Can't check weather conditions.");
            exit(2);
        } catch (IOException ex) {
           System.out.println("There was a problem retrieving data from Open Weather. Sorry!");
           System.out.println("Here some data for nerds:");
           Logger.getLogger(Weather.Reader.class.getName()).log(Level.SEVERE, null, ex);
           exit(2);
        }
    }
}