/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO.impl;

import com.salesoft.DAO.AbstractDAO;
import com.salesoft.DAO.intf.PurchaseProductDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.PurchaseProduct;
import com.salesoft.util.MyLogger;
import com.salesoft.util.RsToModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Product - Obyektimizle Melumat Bazasi arasindaki Emeliyyatlar
 *
 * @author Ramin
 */
public class PurchaseProductDAO implements PurchaseProductDAOIntf {

   

    @Override
    public PurchaseProduct getById(Integer id) {

        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.PurchaseProductSQL.GET(id));

            return RsToModel.rsToPurchaseProduct(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  PurchaseProductDAO.getAllList(): " + ex);
            MyLogger.logException("SQLException - PurchaseProductDAO.getAllList()", ex);
            return null;
        }
    }

    @Override
    public ArrayList<PurchaseProduct> getAll() {
        ArrayList<PurchaseProduct> list = new ArrayList<>();

        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.PurchaseProductSQL.GET_ALL());

            list = RsToModel.rsToPurchaseProductList(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  PurchaseProductDAO.getAllList(): " + ex);
            MyLogger.logException("SQLException - PurchaseProductDAO.getAllList()", ex);
        }

        return list;
    }

    @Override
    public void create(PurchaseProduct entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(PurchaseProduct entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
