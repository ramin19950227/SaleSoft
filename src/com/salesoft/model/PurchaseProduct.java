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

/**
 *
 * @author Ramin
 */
public class PurchaseProduct {

    private final IntegerProperty id;
    private final DoubleProperty totalPrice;

    private final Product product;

    public PurchaseProduct(Integer itemId, Product product) {
        //mehsulun sayi
        Integer productQty = product.getQty();

        //mehsulun alish qiymeti
        Double productPurchasePrice = product.getPurchasePrice();

        //meblegimizi oyrenek
        Double productTotalPrice = productQty * productPurchasePrice;

        this.id = new SimpleIntegerProperty(itemId);
        this.product = product;
        this.totalPrice = new SimpleDoubleProperty(productTotalPrice);

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
