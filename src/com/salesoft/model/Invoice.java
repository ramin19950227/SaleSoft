/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model;

import com.salesoft.util.MyDateConverter;
import java.util.ArrayList;
import java.util.Date;
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
    //Tarixi Cedvelde Normal Yollarla Gostere Bilmek ucun heleki Bele yoldan istifade edecem
    private final StringProperty stringDate; // = bura date - stringe ceviribb verecem

    private Date date = null;
    private ArrayList<InvoiceItem> list = new ArrayList<>();

    /**
     *
     * @param id
     * @param customerName
     * @param totalPrice
     * @param date
     * @param list
     */
    public Invoice(Integer id, String customerName, Double totalPrice, Date date, ArrayList<InvoiceItem> list) {
        this.id = new SimpleIntegerProperty(id);
        this.customerName = new SimpleStringProperty(customerName);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.stringDate = new SimpleStringProperty(MyDateConverter.utilDate.toString(date));

        this.date = date;
        this.list = list;
    }
//
//    public Invoice(Integer id, String customerName, Double totalPrice, String date) {
//        System.out.println("com.salesoft.model.Invoice.<init>(2)");
//        this.id = new SimpleIntegerProperty(id);
//        this.customerName = new SimpleStringProperty(customerName);
//        this.totalPrice = new SimpleDoubleProperty(totalPrice);
//        this.date = new SimpleStringProperty(date);
//
//    }

    public Invoice() {
        this.id = new SimpleIntegerProperty(0);
        this.customerName = new SimpleStringProperty("customerName");
        this.totalPrice = new SimpleDoubleProperty(0.0);
        this.stringDate = new SimpleStringProperty("dateTime");

        this.date = null;
        this.list = null;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", customerName=" + customerName + ", totalPrice=" + totalPrice + ", stringDate=" + stringDate + ", date=" + date + ", list=" + list + '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<InvoiceItem> getList() {
        return list;
    }

    public void setList(ArrayList<InvoiceItem> list) {
        this.list = list;
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

    public final void setStringDate(String value) {
        stringDate.set(value);
    }

    public final String getStringDate() {
        return stringDate.get();
    }

    public final StringProperty stringDateProperty() {
        return stringDate;
    }

}
