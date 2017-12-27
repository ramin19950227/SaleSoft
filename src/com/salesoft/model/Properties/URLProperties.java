/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model.Properties;

import com.salesoft.MainApp;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Ramin
 */
public class URLProperties {

    private final URL MainAppURL;
    private final URL LoginFxmlURL;
    private final URL ApplicationFxmlURL;
    private final URL HomeFxmlURL;
    private final URL ProductTableURL;
    private final URL ProductSaleCartURL;
    private final URL AnbarRootLayoutURL;
    private final URL ProductPurchseURL;
    private final URL SaleRootLayoutURL;
    private final URL SaleInvoiceTableURL;
    private final URL SaleInvoiceDetailsTableURL;
    private final URL PurchaseInvoiceTableURL;
    private final URL RegistrationURL;
    private final URL ProductRegistrarionURL;

    /**
     *
     * @param LoginFxmlAddress
     * @param ApplicationFxml
     * @param HomeFxml
     * @param ProductTable
     * @param ProductSaleCart
     * @param AnbarRootLayout
     * @param ProductPurchse
     * @param SaleRootLayout
     * @param SaleInvoiceTable
     * @param SaleInvoiceDetailsTable
     * @param PurchaseInvoiceTable
     * @param RegistrationURL
     * @param ProductRegistrarionURL
     * @throws java.net.MalformedURLException
     */
    public URLProperties(
            String LoginFxmlAddress,
            String ApplicationFxml,
            String HomeFxml,
            String ProductTable,
            String ProductSaleCart,
            String AnbarRootLayout,
            String ProductPurchse,
            String SaleRootLayout,
            String SaleInvoiceTable,
            String SaleInvoiceDetailsTable,
            String PurchaseInvoiceTable,
            String RegistrationURL,
            String ProductRegistrarionURL
    ) throws MalformedURLException {

        //esas paket unvanimizi aliriq
        String mainAddress = MainApp.class.getResource("").toString();
        this.MainAppURL = new URL(mainAddress);
        this.LoginFxmlURL = new URL(mainAddress + LoginFxmlAddress);
        this.ApplicationFxmlURL = new URL(mainAddress + ApplicationFxml);
        this.HomeFxmlURL = new URL(mainAddress + HomeFxml);
        this.ProductTableURL = new URL(mainAddress + ProductTable);
        this.ProductSaleCartURL = new URL(mainAddress + ProductSaleCart);
        this.AnbarRootLayoutURL = new URL(mainAddress + AnbarRootLayout);
        this.ProductPurchseURL = new URL(mainAddress + ProductPurchse);
        this.SaleRootLayoutURL = new URL(mainAddress + SaleRootLayout);
        this.SaleInvoiceTableURL = new URL(mainAddress + SaleInvoiceTable);
        this.SaleInvoiceDetailsTableURL = new URL(mainAddress + SaleInvoiceDetailsTable);
        this.PurchaseInvoiceTableURL = new URL(mainAddress + PurchaseInvoiceTable);
        this.RegistrationURL = new URL(mainAddress + RegistrationURL);
        this.ProductRegistrarionURL = new URL(mainAddress + ProductRegistrarionURL);
    }

    public URL getMainAppURL() {
        return MainAppURL;
    }

    public URL getLoginFxmlURL() {
        return LoginFxmlURL;
    }

    public URL getApplicationFxmlURL() {
        return ApplicationFxmlURL;
    }

    public URL getHomeFxmlURL() {
        return HomeFxmlURL;
    }

    public URL getProductTableURL() {
        return ProductTableURL;
    }

    public URL getProductSaleCartURL() {
        return ProductSaleCartURL;
    }

    public URL getAnbarRootLayoutURL() {
        return AnbarRootLayoutURL;
    }

    public URL getProductPurchseURL() {
        return ProductPurchseURL;
    }

    public URL getSaleRootLayoutURL() {
        return SaleRootLayoutURL;
    }

    public URL getSaleInvoiceTableURL() {
        return SaleInvoiceTableURL;
    }

    public URL getSaleInvoiceDetailsTableURL() {
        return SaleInvoiceDetailsTableURL;
    }

    public URL getPurchaseInvoiceTableURL() {
        return PurchaseInvoiceTableURL;
    }

    public URL getRegistrationURL() {
        return RegistrationURL;
    }

    public URL getProductRegistrarionURL() {
        return ProductRegistrarionURL;
    }

}
