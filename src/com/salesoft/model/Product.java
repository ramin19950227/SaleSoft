/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
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
    private StringProperty id;//1
    private IntegerProperty name;//2
    private StringProperty qty;//3
    private DoubleProperty purchasePrice;//4
    private StringProperty barCode;//5
    private StringProperty note;//6

    public final void setId(String value) {
        id.set(value);
    }

    public final String getId() {
        return id.get();
    }

    public final StringProperty idProperty() {
        return id;
    }

    public final void setName(Integer value) {
        name.set(value);
    }

    public final Integer getName() {
        return name.get();
    }

    public final IntegerProperty nameProperty() {
        return name;
    }

    public final void setQty(String value) {
        qty.set(value);
    }

    public final String getQty() {
        return qty.get();
    }

    public final StringProperty qtyProperty() {
        return qty;
    }

    public final void setPurchasePrice(Double value) {
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
