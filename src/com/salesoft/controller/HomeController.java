/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.model.Product;
import java.net.URL;
import java.util.ArrayList;
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
     * Mehsullarin Cemi Sayi
     */
    @FXML
    private Label totalQtyLabel;

    /**
     * Mehsullarin Novlerinin Sayı
     */
    @FXML
    private Label totalTypeQtyLabel;

    /**
     *
     * Mehsullarin Mebleglerinin Cemi;
     */
    @FXML
    private Label totalPriceLabel;

    /**
     * DAO Obyektimiz
     */
    ProductDAO productDAO;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // DAO init
        productDAO = new ProductDAO();

        //Labelleri sifirlayiriq
        totalQtyLabel.setText("0");
        totalTypeQtyLabel.setText("0");
        totalPriceLabel.setText("0.0");

        initData();

    }

    /**
     * Melumatlarimizi alir ve yerleshdirir xanalara, Cemi mehsul sayini,
     * mehsullarin novu ve cemi meblegi
     *
     */
    @FXML
    private void initData() {

        /**
         * Məhsulların Cəmi Sayı
         */
        Integer totalQty = 0;

        /**
         * Məhsulların Növ Sayı, Nece no mehsul var onu gosterir , MESELEN 2
         * Rucka var 3 ed defter demeli Cemi 2 nov mehsulumuz var amma mehsul
         * sayi cemi 5-dir
         */
        Integer totalTypeQty = 0;

        /**
         * Məhsulların Cəmi Məbləği
         */
        Double totalPrice = 0.0;

        // mehsullarimizin List-i
        ArrayList<Product> list = productDAO.getAll();

        //Melumatlarimizi ALAQ
        for (Product product : list) {
            totalPrice += product.getPurchasePrice() * product.getQty();
            totalQty += product.getQty();
        }

        // Mehsullarimizin novunun sayini alaq
        totalTypeQty = list.size();

        // Melumatlarimizi yaziriq 
        totalQtyLabel.setText(totalQty.toString() + " Ədəd");

        totalTypeQtyLabel.setText(totalTypeQty.toString() + " Növ");

        totalPriceLabel.setText(totalPrice + " AZN");
    }

}
