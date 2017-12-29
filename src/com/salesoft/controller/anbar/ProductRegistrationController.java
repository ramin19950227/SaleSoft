/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.controller.ApplicationController;
import com.salesoft.model.Product;
import com.salesoft.util.MyView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class ProductRegistrationController implements Initializable {

    @FXML
    private ImageView bIV, nIV;

    @FXML
    private TextField bTF, nTF;
    private String lastCorrectBarCode;

    @FXML
    private Label bWL, nWL;

    @FXML
    private Button saveButton, clearButton;

    private final ProductDAO productDAO = new ProductDAO();
    MyView myView = new MyView();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        saveButton.setOnAction(value -> {
            okButtonOnAction();
        });

        clearButton.setOnAction(value -> {
            clearButtonOnAction();
        });

        saveButton.setDisable(true);

        bTF.setOnKeyReleased(value -> {
            barCodeFieldOnKeyReleased();
        });

        bTF.setPromptText("BarCodu Daxil Edin");
        nTF.setPromptText("Mehsulun Adını Daxil Edin");

        nTF.setOnKeyReleased(value -> {
            nameFieldOnKeyReleased();
        });

        // her ehtimala qarshi init edirem ki birden NullPoiter Cixar))
        lastCorrectBarCode = "";

        bWL.setText(null);
        nWL.setText(null);
    }

    private void barCodeFieldOnKeyReleased() {

        if (isBarcodeInputValid()) {
            // eger Yoxlamadan kecdise Yadda saxlayaq
            lastCorrectBarCode = bTF.getText();

            if (productDAO.getByBarcode(bTF.getText()) == null) {

                myView.showOk(bIV);
                checkButtonEnableStatus();

            } else {
                checkButtonEnableStatus();

                myView.showNo(bIV);
                bWL.setText("Məhsul artıq Qeytiyyatdan Keçib");
            }

        } else {
            checkButtonEnableStatus();

            myView.showNo(bIV);

            // eger mushteririn daxil elediyi yazi Yoxlamani kecmedise onu evvelki dogru ile evez edek
            bTF.setText(lastCorrectBarCode);
        }

    }

    private void nameFieldOnKeyReleased() {

        if (isNameInputValid()) {
            myView.showOk(nIV);

            checkButtonEnableStatus();

        } else {
            myView.showNo(nIV);
            checkButtonEnableStatus();
        }

    }

    private boolean isBarcodeInputValid() {

        if (bTF.getText() == null || bTF.getText().length() == 0) {
            bWL.setText("BarCod Daxil Edin!");

            lastCorrectBarCode = "";
            return false;
        } else {
            bWL.setText(null);
            // пытаемся преобразовать в int.
            try {
                Integer.parseInt(bTF.getText());
            } catch (NumberFormatException e) {
                bWL.setText("Kecərli BarCod daxil edin (Tam eded olmalidir)!");

                return false;
            }
        }

        return true;
    }

    private boolean isNameInputValid() {

        if (nTF.getText() == null || nTF.getText().length() == 0) {
            nWL.setText("Məhsulun adını daxil edin!");
            return false;
        } else {
            nWL.setText(null);
        }

        return true;
    }

    private void checkButtonEnableStatus() {

        if (isBarcodeInputValid() && isNameInputValid() && productDAO.getByBarcode(bTF.getText()) == null) {
            saveButton.setDisable(false);

        } else {
            saveButton.setDisable(true);
        }

    }

    private void okButtonOnAction() {
        Product product = new Product();

        product.setBarCode(bTF.getText());
        product.setName(nTF.getText());

        productDAO.create(product);

        //Anbari Gosterek
        ApplicationController.getApplicationController().btnStockOnClick();

    }

    private void clearButtonOnAction() {

        bTF.setText(null);
        nTF.setText(null);

        bIV.setImage(null);
        nIV.setImage(null);

        nWL.setText(null);
        bWL.setText(null);

        bTF.requestFocus();

    }

}
