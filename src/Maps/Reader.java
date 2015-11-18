package Maps;

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
    Coordinate CoordinateResult;
    private static final String QUERY_STATUS = "/GeocodeResponse/status/text()";
    private static final String QUERY_STATUS_FAIL = "ZERO_RESULT";
    private static final String QUERY_LATITUDE = "/GeocodeResponse/result/geometry/location/lat/text()";
    private static final String QUERY_LONGITUDE = "/GeocodeResponse/result/geometry/location/lng/text()";
    private static final String GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/xml?address=";
    
    public Reader(String address){
        interpreter(getConnection(address));
        printCoordinate();
    }
    
    //Get the correct url for Google Maps from the address specified by the user
    private String getValidUrl(String address){
        //Spaces must be replaced by + for a valid url
        return GEOCODE_URL+address.replace(" ", "+");
    }
            
    //Set proxy
    private void setProxy(){
        System.out.println("--PROXY--");
    }
    
    //Open connection with Google Maps
    private InputStream getConnection(String address){
        try {
            //setProxy();
            URL URLGMaps = new URL(getValidUrl(address));
            URLConnection URLConnectionGMaps = URLGMaps.openConnection();
            InputStream result = new BufferedInputStream(URLConnectionGMaps.getInputStream());
            return result;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;
    }
    
    //Interprets the xml
    private void interpreter(InputStream result){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(result);
            
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            
            //Ckecking if found something
            XPathExpression statusExpression = xpath.compile(QUERY_STATUS);
            String status = (String) statusExpression.evaluate(doc, XPathConstants.STRING);
            if(status.equals(QUERY_STATUS_FAIL)){
                System.out.println("Sorry. Nothing found :-(");
                return;
            }
            
            //Getting coordinates
            XPathExpression lat = xpath.compile(QUERY_LATITUDE);
            XPathExpression lon = xpath.compile(QUERY_LONGITUDE);
            NodeList lats = (NodeList) lat.evaluate(doc, XPathConstants.NODESET);
            NodeList lons = (NodeList) lon.evaluate(doc, XPathConstants.NODESET);
            CoordinateResult = new Coordinate(lats.item(0).getNodeValue(), lons.item(0).getNodeValue()); 
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
    
    private void printCoordinate(){
        System.out.println(CoordinateResult);
    }
    
    public Coordinate getCoordinate(){
        return CoordinateResult;
    }
}
