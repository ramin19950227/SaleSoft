package com.salesoft;

import com.salesoft.DAO.AllPropertiesGetDAO;
import com.salesoft.Properties.AllProperties;
import com.salesoft.util.MyFXMLLoader;
import com.salesoft.util.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * MainApp - Proqramin Esas main olan Class-i dir butun proqram burdan bashlayir
 *
 * @author Ramin
 */
public class MainApp extends Application {

    public MainApp() {

        // ilk Hazirliqlarimizi Inicializasiyamizi edek
        // qovluqlarimizi yoxlayaq her shey yolundadirmi deye
        Initializator.initMyProperties();

        Initializator.initFoldersAndFiles();

        Initializator.initDataBase();

    }

    //Consolumuzu Fayla yazmaq ucun bu obyektden istifade edeceyik
    //PrintStream out = RLogger.logConsoleToFile();
    //Butun datalarin alinmasi, unvanlarin, metinlerin, tercuelerin ve s.
    public static AllProperties ALL_PROPERTIES;

    private static Stage primaryStage;

    //bunu static eledimki obiri classlardan bunu ala bilim
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        ALL_PROPERTIES = AllPropertiesGetDAO.getAllProperties();

        String loginViewTitle = ALL_PROPERTIES.getUIProperty().getApplicationTitle();

        primaryStage.setScene(MyFXMLLoader.getSceneFromURL(ALL_PROPERTIES.getURLProperty().getLoginFxmlURL()));

        primaryStage.setTitle(loginViewTitle);

        primaryStage.getIcons().add(new Image("com/salesoft/image/icon.png"));
        primaryStage.setMaximized(false);
        primaryStage.setMinHeight(500.0);
        primaryStage.setMinWidth(850.0);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
