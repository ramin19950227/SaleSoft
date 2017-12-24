/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO.Properties;

import com.salesoft.model.Properties.URLProperties;
import com.salesoft.util.MyLogger;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramin
 */
public class URLPropertiesDAO {

    public static URLProperties loadURLPropertiesFromFile() {

        try {
            // heleki Test ucun bu cur hazurlayirm birazdan fayldan oxunmani temin edece
            return new URLProperties(
                    "view/Login.fxml",
                    "view/Application.fxml",
                    "view/Home.fxml",
                    "view/anbar/ProductTable.fxml",
                    "view/sale/ProductSaleCart.fxml",
                    "view/AnbarRootLayout.fxml",
                    "view/anbar/ProductPurchse.fxml",
                    "view/SaleRootLayout.fxml",
                    "view/sale/SaleInvoiceTable.fxml",
                    "view/sale/SaleInvoiceDetailsTable.fxml",
                    "view/anbar/PurchaseProductTable.fxml",
                    "view/Registration.fxml");

        } catch (MalformedURLException ex) {
            System.out.println("SQLException -  URLPropertiesDAO.loadURLPropertiesFromFile(): " + ex);
            MyLogger.logException("SQLException - URLPropertiesDAO.loadURLPropertiesFromFile()", ex);
        }
        return null;
    }

    public static void saveURLPropertiesToFile(URLProperties urlp) {
        System.out.println("com.salesoft.DAO.Properties.URLPropertiesDAO.saveDBPropertiesToFile()");
        System.err.println("TODO");
    }

}
