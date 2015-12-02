package Maps;

import java.io.IOException;
import static java.lang.System.exit;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Handle Google Maps request
 *
 * @author Marco De Lucchi
 */
public class Reader {

    Coordinate CoordinateResult;
    private static final String QUERY_STATUS = "/GeocodeResponse/status/text()";
    private static final String QUERY_STATUS_FAIL = "ZERO_RESULTS";
    private static final String QUERY_LATITUDE = "/GeocodeResponse/result/geometry/location/lat/text()";
    private static final String QUERY_LONGITUDE = "/GeocodeResponse/result/geometry/location/lng/text()";
    private static final String GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/xml?address=";
    private static final String QUERY_ADDRESS = "/GeocodeResponse/result/formatted_address/text()";
    private final String address;
    private Connection GMaps;

    /**
     * Starts the address retrieving
     *
     * @param address place where you want to check weather
     * @throws NotFoundException if the address isn't valid is throwed and
     * handled asking another user's input
     */
    public Reader(String address) throws NotFoundException {
        this.address = address;
        interpreter();
        printCoordinate();
    }

    /**
     * Get the correct url for Google Maps from the address specified by the
     * user
     *
     * @return String correct address
     */
    private String getValidUrl() {
        //Spaces must be replaced by + for a valid url
        return GEOCODE_URL + address.replace(" ", "+");
    }

    /**
     * Convert XML data into coordinate
     *
     * @throws NotFoundException throwed if the address isn't found on database
     */
    private void interpreter() throws NotFoundException {
        try {
            GMaps = new Connection(getValidUrl());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(GMaps.getConnection());

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            /**
             * Checking if found results
             */
            XPathExpression statusExpression = xpath.compile(QUERY_STATUS);
            String status = (String) statusExpression.evaluate(doc, XPathConstants.STRING);
            if (status.equals(QUERY_STATUS_FAIL)) {
                throw new NotFoundException(address);
            }

            /**
             * Getting coordinates
             */
            XPathExpression lat = xpath.compile(QUERY_LATITUDE);
            XPathExpression lon = xpath.compile(QUERY_LONGITUDE);
            XPathExpression fa = xpath.compile(QUERY_ADDRESS);
            String lats = (String) lat.evaluate(doc, XPathConstants.STRING);
            String lons = (String) lon.evaluate(doc, XPathConstants.STRING);
            String fas = (String) fa.evaluate(doc, XPathConstants.STRING);
            CoordinateResult = new Coordinate(lats, lons, fas);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            System.out.println("There was a problem retrieving data from Google Maps. Sorry!");
            System.out.println("Here some data for nerds:");
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            exit(2);
        }
    }

    private void printCoordinate() {
        System.out.println(CoordinateResult);
    }

    /**
     * Get the place's coordinate
     *
     * @return Coordinate
     */
    public Coordinate getCoordinate() {
        return CoordinateResult;
    }
}
