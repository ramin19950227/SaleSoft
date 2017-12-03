/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.DAO.DatabaseConnection;
import com.salesoft.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Bu Obyekt Parametrine verilen ResultSeti alib Product a set edir ve Product
 * qaytarir
 *
 * @author Ramin
 */
public class PStoProduct {

    public static Product getProduct(PreparedStatement ps, Connection con) {
        try {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setQty(rs.getInt(3));
                p.setPurchasePrice(rs.getDouble(4));
                p.setBarCode(rs.getString(5));
                p.setNote(rs.getString(6));

                DatabaseConnection.close(con);
                DatabaseConnection.close(rs);
                DatabaseConnection.close(ps);
                return p;
            } else {
                return null; // no entires found
            }
        } catch (SQLException ex) {
            new MyLogger("SQLException in - com.salesof.util.PStoProduct").getLogger().log(Level.SEVERE, null, ex);
            return (null);
        }
    }
}
