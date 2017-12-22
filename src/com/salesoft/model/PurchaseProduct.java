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
public class PurchaseProduct {

    private final IntegerProperty id;
    private final StringProperty purchaseDate;
    private final DoubleProperty totalPrice;

    private final Product product;

    /**
     *
     * @param id
     * @param purchaseDate
     * @param totalPrice
     * @param product
     */
    public PurchaseProduct(Integer id, String purchaseDate, Double totalPrice, Product product) {

        this.id = new SimpleIntegerProperty(id);
        this.purchaseDate = new SimpleStringProperty(purchaseDate);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);

        this.product = product;

    }

    public Product getProduct() {
        return product;
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

    public final void setPurchaseDate(String value) {
        purchaseDate.set(value);
    }

    public final String getPurchaseDate() {
        return purchaseDate.get();
    }

    public final StringProperty purchaseDateProperty() {
        return purchaseDate;
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

}
