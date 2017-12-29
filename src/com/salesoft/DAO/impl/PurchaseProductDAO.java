/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.PurchaseProductDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.PurchaseProduct;
import com.salesoft.util.MyExceptionLogger;
import com.salesoft.util.RsToModel;
import com.salesoft.util.UserOperationLogger;
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
    public void create(PurchaseProduct purchaseProduct) {

        try {
            String SQLQuery = SQL.PurchaseProductSQL.CREATE(purchaseProduct.getDate(), purchaseProduct.getTotalPrice().toString(), purchaseProduct.getProduct().getId().toString(), purchaseProduct.getProduct().getName(), purchaseProduct.getProduct().getQty().toString(), purchaseProduct.getProduct().getPurchasePrice().toString(), purchaseProduct.getProduct().getBarCode(), purchaseProduct.getProduct().getNote());

            UserOperationLogger.logSQL(SQLQuery);

            DBUtil.directExecuteUpdate(SQLQuery);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.create()", ex);
        }
    }

    @Override
    public void update(PurchaseProduct purchaseProduct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PurchaseProduct getById(Integer id) {
        try {
            String SQLQuery = SQL.PurchaseProductSQL.GET(id);

            UserOperationLogger.logSQL(SQLQuery);

            ResultSet rs = DBUtil.directExecuteQuery(SQLQuery);

            return RsToModel.rsToPurchaseProduct(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - PurchaseProductDAO.getAllList()", ex);
            return null;
        }
    }

    @Override
    public ArrayList<PurchaseProduct> getAll() {
        ArrayList<PurchaseProduct> list = new ArrayList<>();

        try {
            String SQLQuery = SQL.PurchaseProductSQL.GET_ALL();

            UserOperationLogger.logSQL(SQLQuery);

            ResultSet rs = DBUtil.directExecuteQuery(SQLQuery);

            list = RsToModel.rsToPurchaseProductList(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - PurchaseProductDAO.getAllList()", ex);
        }

        return list;
    }

}
