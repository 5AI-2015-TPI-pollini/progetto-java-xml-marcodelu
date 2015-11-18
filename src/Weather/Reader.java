package Weather;

import Maps.Coordinate;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class Reader {
    private Coordinate CoordinatePlace;
    private State WeatherNow = null;
    private static final String OPENWEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?mode=xml";
    private static final String QUERY_CURRENT_STATE = "/current/city[\"value\"]/text()";
    private static final String APIKEY = "234af59ed708dd86329f3f939e130a80";
    
    public Reader (Coordinate CoordinatePlace){
        this.CoordinatePlace = CoordinatePlace;
        interpreter(getConnection());
    }
    
    private String getValidUrl(){
        return OPENWEATHER_URL + 
                "&lat=" + CoordinatePlace.getLatitude() +
                "&lon=" + CoordinatePlace.getLongitude() +
                "&appid=" + APIKEY;
    }
    
    private InputStream getConnection(){
        try {
            URL URLOpenWeather = new URL(getValidUrl());
            System.out.println(getValidUrl());
            URLConnection URLConnectionOpenWeather = URLOpenWeather.openConnection();
            InputStream result = new BufferedInputStream(URLConnectionOpenWeather.getInputStream());
            return result;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void interpreter(InputStream result){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(result);
            
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            
            XPathExpression tempExp = xpath.compile("/current/temperature/@value");
            XPathExpression minExp = xpath.compile("/current/temperature/@min");
            XPathExpression maxExp = xpath.compile("/current/temperature/@max");
            XPathExpression humidityExp = xpath.compile("/current/humidity/@value");
            XPathExpression pressureExp = xpath.compile("/current/pressure/@value");
            XPathExpression windExp = xpath.compile("/current/wind/speed/@name");
            
            WeatherNow = new State((String)tempExp.evaluate(doc, XPathConstants.STRING),
                (String)minExp.evaluate(doc, XPathConstants.STRING),
                (String)maxExp.evaluate(doc, XPathConstants.STRING),
                (String)humidityExp.evaluate(doc, XPathConstants.STRING),
                (String)pressureExp.evaluate(doc, XPathConstants.STRING),
                (String)windExp.evaluate(doc, XPathConstants.STRING)
                );
            System.out.println(WeatherNow);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
