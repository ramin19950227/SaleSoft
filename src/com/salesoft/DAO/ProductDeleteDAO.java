/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.MainApp;
import com.salesoft.util.RLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author Ramin
 */
public class ProductDeleteDAO {

    /**
     * Melumat bazasinda Product cedvelimizin adi
     */
    private static final String PRODUCT_TABLE_NAME = "product_list";

    /**
     * Productumuzun Bazada adini deyishmek ucun istifade olunan SQL sorgudur id
     * esasinda adi set edir yani deyishir istediyimize
     */
    private static final String SQL_DELETE_PRODUCT_BY_ID = "DELETE FROM " + PRODUCT_TABLE_NAME + " WHERE id=?;";

    public static void deleteProductById(int id) {
//        MainApp.getLogger().log(Level.SEVERE, "ProductDeleteDAO.deleteProductById(int id)  \nid=: {0}", id);
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_DELETE_PRODUCT_BY_ID);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException ex) {
//            new RLogger("SQLException in - ProductDeleteDAO.deleteProductById(int id)").getLogger().log(Level.SEVERE, "\n"
//                    + "Parametr id=" + id + "\n", ex);//LOG++++++++++++++++++++

        }
    }

}
