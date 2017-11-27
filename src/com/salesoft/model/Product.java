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
 * Product Model Class-idir yani bu klassimizla modelimizin nece olacagini
 * tesvir edirik Programimiza deyirik ki Product deye bir obyekt var ve bu - bu
 * - bu Propertileri var bu - bu metodlardan istifade olunacaq metolar ise get
 * ve set metodlaridir
 *
 * @author Ramin
 */
public class Product {

    //modellerin Property-lerini javaFX de nece lzimdirsa ele yigiram
    //cunki adi qayda ile int a, String b, tipli yazsam irelide
    //problemle rastlashacam Cedvelde Redkte 
    private IntegerProperty id;//1
    private StringProperty name;;//2
    private IntegerProperty qty;//3  
    private DoubleProperty purchasePrice;//4
    private StringProperty barCode;//5
    private StringProperty note;//6

    public Product() {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("name");
        this.qty = new SimpleIntegerProperty(0);
        this.purchasePrice = new SimpleDoubleProperty(0.0);
        this.barCode = new SimpleStringProperty("barCode");
        this.note = new SimpleStringProperty("note");
    }

    public final void setId(int value) {
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

    public final void setQty(int value) {
        qty.set(value);
    }

    public final Integer getQty() {
        return qty.get();
    }

    public final IntegerProperty qtyProperty() {
        return qty;
    }

    public final void setPurchasePrice(double value) {
        purchasePrice.set(value);
    }

    public final Double getPurchasePrice() {
        return purchasePrice.get();
    }

    public final DoubleProperty purchasePriceProperty() {
        return purchasePrice;
    }

    public final void setBarCode(String value) {
        barCode.set(value);
    }

    public final String getBarCode() {
        return barCode.get();
    }

    public final StringProperty barCodeProperty() {
        return barCode;
    }

    public final void setNote(String value) {
        note.set(value);
    }

    public final String getNote() {
        return note.get();
    }

    public final StringProperty noteProperty() {
        return note;
    }

}
