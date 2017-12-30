/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.DAO.impl.PurchaseProductDAO;
import com.salesoft.controller.ApplicationController;
import com.salesoft.model.Product;
import com.salesoft.model.PurchaseProduct;
import com.salesoft.util.MyDateConverter;
import com.salesoft.util.MyView;
import com.salesoft.util.TextFieldValidator;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Application;
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
     * TextArea - Note
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
    private final ProductDAO productDAO = new ProductDAO();
    /**
     * PurchaseProductDAO
     */
    private final PurchaseProductDAO purchaseProductDAO = new PurchaseProductDAO();

    /**
     * Util's
     */
    private final MyView myView = new MyView();
    private final TextFieldValidator TFValidator = new TextFieldValidator();
    private Product product;

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

        // adam Barcodu daxil edib enteri basdiqda yoxlama edirik ve lazimi xanani Focus edirik
        bTF.setOnAction(value -> {
            bTFOnAction();
        });

        nTF.setOnKeyReleased(value -> {
            nTFOnKeyReleased();
        });

        //ad xanassinda enter basildiqda eger bosh deyiles say xanasini fokus ele
        nTF.setOnAction(value -> {
            if (TFValidator.isNotNull(nTF)) {
                qTF.requestFocus();
            }
        });

        //say xanasinda enter basdiqda yoxlayaq tam eded daxil edilibse davamedek
        qTF.setOnAction(value -> {
            if (qTFOnKeyReleased()) {
                pTF.requestFocus();
            }
        });

        qTF.setOnKeyReleased(value -> {
            qTFOnKeyReleased();
        });

        pTF.setOnAction(value -> {
            if (TFValidator.isCorrectInt(pTF)) {
                myView.showOk(pIV);
                pWL.setText(null);
                datePicker.requestFocus();
            } else {
                myView.showNo(pIV);
                pWL.setText("Zehmet Olmasa Qiymeti daxil edin (Keçərli Tamm eded ve ya (Double=?))");
            }

        });

        pTF.setOnKeyReleased(value -> {
            pTFOnKeyReleased();
        });

        clearButton.setOnAction(value -> {
            clearAll();

        });

        saveButton.setOnAction(value -> {
            saveButtonOnAction();
        });

    }

    private void bTFOnKeyReleased() {
        product = null;
        bWL.setText(null);
        String bTemp = bTF.getText();
        clearAll();
        bTF.appendText(bTemp);

        if (TFValidator.isNotNull(bTF)) {
            if (TFValidator.isCorrectInt(bTF)) {

                product = productDAO.getByBarcode(bTF.getText());

                if (product != null) {
                    myView.showOk(bIV);
                    bWL.setText("Mehsul Qeydiyyatdan Kecib");

                    //melumatlari Productdan Alib Set edek
                    nTF.setText(product.getName());
                    qTF.setText("0");
                    pTF.setText(product.getPurchasePrice().toString());

                    //ad xanasini sondurek ki redakte etmek olmasin
                    nTF.setDisable(true);

                } else {
                    myView.showWarning(bIV);
                    bWL.setText("Mehsul Qeydiyyatdan KECMEYIB, Yeni olaraq Qeydiyyata Alinacaq");
                    clearTextFields();
                    bTF.appendText(bTemp);
                }

            } else {
                bWL.setText("Zehmet Olmasa Keçərli barCod Daxil Edin (Tam Ədəd Olmalıdır)");
                myView.showNo(bIV);

                //clearAll(); - Bu metoducagirdiqda barcoduda sifirlayir
            }
        } else {
            bWL.setText("Zehmet Olmasa barCod Daxil Edin");
            myView.showNo(bIV);
            clearTextFields();
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
    private void clearAll() {
        // null to TextField's
        bTF.setText(null);
        nTF.setText(null);
        qTF.setText(null);
        pTF.setText(null);

        //ad xanasini Enable edek
        nTF.setDisable(false);

        //null to TextAre = note
        nTA.setText(null);

        //tarixe Bugungu tarixi yaziriq
        datePicker.setValue(LocalDate.now());

        //shekileri sifirlayaq
        bIV.setImage(null);
        nIV.setImage(null);
        qIV.setImage(null);
        pIV.setImage(null);
        dIV.setImage(null);

        //labelleri sifirlayaq
        bWL.setText(null);
        nWL.setText(null);
        qWL.setText(null);
        pWL.setText(null);
        dWL.setText(null);

    }

    private void clearTextFields() {
        // null to TextField's
        bTF.setText(null);
        nTF.setText(null);
        qTF.setText(null);
        pTF.setText(null);

        //ad xanasini Enable edek
        nTF.setDisable(false);

        //null to TextAre = note
        nTA.setText(null);

        //tarixe Bugungu tarixi yaziriq
        datePicker.setValue(LocalDate.now());
    }

    private void bTFOnAction() {
        //yoxlayaq eger barcod yazilibsa 
        if (TFValidator.isCorrectInt(bTF)) {
            //eger daxil edilen barcod bazada varsa Product null deyil ve null deyilse demeli ad xanasinda melumat var
            // ad xanasinda melumat varsa ve barcod xanasinda enter basilibsa
            // say xanasina fokus ele. yox mehsul bazada yoxdursa ve barcod xanasinda enter basilibsa
            // o zaman ad daxil etmek ucun ad xanasni Fokus ele
            if (product != null) {
                qTF.requestFocus();
            } else {
                nTF.requestFocus();
            }
        }
    }

    private void nTFOnKeyReleased() {
        if (TFValidator.isNotNull(nTF)) {
            myView.showOk(nIV);
            nWL.setText("");
        } else {
            myView.showNo(nIV);
            nWL.setText("Zehmet Olmasa ad daxil edin");
        }

    }

    private boolean qTFOnKeyReleased() {
        if (TFValidator.isCorrectInt(qTF)) {
            myView.showOk(qIV);
            nWL.setText(null);
            return true;
        } else {
            myView.showNo(qIV);
            qWL.setText("Zehmet Olmasa Sayi daxil edin (Keçərli Tamam eded)");
            return false;
        }
    }

    private boolean pTFOnKeyReleased() {
        if (TFValidator.isCorrectDouble(pTF)) {
            myView.showOk(pIV);
            pWL.setText(null);
            return true;
        } else {
            myView.showNo(pIV);
            pWL.setText("Zehmet Olmasa Qiymeti daxil edin (Keçərli Tamm eded ve ya (Double=?))");
            return false;
        }
    }

    private void saveButtonOnAction() {

        if (product != null) {

            product.setQty(Integer.parseInt(qTF.getText()));
            product.setPurchasePrice(Double.parseDouble(pTF.getText()));
            product.setNote(nTA.getText());

            LocalDate localDate = datePicker.getValue();
            Date date = MyDateConverter.asDate(localDate);

            //birinci alish haqqinda melumati yazaq sonra ise mehsulun sayini cemleyib yenileyek
            PurchaseProduct pp = new PurchaseProduct(date, product);
            purchaseProductDAO.create(pp);

            //saylari yenileyek ki evvelki say silinmesin
            Integer oldQty = productDAO.getByBarcode(product.getBarCode()).getQty();
            product.plusQty(oldQty);

            productDAO.update(product);

            ApplicationController.getApplicationController().btnStockOnClick();

        } else {

            //ilk olaraq Mehsulu Qeydiyyatdan kecirek ve sonra alish edek
            product = new Product();

            product.setBarCode(bTF.getText());
            product.setName(nTF.getText());

            productDAO.create(product);

            // indi ise yeni qeydiyyatdan kecen mehsulumuzu alaq yeni id-si ile ve alish emri verek sonra
            product = productDAO.getByBarcode(bTF.getText());

            // indi ise alish edek
            product.setQty(Integer.parseInt(qTF.getText()));
            product.setPurchasePrice(Double.parseDouble(pTF.getText()));
            product.setNote(nTA.getText());

            LocalDate localDate = datePicker.getValue();
            Date date = MyDateConverter.asDate(localDate);

            //birinci alish haqqinda melumati yazaq sonra ise mehsulun sayini cemleyib yenileyek
            PurchaseProduct pp = new PurchaseProduct(date, product);
            purchaseProductDAO.create(pp);

            //saylari yenileyek ki evvelki say silinmesin
            Integer oldQty = productDAO.getByBarcode(product.getBarCode()).getQty();
            product.plusQty(oldQty);

            productDAO.update(product);

            ApplicationController.getApplicationController().btnStockOnClick();

        }

    }

}
