package com.salesoft.DAO.intf;

import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author teyyub , Dec 24, 2017 , 10:05:07 AM
 */
public interface InvoiceItemDAOIntf {

    ArrayList<InvoiceItem> rsToInvoiceItemList(ResultSet rs) throws SQLException;

    public void create(InvoiceItem item);

    public void update(InvoiceItem item);

    public void delete(InvoiceItem item);

    public InvoiceItem get(int id);

    public ArrayList<InvoiceItem> getAll();

    ArrayList<InvoiceItem> getAllById(Integer id);

}
