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
public class AnbarRootLayoutController implements Initializable {

    @FXML
    private StackPane stackPaneCenter;

    /**
     * Anbar Sehifesini gostermek ucun Istifade olunur
     */
    @FXML
    private ToggleButton toggleButtonAnbar;

    /**
     * Mehsul Qeydiyyati Sehifesin Yuklemek ucun Sitifade olunur
     */
    @FXML
    private ToggleButton toggleButtonProductRegistrarion;

    /**
     * Mehsul Mədaxil Sehifesini yukləyir
     */
    @FXML
    private ToggleButton toggleButtonProductMedaxil;

    /**
     * Mehsul Mədaxil Tarixcesinin Sehifesini yukləyir
     */
    @FXML
    private ToggleButton toggleButtonProductHistory;

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
        toggleButtonAnbar.setOnAction(value -> {
            toggleButtonAnbarOnAction();
        });

        toggleButtonProductRegistrarion.setToggleGroup(group);
        toggleButtonProductRegistrarion.setOnAction(value -> {
            toggleButtonProductRegistrarionOnAction();
        });

        toggleButtonProductMedaxil.setToggleGroup(group);
        toggleButtonProductMedaxil.setOnAction(value -> {
            toggleButtonProductMedaxilOnAction();
        });

        toggleButtonProductHistory.setToggleGroup(group);
        toggleButtonProductHistory.setOnAction(value -> {
            toggleButtonProductHistoryOnAction();
        });

    }

    @FXML
    public void toggleButtonAnbarOnAction() {
        headLabel.setText("Anbar");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MyProperties.getURLProperties().getProductTableURL()));
    }

    @FXML
    private void toggleButtonProductRegistrarionOnAction() {
        headLabel.setText("Qeydiyyat");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MyProperties.getURLProperties().getProductRegistrarionURL()));

    }

    @FXML
    public void toggleButtonProductMedaxilOnAction() {
        headLabel.setText("Mədaxil");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MyProperties.getURLProperties().getProductPurchseURL()));
    }

    @FXML
    public void toggleButtonProductHistoryOnAction() {
        headLabel.setText("Mədaxil Tarıxçəsi");

        stackPaneCenter.getChildren().clear();
        stackPaneCenter.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MyProperties.getURLProperties().getPurchaseInvoiceTableURL()));
    }

}
