package Maps;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
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
import Http.Connection;

/**
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class Reader {
    Coordinate CoordinateResult;
    private static final String QUERY_STATUS = "/GeocodeResponse/status/text()";
    private static final String QUERY_STATUS_FAIL = "ZERO_RESULTS";
    private static final String QUERY_LATITUDE = "/GeocodeResponse/result/geometry/location/lat/text()";
    private static final String QUERY_LONGITUDE = "/GeocodeResponse/result/geometry/location/lng/text()";
    private static final String GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/xml?address=";
    private String address;
    
    public Reader(String address){
        this.address=address;
        interpreter();
        printCoordinate();
    }
    
    //Get the correct url for Google Maps from the address specified by the user
    private String getValidUrl(){
        //Spaces must be replaced by + for a valid url
        return GEOCODE_URL+address.replace(" ", "+");
    }
        
    //Interprets the xml
    private void interpreter(){
        try {
            Connection GMaps = new Connection(getValidUrl());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(GMaps.getConnection());
            
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            
            //Ckecking if found something
            XPathExpression statusExpression = xpath.compile(QUERY_STATUS);
            String status = (String) statusExpression.evaluate(doc, XPathConstants.STRING);
            if(status.equals(QUERY_STATUS_FAIL)){
                System.out.println("Sorry, nothing found. Please check the address and try again.");
                exit(1);
            }
            
            //Getting coordinates
            XPathExpression lat = xpath.compile(QUERY_LATITUDE);
            XPathExpression lon = xpath.compile(QUERY_LONGITUDE);
            NodeList lats = (NodeList) lat.evaluate(doc, XPathConstants.NODESET);
            NodeList lons = (NodeList) lon.evaluate(doc, XPathConstants.NODESET);
            CoordinateResult = new Coordinate(lats.item(0).getNodeValue(), lons.item(0).getNodeValue()); 
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            System.out.println("There was a problem retrieving data from Google Maps. Sorry!");
            System.out.println("Here some data for nerds:");
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            exit(2);
        }
    }
    
    private void printCoordinate(){
        System.out.println(CoordinateResult);
    }
    
    public Coordinate getCoordinate(){
        return CoordinateResult;
    }
}
