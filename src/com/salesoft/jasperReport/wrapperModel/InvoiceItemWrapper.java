/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.jasperReport.wrapperModel;

import com.salesoft.model.InvoiceItem;

/**
 * JasperReport-a vermek ucun InvoiceItem obyektini Izolasiya etmek ucun
 * istifade olunur (Wrapper)
 *
 * @author Ramin
 */
public class InvoiceItemWrapper {

    Integer lineNumber;
    String name;
    Integer qty;
    Double price;
    Double totalPrice;

    // Bu Contstructora InvoiceItem vereceyik ve neticede hazir InvoiceItemWrapper Obyekti alacayiq
    public InvoiceItemWrapper(InvoiceItem invoiceItem) {
        lineNumber = 0; // bu zadece bashlangic olaraq teyin edirem
        name = invoiceItem.getProduct().getName();
        qty = invoiceItem.getProduct().getQty();
        price = invoiceItem.getProduct().getSalePrice();
        totalPrice = invoiceItem.getTotalPrice();
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "InvoiceItemWrapper{" + "lineNumber=" + lineNumber + ", name=" + name + ", qty=" + qty + ", price=" + price + ", totalPrice=" + totalPrice + '}';
    }

}
