/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.PurchaseProductDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.ProductImportWrapper;
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
    public void create(ProductImportWrapper purchaseProduct) {

        try {
//            String SQLQuery = SQL.PurchaseProductSQL.CREATE(purchaseProduct.getDate(), purchaseProduct.getTotalPrice().toString(), purchaseProduct.getProduct().getId().toString(), purchaseProduct.getProduct().getName(), purchaseProduct.getProduct().getQty().toString(), purchaseProduct.getProduct().getPurchasePrice().toString(), purchaseProduct.getProduct().getBarCode(), purchaseProduct.getProduct().getNote());
//            UserOperationLogger.logSQL(SQLQuery);
//            DBUtil.mySQLExecuteUpdate(SQLQuery);

            String SQLQuery = SQL.PurchaseProductSQL.CREATE_FOR_ACCES(purchaseProduct.getDate(), purchaseProduct.getTotalPrice().toString(), purchaseProduct.getProduct().getId().toString(), purchaseProduct.getProduct().getName(), purchaseProduct.getProduct().getQty().toString(), purchaseProduct.getProduct().getPurchasePrice().toString(), purchaseProduct.getProduct().getBarCode(), purchaseProduct.getProduct().getNote());
            DBUtil.msAccessExecuteUpdate(SQLQuery);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.create()", ex);
        }
    }

    @Override
    public void update(ProductImportWrapper purchaseProduct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductImportWrapper getById(Integer id) {
        try {
//            String SQLQuery = SQL.PurchaseProductSQL.GET(id);
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);

            //            return "SELECT * FROM `" + DB_NAME + "`.PurchaseProduct WHERE id=" + id;
            ResultSet rs = DBUtil.msAccessExecuteQuery("select * from PurchaseProduct where id=" + id);

            return RsToModel.rsToPurchaseProduct(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - PurchaseProductDAO.getAllList()", ex);
            return null;
        }
    }

    @Override
    public ArrayList<ProductImportWrapper> getAll() {
        ArrayList<ProductImportWrapper> list = new ArrayList<>();

        try {
//            String SQLQuery = SQL.PurchaseProductSQL.GET_ALL();
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);
//

            ResultSet rs = DBUtil.msAccessExecuteQuery("SELECT * FROM PurchaseProduct ORDER BY `id` DESC LIMIT 1000");

            list = RsToModel.rsToPurchaseProductList(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - PurchaseProductDAO.getAllList()", ex);
        }

        return list;
    }

}
