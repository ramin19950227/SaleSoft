/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.Product;
import com.salesoft.util.MyLogger;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ramin
 */
public class ProductDAO extends AbstractDAO<Product, Integer> {

    @Override
    public boolean create(Product entity) {

        try {
            DBUtil.directExecuteUpdate(SQL.Product.CREATE(
                    entity.getName(),
                    entity.getQty().toString(),
                    entity.getPurchasePrice().toString(),
                    entity.getBarCode(),
                    entity.getNote()));
            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException -  DAO.ProductDAO.create(): " + ex);
            MyLogger.logException("SQLException - DAO.ProductDAO.create()", ex);
            return false;
        }

    }

    @Override
    public Product update(Product entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product getById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Product> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
