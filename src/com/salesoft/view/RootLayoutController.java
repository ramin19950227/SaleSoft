package com.salesoft.view;

import com.salesoft.MainApp;
import javafx.fxml.FXML;

public class RootLayoutController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
    }

    /**
     * Bu metod Mehsullar Cedveli Bolumunu gosterir
     */
    @FXML
    private void showProductTable() {
        mainApp.showProductTable();
    }

    /**
     * Bu metod Satish Bolumunu gosterir
     */
    @FXML
    private void showProductSaleCart() {
        mainApp.showProductSaleCart();
    }

    @FXML
    private void showProductSaleInvoice() {
        mainApp.showProductSaleInvoice();
    }

    @FXML
    private void showProductPurchsePanel() {
        mainApp.showProductPurchsePanel();
    }
}
