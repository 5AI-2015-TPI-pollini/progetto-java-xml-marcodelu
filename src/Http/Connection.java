package Http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
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
    private String proxyAddress, proxyPort, authUser, authPassword;
    private Boolean proxyOn;
    
    
    public Connection(String url){
        this.url = url;
        proxyOn=false;
        connect();
    }
    
    private void connect(){
        try {
            URL URLInput = new URL(url);
            URLConnection ConnectionInput = URLInput.openConnection();
            output = new BufferedInputStream(ConnectionInput.getInputStream());
            return;
        } catch (MalformedURLException ex) {
            System.out.println("There was a problem creating the connection. Sorry.");
            output = null;
            exit(1);
        } catch (IOException ex) {
            if(!proxyOn){
                System.out.println("Can't connect. I will try to set up proxy.");
                proxyOn=true;
                setProxy();
                connect();
            }
            else {
                System.out.println("I can't connect to internet. Check your connection and try again.");
                exit(1);
            }
        }
    }
    
    public InputStream getConnection(){
        return output;
    }
    
    private void setProxy(){
        readSettings();
        Authenticator.setDefault(
           new Authenticator() {
              @Override
              public PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(
                       authUser, authPassword.toCharArray());
              }
           }
        );
        System.setProperty("http.proxyUser", authUser);
        System.setProperty("http.proxyPassword", authPassword);
        System.setProperty("http.proxyHost", proxyAddress);
        System.setProperty("http.proxyPort", proxyPort);
    }
    
    private void readSettings(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("proxyconfig.txt"));
            proxyAddress = br.readLine();
            proxyPort = br.readLine();
            authUser = br.readLine();
            authPassword = br.readLine();
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Proxy configuration file is missing!"+"\n"+"Can't connect to Internet.");
            exit(1);
        } catch (IOException ex) {
            System.out.println("I can't connect to internet. Check your connection and try again.");
        }
    }
}
