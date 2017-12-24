package com.salesoft.DAO.intf;

import com.salesoft.model.CartItem;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import java.util.ArrayList;

/**
 *
 * @author teyyub , Dec 24, 2017 , 10:05:07 AM
 */
public interface InvoiceDAOIntf {

    Invoice getInvoiceById(int id);

    ArrayList<Invoice> getAllInvoice();

    ArrayList<InvoiceItem> getAllInvoiceItemListById(Integer id);

    void updateInvoiceCustoemerNameById(Integer id, String customerName);

    ArrayList<Invoice> getInvoiceListByNameLike(String name);

    void insertNewInvoice(String customer, Double mebleg);

    void insertNewInvoiceItem(Integer history_id, CartItem c);

    Integer getLastIdInInvoiceTable();

    void updateInvoiceItem(InvoiceItem invoiceItem);

    void updateInvoice(Invoice invoice);
}
