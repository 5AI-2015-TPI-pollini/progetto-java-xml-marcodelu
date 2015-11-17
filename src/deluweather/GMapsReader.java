package deluweather;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
public class GMapsReader {
    Coordinate CoordinateResult;
    
    public GMapsReader(String address){
        interpreter(getConnection(address));
        printCoordinate();
    }
    
    //Get the correct url for Google Maps from the address specified by the user
    private String getValidUrl(String address){
        String finalUrl = "http://maps.googleapis.com/maps/api/geocode/xml?address=";
        //Spaces must be replaced by + for a valid url
        finalUrl+=address.replace(" ", "+");
        System.out.println(finalUrl);
        return finalUrl;
    }
            
    //Set proxy
    private void setProxy(){
        System.out.println("--PROXY--");
    }
    
    //Open connection with Google Maps
    private InputStream getConnection(String address){
        try {
            //BresetProxy();
            URL URLGMaps = new URL(getValidUrl(address));
            URLConnection URLConnectionGMaps = URLGMaps.openConnection();
            InputStream result = new BufferedInputStream(URLConnectionGMaps.getInputStream());
            return result;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GMapsReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GMapsReader.class.getName()).log(Level.SEVERE, null, ex);
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
            XPathExpression statusExpression = xpath.compile("/GeocodeResponse/status/text()");
            String status = (String) statusExpression.evaluate(doc, XPathConstants.STRING);
            if(status.equals("ZERO_RESULTS")){
                System.out.println("Sorry. Nothing found :-(");
                return;
            }
            
            //Getting coordinates
            XPathExpression coordinatesExpression = xpath.compile("/GeocodeResponse/result/geometry/location[1]/lat/text() | "
                    + "/GeocodeResponse/result/geometry/location[1]/lng/text()");
            NodeList coordinatesList = (NodeList) coordinatesExpression.evaluate(doc, XPathConstants.NODESET);
            CoordinateResult = new Coordinate(coordinatesList.item(0).getNodeValue(), coordinatesList.item(1).getNodeValue());
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GMapsReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
                Logger.getLogger(GMapsReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(GMapsReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(GMapsReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private void printCoordinate()
        {
            System.out.println(CoordinateResult);
        }
}
