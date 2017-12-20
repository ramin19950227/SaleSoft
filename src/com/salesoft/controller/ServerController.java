/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller;

import com.salesoft.MainApp;
import com.salesoft.util.MyAlert;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author rifat
 */
public class ServerController implements Initializable {

    @FXML
    private TextField tfHost;
    @FXML
    private TextField thPort;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnConnect;
    @FXML
    private Button btnReset;
    @FXML
    private Label lablServerStatus;
    @FXML
    private TextField tfDBName;
    @FXML
    private TextField tfUserName;

    Properties properties = new Properties();
    InputStream inputStream;
    OutputStream output = null;

    Connection con;

    String url;
    String user;
    String pass;
    String unicode = "?useUnicode=yes&characterEncoding=UTF-8";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkSQLStatus();
        getDataFromFile();

    }

    @FXML
    private void btnConnectOnAction(ActionEvent event) {
        mkDbProperties();

    }

    @FXML
    private void btnResetOnAction(ActionEvent event) {
    }

    public void getDataFromFile() {
        try {
            inputStream = new FileInputStream("Properties/DBProperties.properties");

            properties.load(inputStream);
            tfHost.setText(properties.getProperty("db.host"));
            tfDBName.setText(properties.getProperty("db.name"));
            tfUserName.setText(properties.getProperty("db.user"));
            pfPassword.setText(properties.getProperty("db.crypted.password"));
            thPort.setText(properties.getProperty("db.port"));
            inputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mkDbProperties() {
        try {
            output = new FileOutputStream("Properties/DBProperties.properties");

            properties.setProperty("db.host", tfHost.getText().trim());
            properties.setProperty("db.port", thPort.getText().trim());
            properties.setProperty("db.name", tfDBName.getText().trim());
            properties.setProperty("db.user", tfUserName.getText().trim());
            properties.setProperty("db.crypted.password", pfPassword.getText().trim());
            properties.store(output, null);
            output.close();
            if (dbConnect()) {
                con.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Server connect successfully");
                alert.setHeaderText("Login now");
                alert.setContentText("Server has been connected sucessfully \n to login now click ok");
                alert.initStyle(StageStyle.UNDECORATED);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage = (Stage) thPort.getScene().getWindow();
                    stage.close();
                }
            } else {
                Alert error_alert = new Alert(Alert.AlertType.ERROR);
                error_alert.setTitle("Can't connect with mysql");
                error_alert.setHeaderText("Can't connect to mysql server");
                error_alert.setContentText("Try again");
                error_alert.initStyle(StageStyle.UNDECORATED);
                error_alert.show();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkSQLStatus() {
        try {
            inputStream = new FileInputStream("Properties/DBProperties.properties");
            String host = properties.getProperty("db.host");
            int port = 3306;
            Socket socket = new Socket(host, port);
            lablServerStatus.setText("Server is running");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            lablServerStatus.setText("Server is NOT running");
            MyAlert.alertContent(Integer.SIZE, "SERVER Sonuludur");
        }
    }

    public void loadPropertiesFile() {
        try {
            inputStream = new FileInputStream("Properties/DBProperties.properties");
            properties.load(inputStream);
            url = "jdbc:mysql://" + properties.getProperty("db.host") + ":" + properties.getProperty("db.port") + "/";
            user = properties.getProperty("db.user");
            pass = properties.getProperty("db.crypted.password");
        } catch (IOException e) {
            System.out.println("DDDD");
        }
    }

    private boolean dbConnect() {
        loadPropertiesFile();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url + unicode, user, pass);
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Too Many Connection");
        }
        return false;
    }

}
