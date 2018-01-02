/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model;

import com.salesoft.util.MyDateConverter;
import java.util.Date;
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
public class ProductImportWrapper {

    private final IntegerProperty id;
    private final StringProperty purchaseDate;
    private final DoubleProperty totalPrice;

    private Date date;
    private Product product;

    /**
     * Constructor For use in DAO.get
     *
     * @param id
     * @param purchaseDate
     * @param totalPrice
     * @param product
     */
    public ProductImportWrapper(Integer id, Date purchaseDate, Double totalPrice, Product product) {

        this.id = new SimpleIntegerProperty(id);
        this.purchaseDate = new SimpleStringProperty(MyDateConverter.utilDate.toStringCustomFormat(purchaseDate, "yyyy-MM-dd"));
        this.totalPrice = new SimpleDoubleProperty(totalPrice);

        this.date = purchaseDate;
        this.product = product;

    }

    /**
     * Constructor FOR DAO.create funktions
     *
     * @param purchaseDate
     * @param product
     */
    public ProductImportWrapper(Date purchaseDate, Product product) {

        this.id = new SimpleIntegerProperty(0);
        this.purchaseDate = new SimpleStringProperty(MyDateConverter.utilDate.toStringCustomFormat(purchaseDate, "yyyy-MM-dd"));
        this.totalPrice = new SimpleDoubleProperty(product.getPurchasePrice() * product.getQty());

        this.date = purchaseDate;
        this.product = product;
    }

    /**
     * Default Constructor with starting initialization data's
     */
    public ProductImportWrapper() {

        this.id = new SimpleIntegerProperty(0);
        this.purchaseDate = new SimpleStringProperty("0000-00-00");
        this.totalPrice = new SimpleDoubleProperty(0.0);

        this.date = new Date();
        this.product = new Product();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    @Override
    public String toString() {
        return "ProductImportWrapper{" + "id=" + id.get() + ", purchaseDate=" + purchaseDate.get() + ", totalPrice=" + totalPrice.get() + ", date=" + MyDateConverter.utilDate.toString(date) + ", product=" + product + '}';
    }
    
    
    
    

}
