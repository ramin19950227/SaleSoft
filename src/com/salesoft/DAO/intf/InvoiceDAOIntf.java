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

    public void create(Invoice invoice);

    public void update(Invoice invoice);

    public void delete(Invoice invoice);

    public Invoice get(int id);

    ArrayList<Invoice> getAll();

    ArrayList<Invoice> getInvoiceListByNameLike(String name);

    Integer getLastIdInInvoiceTable();

    //BUNDAN SONRAKILER InvoiceItem-e aiddir ve Burdan Cixartmaq Lazimdir
    ArrayList<InvoiceItem> getAllInvoiceItemListById(Integer id);

    void insertNewInvoiceItem(Integer history_id, CartItem c);

    void updateInvoiceItem(InvoiceItem invoiceItem);

}
