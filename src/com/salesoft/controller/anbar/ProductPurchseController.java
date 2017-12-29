/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.DAO.impl.PurchaseProductDAO;
import com.salesoft.model.Product;
import com.salesoft.util.MyDateConverter;
import com.salesoft.util.MyView;
import com.salesoft.util.TextFieldValidator;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class ProductPurchseController implements Initializable {

    /**
     * TextField-s
     */
    @FXML
    private TextField bTF, nTF, qTF, pTF;

    /**
     * DatePicer
     */
    @FXML
    private DatePicker datePicker;

    /**
     * TextArea
     */
    @FXML
    private TextArea nTA;

    /**
     * ImageView-s
     */
    @FXML
    private ImageView bIV, nIV, qIV, pIV, dIV;

    /**
     * Label-s For Warnings
     */
    @FXML
    private Label bWL, nWL, qWL, pWL, dWL;

    /**
     * Buttons
     */
    @FXML
    private Button saveButton, clearButton;

    /**
     * ProductDAO
     */
    private final ProductDAO ProductDAO = new ProductDAO();
    /**
     * PurchaseProductDAO
     */
    private final PurchaseProductDAO purchaseProductDAO = new PurchaseProductDAO();

    /**
     * Util's
     */
    private final MyView myView = new MyView();
    private final TextFieldValidator tfv = new TextFieldValidator();

    /**
     * Additional Variables's
     */
    private Product barcodeEnteredProduct = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // DatePicker-e bugunku tarixi set edek
        datePicker.setValue(LocalDate.now());
        datePicker.setOnAction(event -> {
            //for Testing
            LocalDate date = datePicker.getValue();
            //System.out.println("Selected LocalDate: " + date);
            Date d = MyDateConverter.asDate(date);
            //System.out.println("Selected util.Date: " + d);

        });

        // Label-lerimizi Sifirlayaq
        bWL.setText(null);
        nWL.setText(null);
        qWL.setText(null);
        pWL.setText(null);
        dWL.setText(null);

        bTF.setOnKeyReleased(value -> {
            bTFOnKeyReleased();
        });

    }

    private void bTFOnKeyReleased() {
        bWL.setText(null);

        if (tfv.isNotNull(bTF)) {
            if (tfv.isCorrectInt(bTF)) {
                Product product = ProductDAO.getByBarcode(bTF.getText());
                if (product != null) {
                    myView.showOk(bIV);
                    bWL.setText("Mehsul Qeydiyyatdan Kecib, Adi: " + product.getName());

                } else {
                    myView.showWarning(bIV);
                    bWL.setText("Mehsul Qeydiyyatdan KECMEYIB, Yeni olaraq Qeydiyyata Alinacaq");
                }

            } else {
                bWL.setText("Zehmet Olmasa Keçərli barCod Daxil Edin (Tam Ədəd Olmalıdır)");
                myView.showNo(bIV);
            }
        } else {
            bWL.setText("Zehmet Olmasa barCod Daxil Edin");
            myView.showNo(bIV);
        }

    }

    //
    //    @FXML
    //    private void barCodeFieldOnAction() {
    //
    //        barCodeField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //
    //        if (barCodeField.getText() == null || barCodeField.getText().length() == 0) {
    //
    //            barCodeField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
    //            barCodeFieldErrorMessage.setText("  BarCodu Daxil Edin");
    //            barCodeField.requestFocus();
    //
    //        } else {
    //            String barCode = barCodeField.getText();
    //
    //            barcodeEnteredProduct = ProductDAO.getByBarcode(barCode);
    //
    //            if (barcodeEnteredProduct == null) {
    //
    //                barCodeFieldErrorMessage.setText("  Məhsul Qeydiyyatda Yoxdur: Yeni Olaraq Qeydiyyata Alinacaq");
    //                nameField.setDisable(false);
    //                nameField.setText(null);
    //                nameField.requestFocus();
    //
    //            } else {
    //
    //                barCodeFieldErrorMessage.setText(null);
    //
    //                nameField.setText(barcodeEnteredProduct.getName());
    //                nameField.setDisable(true);
    //                qtyField.requestFocus();
    //
    //            }
    //
    //        }
    //
    //    }
    //
    //    @FXML
    //    private void hanleNameField() {
    //        qtyField.requestFocus();
    //        qtyField.selectAll();
    //    }
    //
    //    @FXML
    //    private void hanleQtyField() {
    //        purchasePriceField.requestFocus();
    //        purchasePriceField.selectAll();
    //
    //    }
    //
    //    @FXML
    //    private void hanlePurchasePriceField() {
    //        noteField.requestFocus();
    //        noteField.selectAll();
    //    }
    //
    //    @FXML
    //    private void hanleNoteField() {
    //        saveButton
    //        , clearButton.requestFocus();
    //    }
    //
    //    @FXML
    //    private void hanleSaveButton() {
    //        LocalDate date = datePicker.getValue();
    //        System.out.println("Selected LocalDate: " + date);
    //        Date d = MyDateConverter.asDate(date);
    //        System.out.println("Selected util.Date: " + d);
    //
    //        boolean isValid = isInputValid();
    //
    //        if (isValid && barcodeEnteredProduct != null) {
    //
    //            // bu mehsulumuzun kohne sayi
    //            Integer oldQty = barcodeEnteredProduct.getQty();
    //
    //            //bu yeni daxil edilen say
    //            Integer enteredQty = Integer.valueOf(qtyField.getText());
    //
    //            //buda netice son say yani CEMI
    //            Integer sumQty = oldQty + enteredQty;
    //
    //            barcodeEnteredProduct.setName(nameField.getText());
    //            barcodeEnteredProduct.setQty(sumQty);
    //            barcodeEnteredProduct.setPurchasePrice(Double.valueOf(purchasePriceField.getText()));
    //            barcodeEnteredProduct.setBarCode(barCodeField.getText());
    //            barcodeEnteredProduct.setNote(noteField.getText());
    //
    //            //indi yenileyek
    //            ProductDAO.update(barcodeEnteredProduct);
    //
    //            java.util.Date date1 = MyDateConverter.asDate(datePicker.getValue());
    //
    //            purchaseProductDAO.create(new PurchaseProduct(0, "", barcodeEnteredProduct.getQty() * barcodeEnteredProduct.getPurchasePrice(), barcodeEnteredProduct), date1);
    //
    //            // sonra ise Anbari gosteririk
    //            ApplicationController.getApplicationController().btnStockOnClick();
    //
    //        } else if (isValid && barcodeEnteredProduct == null) {
    //
    //            String name = nameField.getText();
    //            Integer qty = Integer.valueOf(qtyField.getText());
    //            Double purchasePrice = Double.valueOf(purchasePriceField.getText());
    //            String barCode = barCodeField.getText();
    //            String note = noteField.getText();
    //
    //            Product product = new Product(0, name, qty, purchasePrice, barCode, note);
    //
    //            // Yeni DAO-muza Yeni Sorgu Gonderek )) Bele Cox Gozel Gorsenir
    //            // yeni obyektimizi hazirlayiriq ve gonderirik metodumuza
    //            ProductDAO.create(product);
    //
    //            java.util.Date date1 = MyDateConverter.asDate(datePicker.getValue());
    //
    //            PurchaseProduct pp = new PurchaseProduct(0, "", product.getQty() * product.getPurchasePrice(), product);
    //
    //            purchaseProductDAO.create(pp, date1);
    //
    //            ApplicationController.getApplicationController().btnStockOnClick();
    //        }
    //    }
    //
    //    @FXML
    //    private void hanleCanselButton() {
    //        clearFields();
    //        barcodeEnteredProduct = null;
    //    }
    //
    //    @FXML
    //    private void clearFields() {
    //        barCodeField.setText(null);
    //        barCodeField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //
    //        nameField.setText(null);
    //        nameField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //
    //        qtyField.setText(null);
    //        qtyField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //
    //        purchasePriceField.setText(null);
    //        purchasePriceField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //
    //        noteField.setText(null);
    //        noteField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //    }
    //
    //    private boolean isInputValid() {
    //        String errorMessage = "";
    //
    //        if (datePicker.getValue() == null) {
    //            errorMessage += "Zehmet Olmasa Medaxil Tarixini secin!\n";
    //        }
    //
    //        if (nameField.getText() == null || nameField.getText().length() == 0) {
    //            errorMessage += "Mehsulun Adini dogru daxil edin!\n";
    //            nameField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
    //        }
    //
    //        if (qtyField.getText() == null || qtyField.getText().length() == 0 || qtyField.getText().equals("0")) {
    //            errorMessage += "Mehsulun Sayini dogru daxil edin!\n";
    //            qtyField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
    //        } else {
    //            // пытаемся преобразовать в int.
    //            try {
    //                Integer.parseInt(qtyField.getText());
    //            } catch (NumberFormatException e) {
    //                errorMessage += "Kecərli say daxil edin (Tam eded olmalidir)!\n";
    //                qtyField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
    //            }
    //        }
    //
    //        if (purchasePriceField.getText() == null || purchasePriceField.getText().length() == 0) {
    //            errorMessage += "Mehsulun Qiymetini dogru daxil edin!\n";
    //            purchasePriceField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
    //        } else {
    //            // пытаемся преобразовать почтовый код в int.
    //            try {
    //                Double.parseDouble(purchasePriceField.getText());
    //            } catch (NumberFormatException e) {
    //                errorMessage += "Kecərli Qiymət daxil edin!\n";
    //                purchasePriceField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
    //            }
    //        }
    //
    //        if (barCodeField.getText() == null || barCodeField.getText().length() == 0) {
    //            errorMessage += "Mehsulun Barcodunu dogru daxil edin!\n";
    //            barCodeField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
    //
    //        }
    //
    //        if (errorMessage.length() == 0) {
    //            nameField.setStyle("-fx-border-color: white;-fx-border-width: 0");
    //            qtyField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //            purchasePriceField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //            barCodeField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
    //            return true;
    //        } else {
    //            // Показываем сообщение об ошибке.
    //            Alert alert = new Alert(Alert.AlertType.ERROR);
    //            alert.setTitle("Dogru Daxil edin");
    //            alert.setHeaderText("Zehmet olmasa Mehsulun melumatlarini dogru daxil edin");
    //            alert.setContentText(errorMessage);
    //
    //            alert.showAndWait();
    //
    //            return false;
    //        }
    //    }
}
