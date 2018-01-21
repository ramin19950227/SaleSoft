package com.salesoft.DAO.intf;

import com.salesoft.model.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author teyyub , Dec 24, 2017 , 9:33:14 AM
 */
public interface ProductDAOIntf {

    Product rsToProduct(ResultSet rs) throws SQLException;

    ArrayList<Product> rsToProductList(ResultSet rs) throws SQLException;

    void create(Product entity);

    void update(Product entity);

    boolean delete(Integer id);

    Product getById(Integer id);

    Product getByBarcode(String barCode);

    ArrayList<Product> getAll();

    ArrayList<Product> searchByNameLike(String name);

    ArrayList<Product> searchByBarcode(String barCode);
}
