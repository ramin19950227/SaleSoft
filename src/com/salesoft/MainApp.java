package com.salesoft;

import com.salesoft.util.MyFXMLLoader;
import com.salesoft.util.*;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * MainApp - Proqramin Esas main olan Class-i dir butun proqram burdan bashlayir
 *
 * @author Ramin
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Initializator.initFoldersAndFiles();
        Initializator.initMyProperties();

        URL loginViewURL = MyProperties.getURLProperties().getLoginFxmlURL();
        Scene loginViewScene = MyFXMLLoader.getSceneFromURL(loginViewURL);

        primaryStage.setScene(loginViewScene);
        primaryStage.setTitle(" * SaleSoft Alpha versiya 1.21 * ");
        primaryStage.getIcons().add(new Image("com/salesoft/image/icon.png"));
        primaryStage.setMaximized(false);

        // Bu Yontemle Pencerenin olcusunu deyishmeyi qadagn edirik
        primaryStage.resizableProperty().set(false);

        //ekranin olcusunu deyishmeye icaze vermirikse minimum olcu qoymagada ehtiyyac yoxdur
//        primaryStage.setMinHeight(500.0);
//        primaryStage.setMinWidth(850.0);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
