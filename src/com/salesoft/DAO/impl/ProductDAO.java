package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.ProductDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.Product;
import com.salesoft.util.MyExceptionLogger;
import com.salesoft.util.RsToModel;
import com.salesoft.util.UserOperationLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ramin
 */
public class ProductDAO implements ProductDAOIntf {

    @Override
    public void create(Product entity) {

        try {
            String SQLQuery = SQL.ProductSQL.CREATE(entity);

            UserOperationLogger.logSQL(SQLQuery);

            DBUtil.directExecuteUpdate(SQLQuery);
//            return true;

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.create()", ex);
//            return false;
        }

    }

    @Override
    public void update(Product entity) {
        try {
            String SQLQuery = SQL.ProductSQL.UPDATE(entity);

            UserOperationLogger.logSQL(SQLQuery);

            DBUtil.directExecuteUpdate(SQLQuery);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.update()", ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            String SQLQuery = SQL.ProductSQL.DELETE(id);

            UserOperationLogger.logSQL(SQLQuery);

            DBUtil.directExecuteUpdate(SQLQuery);
            return true;

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.delete()", ex);
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
            MyExceptionLogger.logException("SQLException - ProductDAO.getById()", ex);
            return null;
        }
    }

    @Override
    public Product getByBarcode(String barCode) {
        try {
            String SQLQuery = SQL.ProductSQL.GET(barCode);

            UserOperationLogger.logSQL(SQLQuery);

            ResultSet rs = DBUtil.directExecuteQuery(SQLQuery);

            return RsToModel.rsToProduct(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.getByBarcode()", ex);
            return null;
        }
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();

        try {
            String SQLQuery = SQL.ProductSQL.GET_ALL();

            UserOperationLogger.logSQL(SQLQuery);

            ResultSet rs = DBUtil.directExecuteQuery(SQLQuery);

            list = RsToModel.rsToProductList(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.getAll()(): " + ex);
            MyExceptionLogger.logException("SQLException - ProductDAO.getAll()()", ex);
        }

        return list;
    }

    @Override
    public ArrayList<Product> searchByNameLike(String name) {
        ArrayList<Product> list = new ArrayList<>();

        try {
            String SQLQuery = SQL.ProductSQL.SEARCH_BY_NAME_LIKE(name);

            UserOperationLogger.logSQL(SQLQuery);

            ResultSet rs = DBUtil.directExecuteQuery(SQLQuery);

            list = RsToModel.rsToProductList(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.searchByNameLike()", ex);
        }

        return list;
    }

    @Override
    public ArrayList<Product> searchByBarcode(String barCode) {
        ArrayList<Product> list = new ArrayList<>();

        try {
            String SQLQuery = SQL.ProductSQL.SEARCH_BY_BARODE(barCode);

            UserOperationLogger.logSQL(SQLQuery);

            ResultSet rs = DBUtil.directExecuteQuery(SQLQuery);

            list = RsToModel.rsToProductList(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.searchByNameLike()", ex);
        }

        return list;
    }

}
