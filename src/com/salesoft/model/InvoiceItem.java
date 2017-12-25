package com.salesoft.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * InvoiceItem - Class - Updated
 *
 * @author Ramin
 */
public class InvoiceItem {

    //this Object data's
    private final IntegerProperty id;
    private final IntegerProperty invoiceId;
    private final DoubleProperty totalPrice;

    //Inner Object
    private final Product product;

    /**
     *
     * @param id
     * @param invoiceId
     * @param itemTotalPrice
     * @param product
     */
    public InvoiceItem(Integer id, Integer invoiceId, Double itemTotalPrice, Product product) {
        //this properties init
        this.id = new SimpleIntegerProperty(id);
        this.invoiceId = new SimpleIntegerProperty(invoiceId);
        this.totalPrice = new SimpleDoubleProperty(itemTotalPrice);
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

    public final void setInvoiceId(Integer value) {
        invoiceId.set(value);
    }

    public final Integer getInvoiceId() {
        return invoiceId.get();
    }

    public final IntegerProperty invoiceIdProperty() {
        return invoiceId;
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
        return "InvoiceItem{" + "id=" + id + ", invoiceId=" + invoiceId + ", totalPrice=" + totalPrice + ", product=" + product + '}';
    }

}
