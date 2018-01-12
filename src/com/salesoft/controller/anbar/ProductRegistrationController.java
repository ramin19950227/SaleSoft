/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.controller.ApplicationController;
import com.salesoft.model.Product;
import com.salesoft.util.MyDateConverter;
import com.salesoft.util.MyView;
import com.salesoft.util.UserOperationLogger;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
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

    @FXML
    private Label bWL, nWL;

    @FXML
    private Button saveButton, clearButton;

    private final ProductDAO productDAO = new ProductDAO();
    private final MyView myView = new MyView();
    private final PrintWriter LOGWriter = UserOperationLogger.getLogWriter();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductRegistrationController.initialize()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

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

        bWL.setText(null);
        nWL.setText(null);
    }

    private void barCodeFieldOnKeyReleased() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductRegistrationController.barCodeFieldOnKeyReleased()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        LOGWriter.println("");
        LOGWriter.println("Field State:");
        LOGWriter.println("BarCode Field: " + bTF.getText());

        Boolean isBarcodeInputValid = isBarcodeInputValid();

        LOGWriter.println("isBarcodeInputValid: " + isBarcodeInputValid);

        if (isBarcodeInputValid) {
            Product product = productDAO.getByBarcode(bTF.getText());

            if (product == null) {

                LOGWriter.println("Product Not Exist. Can be Registered");
                LOGWriter.println(product);

                myView.showOk(bIV);
                checkButtonEnableStatus();

            } else {
                checkButtonEnableStatus();

                LOGWriter.println("Product is Exist. Can NOT be Registered");
                LOGWriter.println(product);

                myView.showNo(bIV);
                bWL.setText("Məhsul artıq Qeytiyyatdan Keçib");
            }

        } else {
            checkButtonEnableStatus();

            myView.showNo(bIV);

        }

    }

    private void nameFieldOnKeyReleased() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductRegistrationController.nameFieldOnKeyReleased()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        LOGWriter.println("");
        LOGWriter.println("Field State:");
        LOGWriter.println("Name Field: " + nTF.getText());

        Boolean isNameInputValid = isNameInputValid();
        LOGWriter.println("isNameInputValid: " + isNameInputValid);

        if (isNameInputValid) {
            myView.showOk(nIV);

            checkButtonEnableStatus();

        } else {
            myView.showNo(nIV);
            checkButtonEnableStatus();
        }

    }

    private void okButtonOnAction() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductRegistrationController.okButtonOnAction()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        Product product = new Product();

        product.setBarCode(bTF.getText());
        product.setName(nTF.getText());

        LOGWriter.println("Registering Product");

        productDAO.create(product);

        LOGWriter.println(product);
        LOGWriter.println("Product Succes Registered");
        LOGWriter.println("Product Last State (After Registration) IS:");
        LOGWriter.println(productDAO.getByBarcode(bTF.getText()));

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

    private boolean isBarcodeInputValid() {
        if (bTF.getText() == null || bTF.getText().trim().length() == 0) {
            bWL.setText("BarCod Daxil Edin!");
            return false;
        } else {
            bWL.setText(null);
            return true;
        }
    }

    private boolean isNameInputValid() {
        if (nTF.getText() == null || nTF.getText().trim().length() == 0) {
            nWL.setText("Məhsulun adını daxil edin!");
            return false;
        } else {
            nWL.setText(null);
            return true;
        }
    }

    private void checkButtonEnableStatus() {

        if (isBarcodeInputValid() && isNameInputValid() && productDAO.getByBarcode(bTF.getText()) == null) {
            saveButton.setDisable(false);
        } else {
            saveButton.setDisable(true);
        }
    }

}
