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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class ProductRegistrationController implements Initializable {

    @FXML
    private ImageView imageViewBarCode;

    @FXML
    private ImageView imageViewName;

    @FXML
    private TextField barCodeField;
    private String barCodeFieldLastCorrectValue;

    @FXML
    private Label barCodeFieldErrorMessageLabel;

    @FXML
    private TextField nameField;
    private String nameFieldLastCorrectValue;

    @FXML
    private Label nameFieldErrorMessageLabel;

    @FXML
    private Button okButton;

    @FXML
    private Button clearButton;

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

        okButton.setOnAction(value -> {
            okButtonOnAction();
        });

        clearButton.setOnAction(value -> {
            clearButtonOnAction();
        });

        okButton.setDisable(true);

        barCodeField.setOnKeyReleased(value -> {
            barCodeFieldOnKeyReleased();
        });
        barCodeField.setPromptText("BarCodu Daxil Edin");

        nameField.setOnKeyReleased(value -> {
            nameFieldOnKeyReleased();
        });

        // her ehtimala qarshi init edirem ki birden NullPoiter Cixar))
        barCodeFieldLastCorrectValue = "";
        nameFieldLastCorrectValue = "";

        barCodeFieldErrorMessageLabel.setText(null);
        nameFieldErrorMessageLabel.setText(null);
    }

    private void barCodeFieldOnKeyReleased() {

        if (isBarcodeInputValid()) {
            // eger Yoxlamadan kecdise Yadda saxlayaq
            barCodeFieldLastCorrectValue = barCodeField.getText();

            if (productDAO.getByBarcode(barCodeField.getText()) == null) {

                myView.showOk(imageViewBarCode);
                checkButtonEnableStatus();

            } else {
                checkButtonEnableStatus();

                myView.showNo(imageViewBarCode);
                barCodeFieldErrorMessageLabel.setText("Məhsul artıq Qeytiyyatdan Keçib");
            }

        } else {
            checkButtonEnableStatus();

            myView.showNo(imageViewBarCode);

            // eger mushteririn daxil elediyi yazi Yoxlamani kecmedise onu evvelki dogru ile evez edek
            barCodeField.setText(barCodeFieldLastCorrectValue);
        }

    }

    private void nameFieldOnKeyReleased() {

        if (isNameInputValid()) {
            myView.showOk(imageViewName);

            checkButtonEnableStatus();

        } else {
            myView.showNo(imageViewName);
            checkButtonEnableStatus();
        }

    }

    private boolean isBarcodeInputValid() {

        if (barCodeField.getText() == null || barCodeField.getText().length() == 0) {
            barCodeFieldErrorMessageLabel.setText("BarCod Daxil Edin!");

            barCodeFieldLastCorrectValue = "";
            return false;
        } else {
            barCodeFieldErrorMessageLabel.setText(null);
            // пытаемся преобразовать в int.
            try {
                Integer.parseInt(barCodeField.getText());
            } catch (NumberFormatException e) {
                barCodeFieldErrorMessageLabel.setText("Kecərli BarCod daxil edin (Tam eded olmalidir)!");

                return false;
            }
        }

        return true;
    }

    private boolean isNameInputValid() {

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            nameFieldErrorMessageLabel.setText("Məhsulun adını daxil edin!");
            return false;
        } else {
            nameFieldErrorMessageLabel.setText(null);
        }

        return true;
    }

    private void checkButtonEnableStatus() {

        if (isBarcodeInputValid() && isNameInputValid() && productDAO.getByBarcode(barCodeField.getText()) == null) {
            okButton.setDisable(false);

        } else {
            okButton.setDisable(true);
        }

    }

    private void okButtonOnAction() {
        Product product = new Product();

        product.setBarCode(barCodeField.getText());
        product.setName(nameField.getText());

        productDAO.create(product);

        //Anbari Gosterek
        ApplicationController.getApplicationController().btnStockOnClick();

    }

    private void clearButtonOnAction() {

        barCodeField.setText(null);
        nameField.setText(null);

        imageViewBarCode.setImage(null);
        imageViewName.setImage(null);

        nameFieldErrorMessageLabel.setText(null);
        barCodeFieldErrorMessageLabel.setText(null);

        barCodeField.requestFocus();

    }

}
