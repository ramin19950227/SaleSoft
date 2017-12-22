/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.DAO.DatabaseConnection;
import com.salesoft.database.DBUtil;
import com.salesoft.model.Product;
import com.salesoft.model.PurchaseProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class-da Yerleshen Metodlara ResultSet obyektini veririk ve Metodumuza Uygun
 * Model ve ya Model Listini aliriq
 *
 * @author Ramin
 */
public class RsToModel {

    public static ArrayList<PurchaseProduct> rsToPurchaseProductList(ResultSet rs) throws SQLException {
        ArrayList<PurchaseProduct> list = new ArrayList<>();

        try {
            while (rs.next()) {

                Integer id = rs.getInt(1);
                String purchaseDate = rs.getString(2);
                Double totalPrice = rs.getDouble(3);

                // mence Bele daha Sade Olacaq ve rahat Oxunacaq
                Product product = new Product();
                product.setId(rs.getInt(4));
                product.setName(rs.getString(5));
                product.setQty(rs.getInt(6));
                product.setPurchasePrice(rs.getDouble(7));
                product.setBarCode(rs.getString(8));
                product.setNote(rs.getString(9));

                PurchaseProduct pp = new PurchaseProduct(id, purchaseDate, totalPrice, product);
                list.add(pp);
            }
            return list;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.allDisconnect();
        }
    }

    /**
     * Bu Esas Obyektdir ve Bahqa Obyektlerin icinde olmayacagina gore Parametr
     * olaraq sadece RS verilir ve startPoint-e ehtiyyac yoxdur
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static PurchaseProduct rsToPurchaseProduct(ResultSet rs) throws SQLException {
        try {
            if (rs.next()) {
                Integer id = rs.getInt(1);
                String purchaseDate = rs.getString(2);
                Double totalPrice = rs.getDouble(3);
                // mence Bele daha Sade Olacaq ve rahat Oxunacaq
                Product product = new Product();
                product.setId(rs.getInt(4));
                product.setName(rs.getString(5));
                product.setQty(rs.getInt(6));
                product.setPurchasePrice(rs.getDouble(7));
                product.setBarCode(rs.getString(8));
                product.setNote(rs.getString(9));

                PurchaseProduct pp = new PurchaseProduct(id, purchaseDate, totalPrice, product);
                return pp;
            }
            return null;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.allDisconnect();
        }
    }

    /**
     * Bu metoda ResultSet Tipli obyekt veririk ve neticede eger rs bosh deyilse
     * yani RS-de melumat varsa Poduct tipli ArrayList qaytarir.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static ArrayList<Product> rsToProductList(ResultSet rs) throws SQLException {
        try {
            ArrayList<Product> list = new ArrayList<>();

            //budur dogru metod bir dene next() etdikse Melumatlari almaliyiq 2-ci next etmemishden evvel
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setQty(rs.getInt(3));
                p.setPurchasePrice(rs.getDouble(4));
                p.setBarCode(rs.getString(5));
                p.setNote(rs.getString(6));

                list.add(p);
            }
            return list;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.allDisconnect();
        }
    }

    /**
     * Bu metoda ResultSet Tipli obyekt veririk ve neticede eger rs bosh deyilse
     * yani RS-de melumat varsa Poduct tipli obyekt qaytarir
     *
     * @param rs ResultSet SQL sorgu neticesinde elde edilen obyekt
     * @return Product - tipli obyekt qaytarir eger rs bosh deyilse, null - eger
     * Exception olubsa ve ya RS boshdursa (netice yoxdursa)
     * @throws java.sql.SQLException
     */
    public static Product rsToProduct(ResultSet rs) throws SQLException {
        try {
            if (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setQty(rs.getInt(3));
                p.setPurchasePrice(rs.getDouble(4));
                p.setBarCode(rs.getString(5));
                p.setNote(rs.getString(6));
                return p;
            }
            return null; //boshdur

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.allDisconnect();
        }
    }

}
