/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller;

import com.salesoft.util.MyFXMLLoader;
import com.salesoft.util.MyProperties;
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
public class SaleRootLayoutController implements Initializable {
//esas sehifenin unvanini bura yaziriq ki obiri sehifelere burdan kecid ede bilek

    public static ApplicationController appControl;

    @FXML
    private StackPane stackPaneCenter;

    @FXML
    private ToggleButton toggleButtonSale;
    @FXML
    private ToggleButton toggleButtonSaleInvoice;
    @FXML
    private ToggleButton toggleButtonSaleInvoiceDetails;

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
        toggleButtonSale.setSelected(true);
        toggleButtonSale.setToggleGroup(group);
        toggleButtonSaleInvoice.setToggleGroup(group);
        toggleButtonSaleInvoiceDetails.setToggleGroup(group);
    }

    @FXML
    public void toggleButtonSaleOnAction() {
        headLabel.setText("Satish");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MyProperties.getURLProperties().getProductSaleCartURL()));
    }

    @FXML
    public void toggleButtonSaleInvoiceOnAction() {
        headLabel.setText("Satish Arxivi");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MyProperties.getURLProperties().getSaleInvoiceTableURL()));

    }

    @FXML
    public void toggleButtonSaleInvoiceDetailsOnAction() {
        toggleButtonSaleInvoiceDetails.setSelected(true);
        headLabel.setText("Qaime Nomresi ile baxish");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MyProperties.getURLProperties().getSaleInvoiceDetailsTableURL()));

    }

}
