package com.salesoft;

import com.salesoft.view.anbar.ProductTableController;
import com.salesoft.view.anbar.ProductPurchseController;
import com.salesoft.DAO.AllPropertiesGetDAO;
import com.salesoft.Properties.AllProperties;
import com.salesoft.util.MyFXMLLoader;
import com.salesoft.util.*;
import com.salesoft.view.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * MainApp - Proqramin Esas main olan Class-i dir butun proqram burdan bashlayir
 *
 * @author Ramin
 */
public class MainApp extends Application {

    //Butun datalarin alinmasi, unvanlarin, metinlerin, tercuelerin ve s.
    public static final AllProperties ALL_PROPERTIES = AllPropertiesGetDAO.getAllProperties();

    private final String loginViewTitle = ALL_PROPERTIES.getUIProperty().getApplicationTitle();

    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * MyLogger Obyektini elan edirik ve yaradiriq, adini XXXXXXX-AppLog.txt
     * qoyuruq static ve final edirik eks teqdirde error cixirdi bilmedim niye
     */
    private static final Logger logger = new MyLogger("AppLog").getLogger();

    /**
     * MyLogger - Proqramimizin esas MyLogger obyektinin linkini almaq ucun
     * istifade olunur bunun sayesinde esas AppLog faylina qeydlerimizi ede
     * bilerik proqram baglanana qeder ne ish gorulecekse qeyd ede bilerk
     *
     * @return
     */
    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(MyFXMLLoader.getSceneFromURL(ALL_PROPERTIES.getURLProperty().getLoginFxmlURL()));

        primaryStage.setTitle(loginViewTitle);

        primaryStage.getIcons().add(new Image("com/salesoft/image/icon.png"));
        primaryStage.setMaximized(false);
        primaryStage.setMinHeight(500.0);
        primaryStage.setMinWidth(850.0);
        primaryStage.show();

        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
