/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.MainApp;
import com.salesoft.database.DBUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 *
 * @author Ramin
 */
public class FirstStartInitialization {

    public FirstStartInitialization() {

        //qovluqlarimizi yoxlayaq her shey yerindedirse
        initFolders();

        //properties fayllarmizi yoxlayaq
        initProperties();

        try {
            //Baza Ile Elaqeni Yoxlayaq
            DBUtil.hasConnetion();
            System.out.println("com.salesoft.util.FirstStartInitialization.<init>()");
            System.out.println("Baza ile Elaqe Quruldu");
        } catch (SQLException ex) {
            RAlert.alertContent(2, "Baza Ile Elaqe Qura Bilmedim");

            Stage nStage = new Stage();
            nStage.setScene(MyFXMLLoader.getSceneFromURL(MainApp.class.getResource("view/Server.fxml")));
            nStage.setMaximized(false);
            nStage.setTitle("Server Configure - Sale Soft");
            nStage.showAndWait();
            

        }

    }

    private static void initFolders() {
        initLogFolders();
        initPropertiesFolders();

    }

    private static void initProperties() {
        initDBProperties();
        initUIProperties();

    }

    private static void initLogFolders() {
        File f = new File("Log\\Exceptions\\");
        if (!f.exists()) {
            System.err.println("Creating Folder for Exception Logs ");
            f.mkdirs();
        }
    }

    private static void initPropertiesFolders() {
        File folder = new File("Properties\\");
        if (!folder.exists()) {
            System.err.println("Creating Folder for Properties");
            folder.mkdirs();
        }
    }

    /**
     * DBProperties.properties - faylimiz eger yoxdursa onu hazirlayir, Bu
     * Proqrami ilk defe ishe salanda olur
     */
    private static void initDBProperties() {
        File propertiesFile = new File("Properties\\DBProperties.properties");
        if (!propertiesFile.exists()) {
            System.err.println("Creating 'DBProperties.properties'  in 'Properties' folder");

            OutputStream output;
            Properties properties = new Properties();

            try {
                output = new FileOutputStream("Properties/DBProperties.properties");
                properties.setProperty("db.host", "localhost");
                properties.setProperty("db.port", "3306");
                properties.setProperty("db.name", "testdb");
                properties.setProperty("db.user", "root");
                properties.setProperty("db.crypted.password", "password");
                properties.store(output, null);
                output.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("");
                RAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initDBProperties() - FileNotFoundException");
            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                RAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initDBProperties() - IOException");

            }

        }

    }

    /**
     * UIProperties init
     */
    private static void initUIProperties() {

        File propertiesFile = new File("Properties\\UIProperties.properties");
        if (!propertiesFile.exists()) {
            System.err.println("Creating 'UIProperties.properties'  in 'Properties' folder");

            OutputStream output;
            Properties properties = new Properties();

            try {
                output = new FileOutputStream("Properties/UIProperties.properties");
                properties.setProperty("application.Title", "SaleSoft - a Xos G\u0259lmisiniz - Login Bolumu");
                properties.store(output, null);
                output.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("");
                RAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initUIProperties() - FileNotFoundException");
            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                RAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initUIProperties() - IOException");

            }

        }

    }
}
