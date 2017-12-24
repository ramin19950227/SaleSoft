/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class HomeController implements Initializable {

    /**
     * Butun Mehsullarin Mebleglerinin Cemi += (Mehsul sayi*alish qiymeti);
     */
    @FXML
    private Label totalItemsLabel;

    /**
     * Cemi Mehsul Sayi
     */
    @FXML
    private Label stockValueLabel;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Labelleri sifirlayiriq
        totalItemsLabel.setText("0");
        stockValueLabel.setText("0");
        
        initData();

    }

    /**
     * Melumatlarimizi alir ve yerleshdirir xanalara, Cemi mehsul sayini ve cemi
     * meblegi
     *
     */
    @FXML
    private void initData() {

        // Mehsul sayini al
        Integer allQty = 21; // test ucun
        totalItemsLabel.setText(allQty.toString());

        // Cemi meblegi al
        Double stockValue = 852.55;
        stockValueLabel.setText(stockValue + " AZN");
    }

}
