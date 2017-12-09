/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.model.CartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramin
 */
public class ProductNewDAO {

    /**
     * Melumat bazasinda Product cedvelimizin adi
     */
    private static final String PRODUCT_TABLE_NAME = "product_list";

    /**
     * Productumuzun Bazada adini deyishmek ucun istifade olunan SQL sorgudur id
     * esasinda adi set edir yani deyishir istediyimize
     */
    private static final String SQL_INSERT_PRODUCT = "INSERT INTO " + PRODUCT_TABLE_NAME + " (ad, say, alishqiymeti, barcode, qeyd) VALUES (?, ?, ?, ?, ?)";

    public static void createNewProduct(String name, Integer qty, Double purchasePrice, String barCode, String note) {

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_INSERT_PRODUCT);
            ps.setString(1, name);
            ps.setInt(2, qty);
            ps.setDouble(3, purchasePrice);

            ps.setString(4, barCode);

            ps.setString(5, note);

            ps.executeUpdate();

            DatabaseConnection.close(con);
            DatabaseConnection.close(ps);

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
