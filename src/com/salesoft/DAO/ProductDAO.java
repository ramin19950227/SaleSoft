/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ProductDAO - bu Melumat bazasindan melumatlari almaq ucun istifade olunur.
 * Melumat Bazasinda melumatlari alib obyekt halinda bize qaytaracaq
 *
 * @author Ramin
 */
public class ProductDAO {

    /**
     * Melumat bazasinda Product cedvelimizin adi
     */
    private static final String PRODUCT_TABLE_NAME = "product_list";

    /**
     * Bizim SQL sorgularimiz, adlarinden bilinir ne ucun istifade olunursa
     * biraz inglis dili bilmek bes eder
     */
    private static final String SQL_GET_ALL_PRODUCTS = "SELECT * FROM " + PRODUCT_TABLE_NAME;

    /**
     * Bunlarda Metodlarimiz
     */
    /**
     * getAllProductList - Bazada olan mehsullari Product modeli obyektine yigib
     * ArrayList kimi qaytarir
     */
    public static ArrayList<Product> getAllProductList() {
        Product p = null;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_GET_ALL_PRODUCTS);
            ArrayList<Product> arrayList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("ad"));
                p.setQty(rs.getInt("say"));
                p.setPurchasePrice(rs.getDouble("alishqiymeti"));
                p.setBarCode(rs.getString("barcode"));
                p.setNote(rs.getString("qeyd"));

                arrayList.add(p);

                found = true;
            }
            DatabaseConnection.close(con);
            DatabaseConnection.close(rs);
            if (found) {
                return arrayList;
            } else {
                return null; // no entires found
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "SQLException - in metod: (ArrayList<Product> getAllProductList())", ex);
            return (null);
        }
    }
}
