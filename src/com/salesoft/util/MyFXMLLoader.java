/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Ramin
 */
public class MyFXMLLoader {

    /**
     * URL address alir ve hazir Scene obyekti qaytarir
     *
     * @param url
     * @return
     */
    public static Scene getSceneFromURL(URL url) {
        Scene scene = new Scene(new AnchorPane());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            AnchorPane root = loader.load();
            scene = new Scene(root);
        } catch (IOException ex) {
            Logger.getLogger(MyFXMLLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

    /**
     * URL address alir ve hazir AnchorPane obyekti qaytarir
     *
     * @param url
     * @return
     */
    public static AnchorPane getAnchorPaneFromURL(URL url) {
        AnchorPane anchorPane = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            anchorPane = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MyFXMLLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return anchorPane;
    }

    /**
     * URL address alir ve hazir Parent obyekti qaytarir
     *
     * @param url
     * @return
     */
    public static Parent getParentFromURL(URL url) {
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            parent = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MyFXMLLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parent;
    }

}
