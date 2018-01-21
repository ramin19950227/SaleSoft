package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.ProductDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.model.Product;
import com.salesoft.util.ExceptionShowDialog;
import com.salesoft.util.MyExceptionLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ramin
 */
public class ProductDAO implements ProductDAOIntf {

    //<editor-fold defaultstate="collapsed" desc="rsToProduct">
    @Override
    public Product rsToProduct(ResultSet rs) throws SQLException {
        try {
            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("qty"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("salePrice"),
                        rs.getString("barCode"),
                        rs.getString("note"));
                return product;
            }
            return null; //boshdur

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.AllDisconnect();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="rsToProductList">
    @Override
    public ArrayList<Product> rsToProductList(ResultSet rs) throws SQLException {
        try {
            ArrayList<Product> list = new ArrayList<>();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("qty"),
                        rs.getDouble("purchasePrice"),
                        rs.getDouble("salePrice"),
                        rs.getString("barCode"),
                        rs.getString("note"));

                list.add(product);
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
    public void create(Product product) {
        try {
//            String SQLQuery = SQL.ProductSQL.CREATE(entity);
//            UserOperationLogger.logSQL(SQLQuery);
//            DBUtil.mySQLExecuteUpdate(SQLQuery);

            DBUtil.msAccessExecuteUpdate("INSERT INTO Product (name, qty, purchasePrice, salePrice, barCode, note) "
                    + "VALUES ('" + product.getName() + "',"
                    + " '" + product.getQty().toString() + "',"
                    + " '" + product.getPurchasePrice().toString() + "',"
                    + " '" + product.getSalePrice().toString() + "',"
                    + " '" + product.getBarCode() + "',"
                    + " '" + product.getNote() + "')");

//            return true;
        } catch (SQLException ex) {
            new ExceptionShowDialog(ex).showAndWait();
            MyExceptionLogger.logException("SQLException - ProductDAO.create()", ex);
//            return false;
        }

    }

    @Override
    public void update(Product product) {
        try {
//            String SQLQuery = SQL.ProductSQL.UPDATE(entity);
////            UserOperationLogger.logSQL(SQLQuery);
//            DBUtil.mySQLExecuteUpdate(SQLQuery);

            DBUtil.msAccessExecuteUpdate("UPDATE Product SET "
                    + "`name`='" + product.getName() + "', "
                    + "`qty`='" + product.getQty().toString() + "', "
                    + "`purchasePrice`='" + product.getPurchasePrice().toString() + "', "
                    + "`salePrice`='" + product.getSalePrice().toString() + "', "
                    + "`barCode`='" + product.getBarCode() + "', "
                    + "`note`='" + product.getNote() + "' "
                    + "WHERE  `id`=" + product.getId() + ";");

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.update()", ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
//            String SQLQuery = SQL.ProductSQL.DELETE(id);
//            UserOperationLogger.logSQL(SQLQuery);
//            DBUtil.mySQLExecuteUpdate(SQLQuery);

            DBUtil.msAccessExecuteUpdate("DELETE FROM Product WHERE id=" + id);

            return true;

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.delete()", ex);
            return false;
        }

    }

    @Override
    public Product getById(Integer id) {
        try {
//            String SQLQuery = SQL.ProductSQL.GET(id);
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);

            ResultSet rs = DBUtil.msAccessExecuteQuery("SELECT * FROM Product WHERE id=" + id);

            return rsToProduct(rs);

        } catch (SQLException ex) {
            System.out.println("SQLException -  ProductDAO.getById(): " + ex);
            MyExceptionLogger.logException("SQLException - ProductDAO.getById()", ex);
        }
        return null;
    }

    @Override
    public Product getByBarcode(String barCode) {
        try {
//            String SQLQuery = SQL.ProductSQL.GET(barCode);
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);

            ResultSet rs = DBUtil.msAccessExecuteQuery("SELECT * FROM Product WHERE barCode='" + barCode + "'");

            return rsToProduct(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.getByBarcode()", ex);
        }
        return null;
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();

        try {
//            String SQLQuery = SQL.ProductSQL.GET_ALL();
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);

            ResultSet rs = DBUtil.msAccessExecuteQuery("SELECT * FROM Product ORDER BY `id` DESC");

            return rsToProductList(rs);

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
//            String SQLQuery = SQL.ProductSQL.SEARCH_BY_NAME_LIKE(name);
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);

            ResultSet rs = DBUtil.msAccessExecuteQuery("SELECT * FROM Product WHERE name LIKE '%" + name + "%'");

            return rsToProductList(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.searchByNameLike()", ex);
        }

        return list;
    }

    @Override
    public ArrayList<Product> searchByBarcode(String barCode) {
        ArrayList<Product> list = new ArrayList<>();

        try {
//            String SQLQuery = SQL.ProductSQL.SEARCH_BY_BARODE(barCode);
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);

            ResultSet rs = DBUtil.msAccessExecuteQuery("SELECT * FROM Product WHERE barCode='" + barCode + "'");

            return rsToProductList(rs);

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - ProductDAO.searchByNameLike()", ex);
        }

        return list;
    }

}
