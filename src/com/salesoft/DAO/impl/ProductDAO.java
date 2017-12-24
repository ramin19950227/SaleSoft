 
package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.ProductDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.Product;
import com.salesoft.util.MyLogger;
import com.salesoft.util.RsToModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ramin
 */
public class ProductDAO implements ProductDAOIntf{

   
    @Override
    public void create(Product entity) {

        try {
            DBUtil.directExecuteUpdate(SQL.ProductSQL.CREATE(entity));
//            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.create(): " + ex);
            MyLogger.logException("SQLException - ProductDAO.create()", ex);
//            return false;
        }

    }

    @Override
    public void update(Product entity) {
        try {
            DBUtil.directExecuteUpdate(SQL.ProductSQL.UPDATE(entity));
//            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.update(): " + ex);
            MyLogger.logException("SQLException - ProductDAO.update()", ex);
//            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            DBUtil.directExecuteUpdate(SQL.ProductSQL.DELETE(id));
            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.delete(): " + ex);
            MyLogger.logException("SQLException - ProductDAO.delete()", ex);
            return false;
        }

    }

    @Override
    public Product getById(Integer id) {

        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.ProductSQL.GET(id));

            return RsToModel.rsToProduct(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.getById(): " + ex);
            MyLogger.logException("SQLException - ProductDAO.getById()", ex);
            return null;
        }
    }

    @Override
    public Product getByBarcode(String barCode) {

        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.ProductSQL.GET(barCode));

            return RsToModel.rsToProduct(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.getByBarcode(): " + ex);
            MyLogger.logException("SQLException - ProductDAO.getByBarcode()", ex);
            return null;
        }
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();

        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.ProductSQL.GET_ALL());

            list = RsToModel.rsToProductList(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.getAll()(): " + ex);
            MyLogger.logException("SQLException - ProductDAO.getAll()()", ex);
        }

        return list;
    }

    @Override
    public ArrayList<Product> searchByNameLike(String name) {
        ArrayList<Product> list = new ArrayList<>();

        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.ProductSQL.SEARCH_BY_NAME_LIKE(name));

            list = RsToModel.rsToProductList(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.searchByNameLike(): " + ex);
            MyLogger.logException("SQLException - ProductDAO.searchByNameLike()", ex);
        }

        return list;
    }

    public ArrayList<Product> searchByBarcode(String barCode) {
        ArrayList<Product> list = new ArrayList<>();

        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.ProductSQL.SEARCH_BY_BARODE(barCode));

            list = RsToModel.rsToProductList(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.searchByNameLike(): " + ex);
            MyLogger.logException("SQLException - ProductDAO.searchByNameLike()", ex);
        }

        return list;
    }

   

}
