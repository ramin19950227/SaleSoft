/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.Properties;

import com.salesoft.MainApp;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Ramin
 */
public class URLProperty {

    private final URL MainAppURL;
    private final URL LoginFxmlURL; // 3 in DB table
    private final URL ApplicationFxmlURL; //4 DB table
    private final URL HomeFxmlURL; //5
    private final URL ProductTableURL; //6
    private final URL ProductSaleCartURL; //7

    /**
     *
     * @param LoginFxmlAddress - LoginFxml 3-in table
     * @param ApplicationFxml - ApplicationFxml 4-in table
     * @param HomeFxml - HomeFxml - 5
     * @param ProductTable - ProductTable - 6
     * @param ProductSaleCart - 7
     * @throws java.net.MalformedURLException
     */
    public URLProperty(String LoginFxmlAddress, String ApplicationFxml, String HomeFxml, String ProductTable, String ProductSaleCart) throws MalformedURLException {
        //esas paket unvanimizi aliriq
        String mainAddress = MainApp.class.getResource("").toString();
        this.MainAppURL = new URL(mainAddress);
        this.LoginFxmlURL = new URL(mainAddress + LoginFxmlAddress);
        this.ApplicationFxmlURL = new URL(mainAddress + ApplicationFxml);
        this.HomeFxmlURL = new URL(mainAddress + HomeFxml);
        this.ProductTableURL = new URL(mainAddress + ProductTable);
        this.ProductSaleCartURL = new URL(mainAddress + ProductSaleCart);
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

}
