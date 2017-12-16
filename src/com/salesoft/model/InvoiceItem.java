/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ramin
 */
public class InvoiceItem {

    private final IntegerProperty id;//1 InvoiceItem-in id-si cedvelde olan id, mehsula hec bir aidiyyati yoxdur
    private final StringProperty name;//2 mehsulun adi
    private final IntegerProperty qty;//3  mehsulun satish sayi
    private final DoubleProperty totalPrice;//4 mehsulunn satish meblegi

    //yeni 
    private final IntegerProperty productId; // mehsulun id-si
    private final StringProperty productBarCode; // mehsulun barodu

    public InvoiceItem(int id, String name, int say, double mebleg, Integer productId, String barCode) {
        super();
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.qty = new SimpleIntegerProperty(say);
        this.totalPrice = new SimpleDoubleProperty(mebleg);

        // yeni elaveler
        this.productId = new SimpleIntegerProperty(productId);
        this.productBarCode = new SimpleStringProperty(barCode);
    }

    public final void setId(Integer value) {
        id.set(value);
    }

    public final Integer getId() {
        return id.get();
    }

    public final IntegerProperty idProperty() {
        return id;
    }

    public final void setName(String value) {
        name.set(value);
    }

    public final String getName() {
        return name.get();
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final void setQty(Integer value) {
        qty.set(value);
    }

    public final Integer getQty() {
        return qty.get();
    }

    public final IntegerProperty qtyProperty() {
        return qty;
    }

    public final void setTotalPrice(Double value) {
        totalPrice.set(value);
    }

    public final Double getTotalPrice() {
        return totalPrice.get();
    }

    public final DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public final void setProductId(Integer value) {
        productId.set(value);
    }

    public final Integer getProductId() {
        return productId.get();
    }

    public final IntegerProperty productIdProperty() {
        return productId;
    }

    public final void setProductBarCode(String value) {
        productBarCode.set(value);
    }

    public final String getProductBarCode() {
        return productBarCode.get();
    }

    public final StringProperty productBarCodeProperty() {
        return productBarCode;
    }

}
