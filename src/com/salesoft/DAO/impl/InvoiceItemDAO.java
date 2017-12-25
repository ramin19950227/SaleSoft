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
import com.salesoft.util.MyLogger;
import com.salesoft.util.RsToModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ramin
 */
public class InvoiceItemDAO implements InvoiceItemDAOIntf {

    @Override
    public void create(InvoiceItem item) {
        try {
            DBUtil.directExecuteUpdate(SQL.InvoiceItemSQL.CREATE(item));
        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceItemDAO.create(InvoiceItem item)", ex);
        }
    }

    @Override
    public void update(InvoiceItem item) {
        try {
            DBUtil.directExecuteUpdate(SQL.InvoiceItemSQL.UPDATE(item));
        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceItemDAO.create(InvoiceItem item)", ex);
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
            ArrayList<InvoiceItem> list;
            ResultSet rs = DBUtil.directExecuteQuery(SQL.InvoiceItemSQL.GET_ALL_BY_INVOICE_ID(id));

            list = RsToModel.rsToInvoiceItemList(rs);

            return list;

        } catch (SQLException ex) {
            MyLogger.logException("SQLException - InvoiceDAO.getAllInvoiceItemListById(Integer id)", ex);
            return null;
        }
    }

}
