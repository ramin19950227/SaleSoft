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
 * Bu obyekt Cart-a yain Sebete yerleshdirilecek Producklar ucun OBOLOCKA dir
 * Оболочка, yani Product CartItemin icinde olacaq, CartItem-ler ise Cart-in
 * icinde
 *
 * @author Ramin
 */
public class CartItem {

    private IntegerProperty id;//1 mehsulun id-si irelide Key kimi istifade olunacaq
    private StringProperty name;//2 mehsulun adi
    private IntegerProperty qty;//3  mehsulun satish sayi
    private DoubleProperty salePrice;//4 mehsulunn 1- ededinin satish qiymeti 
    private DoubleProperty totalPrice;//5 mehsulun cemi catish meblegi
    private Product product;//6//6

    {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(null);
        this.qty = new SimpleIntegerProperty(0);
        this.salePrice = new SimpleDoubleProperty(0);
        this.totalPrice = new SimpleDoubleProperty(0);
        this.product = null;
    }

    public CartItem(Product product, int qty, double salePrice) {
        System.out.println("product == null () " + (product == null));
        System.out.println("product.getName () " + (product.getName()));
        
        id.set(product.getId());
        this.setName(product.getName());
        this.setSalePrice(salePrice);
        this.setQty(qty);
        this.setProduct(product);
        this.setTotalPrice(salePrice * qty);
    }

    public Product getProduct() {
        return product;
    }

    /**
     * Bu Mehsul bolumudur bunu set Etmek olmaz deyishmek olmaz cunki product
     * obyeti Construktora verilir ve bitdi, tesevvur edin ish esnasinda bashqa
     * proqramci mehsulu deyishir ne olacaqsa yaxshi sheyler olmayacaq id-ler
     * ferqli olacaq birinin id-si ile bashqa mal satilacaq alma yazilacaq
     * armuddan say azalacaq almanida armud qiymetine satacaq BIYABIRCILIQ
     * :)))))
     *
     * @param product
     */
    private void setProduct(Product product) {
        this.product = product;
    }

    /**
     * ID unikaldir deyishmek olmaz construktor product verilende onnan alir ve
     * KEY-kimi yani acar kimi istifade olunur bunu deyishmek olmaz
     *
     * @param value
     */
    public final void setId(Integer value) {
        System.out.println(value);
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

    public final void setSalePrice(Double value) {
        salePrice.set(value);
    }

    public final Double getSalePrice() {
        return salePrice.get();
    }

    public final DoubleProperty salePriceProperty() {
        return salePrice;
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
