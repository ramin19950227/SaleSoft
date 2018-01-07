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
    private final IntegerProperty id;//1
    private final StringProperty name;
    ;//2
    private final IntegerProperty qty;//3  
    private final DoubleProperty purchasePrice;//4
    private final StringProperty barCode;//5
    private final StringProperty note;//6

    /**
     * Kohne Constructor bele olanda gerek her sheyi set edem hansinisa unutsam
     * Problem Cixacaq
     *
     * @deprecated
     */
    public Product() {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.qty = new SimpleIntegerProperty(0);
        this.purchasePrice = new SimpleDoubleProperty(0.0);
        this.barCode = new SimpleStringProperty("");
        this.note = new SimpleStringProperty("");
    }

    /**
     * Yeni Construktor Yeni DAO-da Bunu Istifade edecem
     *
     * @param id
     * @param name
     * @param qty
     * @param purchasePrice
     * @param barCode
     * @param note
     */
    public Product(Integer id, String name, Integer qty, Double purchasePrice, String barCode, String note) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.qty = new SimpleIntegerProperty(qty);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.barCode = new SimpleStringProperty(barCode);
        this.note = new SimpleStringProperty(note);
    }

    /**
     *
     * Metod Bu Obyektin Yeni Clonunu Qaytarir Yeni Obyekt Bu obyekt ile eyni
     * Unvana yonllendirmiller, bir soznen Heap-da yeni obyekt qurur ve unvanini
     * qaytarir
     *
     * @return
     */
    public Product dublicateThisProduct() {
        Product newProduct = new Product();

        newProduct.setId(this.getId());
        newProduct.setName(this.getName());
        newProduct.setBarCode(this.getBarCode());
        newProduct.setNote(this.getNote());
        newProduct.setPurchasePrice(this.getPurchasePrice());
        newProduct.setQty(this.getQty());

        return newProduct;
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

    @Override
    public String toString() {
        return "Product{" + "id=" + id.get() + ", name=" + name.get() + ", qty=" + qty.get() + ", purchasePrice=" + purchasePrice.get() + ", barCode=" + barCode.get() + ", note=" + note.get() + '}';
    }

    /**
     * Yeni Metod Mehsulun indiki sayina verilen sayi elave edir yani Plus
     *
     * @param qty
     */
    public void plusQty(Integer qty) {
        setQty(getQty() + qty);
    }

    public void minusQty(Integer qty) {
        setQty(getQty() - qty);
    }
}
