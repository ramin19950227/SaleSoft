package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.InvoiceDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import com.salesoft.util.MyLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDAO implements InvoiceDAOIntf {

    InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();

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
            ArrayList<InvoiceItem> list = invoiceItemDAO.getAllById(id);
            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceSQL.GET(id));

            while (rs.next()) {
                invoice = new Invoice(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        new java.util.Date(rs.getTimestamp(4).getTime()),
                        list
                );
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
                list.add(new Invoice(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        new java.util.Date(rs.getTimestamp(4).getTime()),
                        new ArrayList<>()
                ));
            }

        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.getAll()", ex);
        }
        return list;

    }

    @Override
    public ArrayList<Invoice> getAllByNameLike(String name) {
        ArrayList<Invoice> list = new ArrayList<>();
        try {

            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceSQL.GET_ALL_BY_NAME_LIKE(name));
            while (rs.next()) {
                list.add(
                        new Invoice(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getDouble(3),
                                new java.util.Date(rs.getTimestamp(4).getTime()),
                                new ArrayList<>()
                        )
                );
            }
        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.getInvoiceListByNameLike(String name)", ex);
        }
        return list;
    }

    @Override
    public Integer getLastId() {
        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceSQL.GET_LAST_ID());
            if (rs.next()) {
                return rs.getInt("MAX(id)");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.getLastIdInInvoiceTable()", ex);
            return null;
        }
    }
}
