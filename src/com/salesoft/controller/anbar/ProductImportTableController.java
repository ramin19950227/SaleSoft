package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.PurchaseProductDAO;
import com.salesoft.model.ProductImportWrapper;
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
public class ProductImportTableController implements Initializable {

    //cedvel ve List-i Elan edirik
    @FXML
    private TableView<ProductImportWrapper> purchaseProductTable;

    private final ObservableList<ProductImportWrapper> purchaseProductList = FXCollections.observableArrayList();

    // Bu Propertiler ProductImportWrapper - obyektinde saxlanacaq
    @FXML
    private TableColumn<ProductImportWrapper, Number> idColumn;
    @FXML
    private TableColumn<ProductImportWrapper, Number> totalPriceColumn;
    @FXML
    private TableColumn<ProductImportWrapper, String> purchaseDateColumn;

    // Bu Propertiler ise ProductImportWrapper-Obyektinin icinde olan Product Obyektinden Alinacaq
    @FXML
    private TableColumn<ProductImportWrapper, String> productNameColumn;
    @FXML
    private TableColumn<ProductImportWrapper, Number> productQtyColumn;
    @FXML
    private TableColumn<ProductImportWrapper, Number> productPurchasePriceColumn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ProductImportWrapper- obyekti nin Propertileri
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().nameProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        purchaseDateColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseDateProperty());

        // ProductImportWrapper -in Icindeki Product Obyekyinin - Propertileri
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().nameProperty());
        productQtyColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().qtyProperty());
        productPurchasePriceColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().purchasePriceProperty());

        updateTable();
    }

    public void updateTable() {

        ArrayList<ProductImportWrapper> list = new PurchaseProductDAO().getAll();

        if (!list.isEmpty()) {
            purchaseProductList.clear();
            purchaseProductList.addAll(list);
            purchaseProductTable.setItems(purchaseProductList);

        }

    }

}
