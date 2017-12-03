package com.salesoft.view;

import com.salesoft.MainApp;
import javafx.fxml.FXML;

public class RootLayoutController {

    private MainApp mainApp;

    @FXML
    private void initialize() {

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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

}
