package Http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */
public class Connection {
    private String url;
    private InputStream output;
    
    
    public Connection(String url){
        this.url = url;
        connect();
    }
    
    private void connect(){
        try {
            URL URLInput = new URL(url);
            URLConnection ConnectionInput = URLInput.openConnection();
            output = new BufferedInputStream(ConnectionInput.getInputStream());
        } catch (MalformedURLException ex) {
            output = null;
        } catch (IOException ex) {
            output = null;
        }
    }
    
    public InputStream getConnection(){
        return output;
    }

}
