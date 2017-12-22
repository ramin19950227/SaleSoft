/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller.anbar;

import com.salesoft.DAO.ProductDAO;
import com.salesoft.DAO.ProductGetDAO;
import com.salesoft.model.Product;
import com.salesoft.controller.AnbarRootLayoutController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class ProductPurchseController implements Initializable {

    @FXML
    private TextField barCodeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField qtyField;

    @FXML
    private TextField purchasePriceField;

    @FXML
    private TextField noteField;

    @FXML
    private Button saveButton;

    private Product barcodeEnteredProduct = null;
    private final ProductDAO ProductDAO = new ProductDAO();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleBarCode() {

        barCodeField.setStyle("-fx-border-color: white;-fx-border-width: 0;");

        if (barCodeField.getText() == null || barCodeField.getText().length() == 0) {

            barCodeField.setStyle("-fx-border-color: red;-fx-border-width: 5;");

            barCodeField.requestFocus();
        } else {
            String barCode = barCodeField.getText();

            barcodeEnteredProduct = ProductGetDAO.getProductByBarCode(barCode);

            if (barcodeEnteredProduct == null) {

                nameField.requestFocus();
                nameField.setText("Yeni Mehsul:");
                nameField.selectAll();
            } else {
                clearFields();
                setProductToEditFields(barcodeEnteredProduct);
            }

        }

    }

    @FXML
    private void hanleNameField() {
        qtyField.requestFocus();
        qtyField.selectAll();
    }

    @FXML
    private void hanleQtyField() {
        purchasePriceField.requestFocus();
        purchasePriceField.selectAll();

    }

    @FXML
    private void hanlePurchasePriceField() {
        noteField.requestFocus();
        noteField.selectAll();
    }

    @FXML
    private void hanleNoteField() {
        saveButton.requestFocus();
    }

    @FXML
    private void hanleSaveButton() {
        boolean isValid = isInputValid();

        if (isValid && barcodeEnteredProduct != null) {

            // bu mehsulumuzun kohne sayi
            Integer oldQty = barcodeEnteredProduct.getQty();

            //bu yeni daxil edilen say
            Integer enteredQty = Integer.valueOf(qtyField.getText());

            //buda netice son say yani CEMI
            Integer sumQty = oldQty + enteredQty;

            barcodeEnteredProduct.setName(nameField.getText());
            barcodeEnteredProduct.setQty(sumQty);
            barcodeEnteredProduct.setPurchasePrice(Double.valueOf(purchasePriceField.getText()));
            barcodeEnteredProduct.setBarCode(barCodeField.getText());
            barcodeEnteredProduct.setNote(noteField.getText());

            //indi yenileyek
            ProductDAO.update(barcodeEnteredProduct);

            // sonra ise Anbari gosteririk
            AnbarRootLayoutController.appControl.btnStockOnClick();

        } else if (isValid && barcodeEnteredProduct == null) {

            String name = nameField.getText();
            Integer qty = Integer.valueOf(barCodeField.getText());
            Double purchasePrice = Double.valueOf(purchasePriceField.getText());
            String barCode = barCodeField.getText();
            String note = noteField.getText();

            Product product = new Product(0, name, qty, purchasePrice, barCode, note);

            // Yeni DAO-muza Yeni Sorgu Gonderek )) Bele Cox Gozel Gorsenir
            // yeni obyektimizi hazirlayiriq ve gonderirik metodumuza
            ProductDAO.create(product);

            AnbarRootLayoutController.appControl.btnStockOnClick();
        }
    }

    @FXML
    private void hanleCanselButton() {
        clearFields();
        barcodeEnteredProduct = null;
    }

    /**
     * Additional Metods
     */
    /**
     *
     * @param product
     */
    private void setProductToEditFields(Product product) {
        if (product != null) {
            barCodeField.setText(product.getBarCode());
            nameField.setText(product.getName());
            nameField.setEditable(false);
            qtyField.setText(product.getQty().toString());
            qtyField.requestFocus();
            purchasePriceField.setText(product.getPurchasePrice().toString());

        }
    }

    @FXML
    private void clearFields() {
        barCodeField.setText(null);
        barCodeField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
        barCodeField.requestFocus();

        nameField.setText(null);
        nameField.setEditable(true);
        nameField.setStyle("-fx-border-color: white;-fx-border-width: 0;");

        qtyField.setText(null);
        qtyField.setStyle("-fx-border-color: white;-fx-border-width: 0;");

        purchasePriceField.setText(null);
        purchasePriceField.setStyle("-fx-border-color: white;-fx-border-width: 0;");

        noteField.setText(null);
        noteField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Mehsulun Adini dogru daxil edin!\n";
            nameField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
        }

        if (qtyField.getText() == null || qtyField.getText().length() == 0) {
            errorMessage += "Mehsulun Sayini dogru daxil edin!\n";
            qtyField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
        } else {
            // пытаемся преобразовать в int.
            try {
                Integer.parseInt(qtyField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Kecərli say daxil edin (Tam eded olmalidir)!\n";
                qtyField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
            }
        }

        if (purchasePriceField.getText() == null || purchasePriceField.getText().length() == 0) {
            errorMessage += "Mehsulun Qiymetini dogru daxil edin!\n";
            purchasePriceField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Double.parseDouble(purchasePriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Kecərli Qiymət daxil edin!\n";
                purchasePriceField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
            }
        }

        if (barCodeField.getText() == null || barCodeField.getText().length() == 0) {
            errorMessage += "Mehsulun Barcodunu dogru daxil edin!\n";
            barCodeField.setStyle("-fx-border-color: red;-fx-border-width: 5;");

        }

        if (errorMessage.length() == 0) {
            nameField.setStyle("-fx-border-color: white;-fx-border-width: 0");
            qtyField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            purchasePriceField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            barCodeField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dogru Daxil edin");
            alert.setHeaderText("Zehmet olmasa Mehsulun melumatlarini dogru daxil edin");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
