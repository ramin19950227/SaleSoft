/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view;

import com.salesoft.MainApp;
import com.salesoft.util.MyFXMLLoader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class AnbarRootLayoutController implements Initializable {

    //esas sehifenin unvanini bura yaziriq ki obiri sehifelere burdan kecid ede bilek
    public static ApplicationController appControl;

    @FXML
    private StackPane stackPaneCenter;

    @FXML
    private ToggleButton toggleButtonAnbar;
    @FXML
    private ToggleButton toggleButtonNewProduct;
    @FXML
    private ToggleButton toggleButtonPurchaseInvoiceTable;

    @FXML
    private Label headLabel;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup group = new ToggleGroup();
        toggleButtonAnbar.setSelected(true);
        toggleButtonAnbar.setToggleGroup(group);
        toggleButtonNewProduct.setToggleGroup(group);
        toggleButtonPurchaseInvoiceTable.setToggleGroup(group);

    }

    @FXML
    public void toggleButtonAnbarOnAction() {
        headLabel.setText("Anbar");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MainApp.ALL_PROPERTIES.getURLProperty().getProductTableURL()));
    }

    @FXML
    private void toggleButtonNewProductOnAction() {
        headLabel.setText("Məhsul Alışı");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MainApp.ALL_PROPERTIES.getURLProperty().getProductPurchseURL()));

    }

    public static void setRoot(ApplicationController input) {
        appControl = input;
    }

    @FXML
    public void toggleButtonPurchaseInvoiceOnAction() {
        headLabel.setText("Alış Tarıxçəsi");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MainApp.ALL_PROPERTIES.getURLProperty().getPurchaseInvoiceTableURL()));
    }

}
