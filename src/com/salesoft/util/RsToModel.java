/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.database.DBUtil;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import com.salesoft.model.Product;
import com.salesoft.model.ProductImportWrapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class-da Yerleshen Metodlara ResultSet obyektini veririk ve Metodumuza Uygun
 * Model ve ya Model Listini aliriq
 *
 * @author Ramin
 */
public class RsToModel {

    public static ArrayList<ProductImportWrapper> rsToPurchaseProductList(ResultSet rs) throws SQLException {
        ArrayList<ProductImportWrapper> list = new ArrayList<>();

        try {
            while (rs.next()) {

                Integer id = rs.getInt(1);
                Date uDate = new Date(rs.getDate(2).getTime());
                Double totalPrice = rs.getDouble(3);

                // mence Bele daha Sade Olacaq ve rahat Oxunacaq
                Product product = new Product();
                product.setId(rs.getInt(4));
                product.setName(rs.getString(5));
                product.setQty(rs.getInt(6));
                product.setPurchasePrice(rs.getDouble(7));
                product.setBarCode(rs.getString(8));
                product.setNote(rs.getString(9));

                ProductImportWrapper pp = new ProductImportWrapper(id, uDate, totalPrice, product);
                list.add(pp);
            }
            return list;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.AllDisconnect();
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
    public static ProductImportWrapper rsToPurchaseProduct(ResultSet rs) throws SQLException {
        try {
            if (rs.next()) {
                Integer id = rs.getInt(1);
                Date date = new Date(rs.getDate(2).getTime());
                Double totalPrice = rs.getDouble(3);

                //Product-i aliriq
                Product product = new Product();
                product.setId(rs.getInt(4));
                product.setName(rs.getString(5));
                product.setQty(rs.getInt(6));
                product.setPurchasePrice(rs.getDouble(7));
                product.setBarCode(rs.getString(8));
                product.setNote(rs.getString(9));

                ProductImportWrapper pp = new ProductImportWrapper(id, date, totalPrice, product);
                return pp;
            }
            return null;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.AllDisconnect();
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
            DBUtil.AllDisconnect();
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
            DBUtil.AllDisconnect();
        }
    }

    public static InvoiceItem rsToInvoiceItem(ResultSet rs) throws SQLException {
        try {

            Product p = new Product();
            p.setId(rs.getInt(4));
            p.setName(rs.getString(5));
            p.setQty(rs.getInt(6));
            p.setPurchasePrice(rs.getDouble(7));
            p.setBarCode(rs.getString(8));
            p.setNote(rs.getString(9));

            InvoiceItem item = new InvoiceItem(rs.getInt(1), rs.getInt(2), rs.getDouble(3), p);
            return item;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.AllDisconnect();
        }
    }

    public static ArrayList<InvoiceItem> rsToInvoiceItemList(ResultSet rs) throws SQLException {
        try {
            ArrayList<InvoiceItem> list = new ArrayList<>();

            //budur dogru metod bir dene next() etdikse Melumatlari almaliyiq 2-ci next etmemishden evvel
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(4));
                p.setName(rs.getString(5));
                p.setQty(rs.getInt(6));
                p.setPurchasePrice(rs.getDouble(7));
                p.setBarCode(rs.getString(8));
                p.setNote(rs.getString(9));

                InvoiceItem item = new InvoiceItem(rs.getInt(1), rs.getInt(2), rs.getDouble(3), p);

                list.add(item);
            }
            return list;

        } catch (SQLException ex) {
            throw ex;

        } finally {
            DBUtil.AllDisconnect();
        }
    }

    // heleki qalsin sonra davam edecem inshaAllah
//    public static Invoice rsToInvoice(ResultSet rs) throws SQLException {
//        try {
//            if (rs.next()) {
//                Invoice invoice = new Invoice(rs.getInt(1), rs.getString(2), rs.getDouble(3), );
//
//                InvoiceItem item = new InvoiceItem(rs.getInt(1), rs.getInt(2), rs.getDouble(3), p);
//                return item;
//            }
//            return null; //boshdur
//
//        } catch (SQLException ex) {
//            throw ex;
//
//        } finally {
//            DBUtil.AllDisconnect();
//        }
//    }
}
