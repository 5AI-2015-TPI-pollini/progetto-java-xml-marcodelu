package Http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.System.exit;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 ** Create and manage the connection to a website
 *
 * @author Marco De Lucchi
 */
public class Connection {

    private final String url;
    private InputStream output;
    private String proxyAddress, proxyPort, authUser, authPassword;
    private Boolean proxyOn;
    private Alert alert;

    /**
     * Create connection to a website
     *
     * @param url website url to connect
     */
    public Connection(String url) {
        this.url = url;
        proxyOn = false;

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Connection in progress");
        alert.setHeaderText("Please wait...");
        alert.setContentText("Looking for the address...");
        alert.show();

        connect();

        alert.close();
    }

    private void connect() {
        try {
            URL URLInput = new URL(url);
            URLConnection ConnectionInput = URLInput.openConnection();
            output = new BufferedInputStream(ConnectionInput.getInputStream());
        } catch (MalformedURLException ex) {
            cantconnect();
        } catch (IOException ex) {
            cantconnect();
        }
    }

    /**
     * Get the stream of the website
     *
     * @return InpuStream
     */
    public InputStream getConnection() {
        return output;
    }

    /**
     * Analyze why software can't reach Internet
     */
    private void cantconnect() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Can't connect to Internet");
        alert.setHeaderText("I wasn't able to connect to Internet.");
        alert.setContentText("Maybe are you under a proxy connection?");

        ButtonType buttonTryAgain = new ButtonType("Try again");
        ButtonType buttonSetProxy = new ButtonType("Set proxy");
        ButtonType buttonTypeCancel = new ButtonType("Quit", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTryAgain, buttonSetProxy, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTryAgain) {
            connect();
        } else if (result.get() == buttonSetProxy) {
            proxyOn = true;
            setProxy();
            connect();
        } else {
            exit(2);
        }
    }

    /**
     * Set a proxy for the connection
     */
    private void setProxy() {
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

    /**
     * If configuration file is missing, will ask user for insert configuration
     */
    private void missingConfigurationProxy() {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please insert proxy configuration");

        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField address = new TextField();
        address.setPromptText("192.168.0.1");
        TextField port = new TextField();
        port.setPromptText("8080");
        TextField username = new TextField();
        username.setPromptText("username");
        PasswordField password = new PasswordField();
        password.setPromptText("password");

        grid.add(new Label("Address:"), 0, 0);
        grid.add(address, 1, 0);
        grid.add(new Label("Port:"), 0, 1);
        grid.add(port, 1, 1);
        grid.add(new Label("Username:"), 0, 2);
        grid.add(username, 1, 2);
        grid.add(new Label("Password:"), 0, 3);
        grid.add(password, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == loginButtonType) {
                return new String[]{address.getText(), port.getText(), username.getText(), password.getText()};
            }
            if (dialogButton == ButtonType.CANCEL) {
                exit(2);
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();
        result.ifPresent(configuration -> {
            proxyAddress = configuration[0];
            proxyPort = configuration[1];
            authUser = configuration[2];
            authPassword = configuration[3];
        });
        writeSettings();
    }

    /**
     * Read proxy configuration file
     */
    private void readSettings() {
        try (BufferedReader br = new BufferedReader(new FileReader("proxy.config"))) {
            proxyAddress = br.readLine();
            proxyPort = br.readLine();
            authUser = br.readLine();
            authPassword = br.readLine();
        } catch (FileNotFoundException ex) {
            missingConfigurationProxy();
        } catch (IOException ex) {
            cantconnect();
        }
    }

    /**
     * Write proxy configuration file
     */
    private void writeSettings() {
        try (PrintWriter pw = new PrintWriter("proxy.config", "UTF-8")) {
            pw.println(proxyAddress);
            pw.println(proxyPort);
            pw.println(authUser);
            pw.println(authPassword);
            pw.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            exit(3);
        }
    }
}
