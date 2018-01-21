/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.InvoiceItemDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.InvoiceItem;
import com.salesoft.model.Product;
import com.salesoft.util.MyExceptionLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ramin
 */
public class InvoiceItemDAO implements InvoiceItemDAOIntf {

    @Override
    //<editor-fold defaultstate="collapsed" desc="rsToInvoiceItemList()">
    public ArrayList<InvoiceItem> rsToInvoiceItemList(ResultSet rs) throws SQLException {
        try {
            ArrayList<InvoiceItem> list = new ArrayList<>();
            
            //budur dogru metod bir dene next() etdikse Melumatlari almaliyiq 2-ci next etmemishden evvel
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("product_id"));
                p.setName(rs.getString("product_name"));
                p.setQty(rs.getInt("product_qty"));
                p.setPurchasePrice(rs.getDouble("product_purchasePrice"));
                p.setSalePrice(rs.getDouble("product_salePrice"));
                p.setBarCode(rs.getString("product_barCode"));
                p.setNote(rs.getString("product_note"));
                
                InvoiceItem item = new InvoiceItem(rs.getInt("id"), rs.getInt("invoiceId"), rs.getDouble("totalPrice"), p);
                
                list.add(item);
            }
            return list;
            
        } catch (SQLException ex) {
            throw ex;
            
        } finally {
            DBUtil.AllDisconnect();
        }
    }
//</editor-fold>

    @Override
    public void create(InvoiceItem item) {
        try {
//            String SQLQuery = SQL.InvoiceItemSQL.CREATE(item);
//            UserOperationLogger.logSQL(SQLQuery);
//            DBUtil.mySQLExecuteUpdate(SQLQuery);

            String SQLQuery = SQL.InvoiceItemSQL.CREATE_FOR_ACCESS(item);
            DBUtil.msAccessExecuteUpdate(SQLQuery);
        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - InvoiceItemDAO.create(InvoiceItem item)", ex);
        }
    }

    @Override
    public void update(InvoiceItem item) {
        try {
//            String SQLQuery = SQL.InvoiceItemSQL.UPDATE(item);
//            UserOperationLogger.logSQL(SQLQuery);
//            DBUtil.mySQLExecuteUpdate(SQLQuery);

            String SQLQuery = SQL.InvoiceItemSQL.UPDATE_FOR_ACCESS(item);
            DBUtil.msAccessExecuteUpdate(SQLQuery);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - InvoiceItemDAO.create(InvoiceItem item)", ex);
        }
    }

    @Override
    public void delete(InvoiceItem item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InvoiceItem get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceItem> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceItem> getAllById(Integer id) {
        try {
//            String SQLQuery = SQL.InvoiceItemSQL.GET_ALL_BY_INVOICE_ID(id);
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);

            ResultSet rs = DBUtil.msAccessExecuteQuery("SELECT * FROM InvoiceItem WHERE invoiceId=" + id + " ORDER BY `id` DESC");

            return rsToInvoiceItemList(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - InvoiceDAO.getAllInvoiceItemListById(Integer id)", ex);
            return null;
        }
    }

}
