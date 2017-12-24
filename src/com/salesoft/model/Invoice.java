/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Invoice - Qaime Modelidir icinde Qaime haqda melumatlari ve Satilan mehsullar
 * var )))
 *
 * @author Ramin Ismayilov
 * @TARIX 19.11.2017
 *
 */
public class Invoice {

    private final IntegerProperty id;
    private final StringProperty customerName;
    private final DoubleProperty totalPrice;
    private final StringProperty date;

    private ArrayList<InvoiceItem> list = new ArrayList<>();

    {// inicializasiya bloku vtomatik ishe dushur
        this.id = new SimpleIntegerProperty(0);
        this.customerName = new SimpleStringProperty(null);
        this.totalPrice = new SimpleDoubleProperty(0);
        this.date = new SimpleStringProperty(null);
    }

    /**
     * Constructor
     *
     * @param id
     * @param date
     * @param totalPrice
     * @param customerName
     * @param list
     */
    public Invoice(int id, String date, double totalPrice, String customerName, ArrayList<InvoiceItem> list) {
        super();
        this.id.set(id);
        this.date.set(date);
        this.totalPrice.set(totalPrice);
        this.customerName.set(customerName);
        this.list = list;
    }

    public Invoice(int id, String date, double totalPrice, String customerName) {
        super();
        this.id.set(id);
        this.date.set(date);
        this.totalPrice.set(totalPrice);
        this.customerName.set(customerName);
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

    public final void setCustomerName(String value) {
        customerName.set(value);
    }

    public final String getCustomerName() {
        return customerName.get();
    }

    public final StringProperty customerNameProperty() {
        return customerName;
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

    public final void setDate(String value) {
        date.set(value);
    }

    public final String getDate() {
        return date.get();
    }

    public final StringProperty dateProperty() {
        return date;
    }

    public ArrayList<InvoiceItem> getList() {
        return list;
    }

    public void setList(ArrayList<InvoiceItem> list) {
        this.list = list;
    }

    public void addItemToList(InvoiceItem item) {
        this.list.add(item);
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", customerName=" + customerName + ", totalPrice=" + totalPrice + ", date=" + date + ", list=" + list + '}';
    }

}
