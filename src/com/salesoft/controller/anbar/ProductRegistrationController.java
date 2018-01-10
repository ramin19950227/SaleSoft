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
        System.out.println("");
        System.out.println("__________________________________________________________________________");
        System.out.println("ProductRegistrationController.initialize()");
        System.out.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

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
        System.out.println("");
        System.out.println("__________________________________________________________________________");
        System.out.println("ProductRegistrationController.barCodeFieldOnKeyReleased()");
        System.out.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        System.out.println("");
        System.out.println("Field State:");
        System.out.println("BarCode Field: " + bTF.getText());

        Boolean isBarcodeInputValid = isBarcodeInputValid();

        System.out.println("isBarcodeInputValid: " + isBarcodeInputValid);

        if (isBarcodeInputValid) {
            Product product = productDAO.getByBarcode(bTF.getText());

            if (product == null) {

                System.out.println("Product Not Exist. Can be Registered");
                System.out.println(product);

                myView.showOk(bIV);
                checkButtonEnableStatus();

            } else {
                checkButtonEnableStatus();

                System.out.println("Product is Exist. Can NOT be Registered");
                System.out.println(product);

                myView.showNo(bIV);
                bWL.setText("Məhsul artıq Qeytiyyatdan Keçib");
            }

        } else {
            checkButtonEnableStatus();

            myView.showNo(bIV);

        }

    }

    private void nameFieldOnKeyReleased() {
        System.out.println("");
        System.out.println("__________________________________________________________________________");
        System.out.println("ProductRegistrationController.nameFieldOnKeyReleased()");
        System.out.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        System.out.println("");
        System.out.println("Field State:");
        System.out.println("Name Field: " + nTF.getText());

        Boolean isNameInputValid = isNameInputValid();
        System.out.println("isNameInputValid: " + isNameInputValid);

        if (isNameInputValid) {
            myView.showOk(nIV);

            checkButtonEnableStatus();

        } else {
            myView.showNo(nIV);
            checkButtonEnableStatus();
        }

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

    private void okButtonOnAction() {
        System.out.println("");
        System.out.println("__________________________________________________________________________");
        System.out.println("ProductRegistrationController.okButtonOnAction()");
        System.out.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        Product product = new Product();

        product.setBarCode(bTF.getText());
        product.setName(nTF.getText());

        System.out.println("Registering Product");

        productDAO.create(product);

        System.out.println(product);
        System.out.println("Product Succes Registered");
        System.out.println("Product Last State (After Registration) IS:");
        System.out.println(productDAO.getByBarcode(bTF.getText()));

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
