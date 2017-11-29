/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view;

import com.salesoft.DAO.ProductUpdateDAO;
import com.salesoft.MainApp;
import com.salesoft.model.Product;
import com.sun.prism.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class ProductEditController implements Initializable {

    @FXML
    private int id;

    @FXML
    private Label idLabel;

    @FXML
    private TextField nameField;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField purchasePriceField;
    @FXML
    private TextField barCodeField;
    @FXML
    private TextField noteField;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

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

    /**
     * Bu metod parametrinde verilen mehsulun melumatlarini redakte xanalarina
     * doldurur ProductEdit.fxml-de olan
     *
     * @param product
     */
    public void setEditingProductId(Product product) {
        System.out.println("setEditingProductId - called" + ""
                + "product == null ?:" + (product == null));
        this.id = product.getId();
        this.idLabel.setText(product.getId().toString());

        this.nameField.setText(product.getName());
        this.qtyField.setText(product.getQty().toString());
        this.purchasePriceField.setText(product.getPurchasePrice().toString());
        this.barCodeField.setText(product.getBarCode());
        this.noteField.setText(product.getNote());
    }

    /**
     * Bu metod redkte xanalarindan melumatlari alib mehsulumuzu o melumatlarla
     * yenileyir ID-ile yenilemezden evvel yoxlayir eger esas xanalar boshdursa
     * o zaman xeberdarliq cixarir ve yenilemeye qoymur
     *
     * @TEKLIF - ireliye bosh olan hansi xana oldugunu teyin edib onun regini
     * deyisib ve focus elemek olar
     */
    @FXML
    private void updateProduct() {

        if (nameField.getText() == null
                || nameField.getText().equals("")
                || qtyField.getText() == null
                || qtyField.getText().equals("")
                || purchasePriceField.getText() == null
                || purchasePriceField.getText().equals("")
                || barCodeField.getText() == null
                || barCodeField.getText().equals("")
                || noteField.getText() == null
                || noteField.getText().equals("")) {

            // xeberdarliq cixariram ki hansisa xana oshdur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Zehmet Olmaza Xanalari Doldurun");
            alert.setHeaderText("Xanalari Tam Doldurun");
            alert.setContentText("Yadda Saxlamaq olmaz");

            //xeberdarligi eekranda gosterib ve cavab verilene dek gozleyirem
            alert.showAndWait();

            //ok basildiqdan sonra say xanasini focus edire
            // irelde bosh olan xanani teyin edib onu focus edecem
            qtyField.requestFocus();

            // say xanasinin rengii ve cercive rengini deyishdim
            qtyField.setStyle("-fx-border-color: red;-fx-border-width: 5;");;

            // ve YENILEME metodundan cixiram ki davam elemesin ve bosh melumati gondermesin BAZAYA
            return;
        }

        int id = this.id;
        String name = nameField.getText();
        int qty = Integer.valueOf(qtyField.getText());
        double purchasePrice = Double.valueOf(purchasePriceField.getText());
        String barCode = barCodeField.getText();
        String note = noteField.getText();

        ProductUpdateDAO.updateProductNameById(id, name);
        ProductUpdateDAO.updateProductQtyById(id, qty);
        ProductUpdateDAO.updateProductPurchasePriceById(id, purchasePrice);
        ProductUpdateDAO.updateProductBarCodeById(id, barCode);
        ProductUpdateDAO.updateProductNoteById(id, note);

        //melumatlari yeniledikden sonra cedveimizide yenileyirik
        mainApp.showProductTable();

        // TEKLIF: lazim olsa ireliye mehsulu yeniledikden sonra cedveli de yeniledikdensonra
        //mehsulumuzu cedvelde sece de bilerik selectile 
        // sadece onnan otru maindede ProdctTableControllerdede 
        //metod lazimdir ki men ona mehsulu verende o select elesin
    }

    @FXML
    private void cancelEditing() {
        mainApp.clearRight();
    }

}
