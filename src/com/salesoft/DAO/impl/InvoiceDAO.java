package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.InvoiceDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.CartItem;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import com.salesoft.util.MyLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceDAO implements InvoiceDAOIntf {

    @Override
    public void create(Invoice invoice) {
        try {
            DBUtil.directExecuteUpdate(SQL.InvoiceSQL.CREATE(invoice));
        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.create(Invoice invoice)", ex);

        }
    }

    @Override
    public void update(Invoice invoice) {
        try {
            DBUtil.directExecuteUpdate(SQL.InvoiceSQL.UPDATE(invoice));
        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.update(Invoice invoice)", ex);

        }
    }

    @Override
    public void delete(Invoice invoice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Invoice get(int id) {
        Invoice invoice = null;
        try {
            ArrayList<InvoiceItem> list = getAllInvoiceItemListById(id);

            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceSQL.GET(id));

            while (rs.next()) {
                invoice = new Invoice(rs.getInt(1), rs.getString(6), rs.getDouble(3), rs.getString(2), list);
            }

        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.get(int id)", ex);
        }
        return invoice;
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<Invoice> getAll() {
        ArrayList<Invoice> list = new ArrayList<>();
        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceSQL.GET_ALL());

            while (rs.next()) {
                list.add(new Invoice(rs.getInt(1), rs.getString(6), rs.getDouble(3), rs.getString(2)));
            }

        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.getAll()", ex);
        }
        return list;

    }

    @Override
    public ArrayList<InvoiceItem> getAllInvoiceItemListById(Integer id) {
        try {
            ArrayList<InvoiceItem> list = new ArrayList<>();
            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceItem.GET_ALL_BY_INVOICE_ID(id));

            while (rs.next()) {
                list.add(new InvoiceItem(rs.getInt(1), rs.getString(4), rs.getInt(5), rs.getDouble(7), rs.getInt(3), rs.getString(8)));
            }
            return list;

        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.getAllInvoiceItemListById(Integer id)", ex);
            return null;
        }
    }

    @Override
    public ArrayList<Invoice> getInvoiceListByNameLike(String name) {
        ArrayList<Invoice> list = new ArrayList<>();
        try {

            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceSQL.GET_ALL_BY_NAME_LIKE(name));
            while (rs.next()) {
                list.add(new Invoice(rs.getInt(1), rs.getString(6), rs.getDouble(3), rs.getString(2)));
            }
        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.getInvoiceListByNameLike(String name)", ex);
        }
        return list;
    }

    @Override
    public void insertNewInvoiceItem(Integer history_id, CartItem c) {
        try {
            DBUtil.dbExecuteUpdate(
                    SQL.InvoiceItem.INVOICEITEM_ADD_NEW
                            .replaceAll("history_idR", history_id.toString())
                            .replaceAll("p_idR", c.getId().toString())
                            .replaceAll("p_nameR", c.getName())
                            .replaceAll("p_sayR", c.getQty().toString())
                            .replaceAll("p_qiymetR", c.getSalePrice().toString())
                            .replaceAll("p_meblegR", c.getTotalPrice().toString())
                            .replaceAll("p_barcodeR", c.getProduct().getBarCode())
                            .replaceAll("p_qeydR", "note")
                            .replaceAll("p_satishdan_evvelki_sayR", c.getProduct().getQty().toString())
            );
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Integer getLastIdInInvoiceTable() {
        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceSQL.GET_LAST_ID());
            if (rs.next()) {
                return rs.getInt("MAX(id)");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        try {
            DBUtil.dbExecuteUpdate(
                    SQL.InvoiceItem.INVOICEITEM_UPDATE_BY_ID
                            .replaceAll("p_sayR", invoiceItem.getQty().toString())
                            .replaceAll("p_meblegR", invoiceItem.getTotalPrice().toString())
                            .replaceAll("idR", invoiceItem.getId().toString())
            );
        } catch (SQLException ex) {

        }

    }

}
