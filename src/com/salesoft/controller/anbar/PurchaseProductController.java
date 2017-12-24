/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller.anbar;

import com.salesoft.DAO.PurchaseProductDAO;
import com.salesoft.DAO.PurchaseProductDAO;
import com.salesoft.model.PurchaseProduct;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class PurchaseProductController implements Initializable {

    //cedvel ve List-i Elan edirik
    @FXML
    private TableView<PurchaseProduct> purchaseProductTable;

    private final ObservableList<PurchaseProduct> purchaseProductList = FXCollections.observableArrayList();

    // Bu Propertiler PurchaseProduct - obyektinde saxlanacaq
    @FXML
    private TableColumn<PurchaseProduct, Number> idColumn;
    @FXML
    private TableColumn<PurchaseProduct, Number> totalPriceColumn;
    @FXML
    private TableColumn<PurchaseProduct, String> purchaseDateColumn;

    // Bu Propertiler ise PurchaseProduct-Obyektinin icinde olan Product Obyektinden Alinacaq
    @FXML
    private TableColumn<PurchaseProduct, String> productNameColumn;
    @FXML
    private TableColumn<PurchaseProduct, Number> productQtyColumn;
    @FXML
    private TableColumn<PurchaseProduct, Number> productPurchasePriceColumn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // PurchaseProduct- obyekti nin Propertileri
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().nameProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        purchaseDateColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseDateProperty());

        // PurchaseProduct -in Icindeki Product Obyekyinin - Propertileri
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().nameProperty());
        productQtyColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().qtyProperty());
        productPurchasePriceColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().purchasePriceProperty());

        updateTable();
    }

    public void updateTable() {

        ArrayList<PurchaseProduct> list = new PurchaseProductDAO().getAll();

        if (!list.isEmpty()) {
            purchaseProductList.clear();
            purchaseProductList.addAll(list);
            purchaseProductTable.setItems(purchaseProductList);

        }

    }

}
