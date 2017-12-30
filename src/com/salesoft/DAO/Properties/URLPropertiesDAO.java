/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO.Properties;

import com.salesoft.model.Properties.URLProperties;
import com.salesoft.util.MyExceptionLogger;
import java.net.MalformedURLException;

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
                    "view/anbar/ProductImport.fxml",
                    "view/SaleRootLayout.fxml",
                    "view/sale/SaleInvoiceTable.fxml",
                    "view/sale/SaleInvoiceDetailsTable.fxml",
                    "view/anbar/ProductImportTable.fxml",
                    "view/Registration.fxml",
                    "view/anbar/ProductRegistration.fxml"
            );

        } catch (MalformedURLException ex) {
            System.out.println("SQLException -  URLPropertiesDAO.loadURLPropertiesFromFile(): " + ex);
            MyExceptionLogger.logException("SQLException - URLPropertiesDAO.loadURLPropertiesFromFile()", ex);
        }
        return null;
    }

    public static void saveURLPropertiesToFile(URLProperties urlp) {
        System.out.println("com.salesoft.DAO.Properties.URLPropertiesDAO.saveDBPropertiesToFile()");
        System.err.println("TODO");
    }

}
