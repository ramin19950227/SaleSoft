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

    {// inicializasiya bloku vtomatik ishe dushur
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(null);
        this.qty = new SimpleIntegerProperty(0);
        this.totalPrice = new SimpleDoubleProperty(0);
    }

    public InvoiceItem(int id, String name, int say, double mebleg) {
        super();
        this.id.set(id);
        this.name.set(name);
        this.qty.set(say);
        this.totalPrice.set(mebleg);
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

}
