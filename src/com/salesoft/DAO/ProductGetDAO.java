/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.Product;
import com.salesoft.util.RsToModel;
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
public class ProductGetDAO {

    /**
     * getAllProductList - Bazada olan mehsullari Product modeli obyektine yigib
     * ArrayList olaraq qaytarir
     *
     * @return arrayList
     */
    public static ArrayList<Product> getAllProductList() {
        try {
            return RsToModel.rsToProductList(DBUtil.directExecuteQuery(SQL.Product.PRODUCT_GET_ALL));
        } catch (SQLException ex) {
            System.out.println("SQLException");
            System.out.println("com.salesoft.DAO.ProductGetDAO.getAllProductList()");
            Logger.getLogger(ProductGetDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * getAllProductListByNameLike - Bazada axtarish kecirir ve gonderilen
     * parametre uygun butun setirleri (mehsullari) Product modeli obyektine
     * yigib ArrayList kimi qaytarir
     *
     * @param name
     * @return arrayList
     */
    public static ArrayList<Product> getAllProductListByNameLike(String name) {
//        MainApp.getLogger().info("SEARCH By Name: " + name);//LOG++++++++++++++++++++
        Product p = null;
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL.Product.PRODUCT_GEL_ALL_BY_NAME_LIKE_P);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            ArrayList<Product> arrayList = new ArrayList<>();

            boolean found = false;
            while (rs.next()) {
                p = new Product();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setQty(rs.getInt(3));
                p.setPurchasePrice(rs.getDouble(4));
                p.setBarCode(rs.getString(5));
                p.setNote(rs.getString(6));

                arrayList.add(p);

                found = true;
            }
            DatabaseConnection.close(con);
            DatabaseConnection.close(rs);
            if (found) {
//                MainApp.getLogger().info("\nSEARCH result size:" + arrayList.size() + "\n");//LOG++++++++++++++++++++
//                for (Product product : arrayList) {
////                    MainApp.getLogger().info("SEARCH items:" + "\n ");//LOG++++++++++++++++++++
////                    MainApp.getLogger().info(product.getId() + "\n");//LOG++++++++++++++++++++
////                    MainApp.getLogger().info(product.getName() + "\n");//LOG++++++++++++++++++++
//                }
                return arrayList;
            } else {
                return null; // no entires found
            }
        } catch (SQLException ex) {
//            new MyLogger("ProductDAO.getAllProductListByNameLike(String name) - SQLException").getLogger().log(Level.SEVERE, "SQLException - Search word(name)=:" + name, ex);//LOG++++++++++++++++++++
            return (null);
        }
    }

    /**
     * getAllProductListByNameLike - Bazada axtarish kecirir ve gonderilen
     * parametre uygun butun setirleri (mehsullari) Product modeli obyektine
     * yigib ArrayList kimi qaytarir
     *
     * @param name
     * @return arrayList
     */
    public static ArrayList<Product> getAllProductListByBarCode(String barCode) {
//        MainApp.getLogger().info("SEARCH By barCode: " + barCode);//LOG++++++++++++++++++++
        Product p = null;
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL.Product.PRODUCT_GEL_ALL_BY_BARCODE_P);
            ps.setString(1, barCode);
            ResultSet rs = ps.executeQuery();

            ArrayList<Product> arrayList = new ArrayList<>();

            boolean found = false;
            while (rs.next()) {
                p = new Product();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setQty(rs.getInt(3));
                p.setPurchasePrice(rs.getDouble(4));
                p.setBarCode(rs.getString(5));
                p.setNote(rs.getString(6));

                arrayList.add(p);

                found = true;
            }
            DatabaseConnection.close(con);
            DatabaseConnection.close(rs);
            if (found) {
//                MainApp.getLogger().info("\n FIND result size:" + arrayList.size() + "\n");//LOG++++++++++++++++++++
//                for (Product product : arrayList) {
//                    MainApp.getLogger().info("FIND items:" + "\n ");//LOG++++++++++++++++++++
//                    MainApp.getLogger().info(product.getId() + "\n");//LOG++++++++++++++++++++
//                    MainApp.getLogger().info(product.getName() + "\n");//LOG++++++++++++++++++++
//                }
                return arrayList;
            } else {
                return null; // no entires found
            }
        } catch (SQLException ex) {
//            new MyLogger("ProductDAO.getAllProductListByBarCode(String BarCode) - SQLException").getLogger().log(Level.SEVERE, "SQLException - Search word(BarCode)=:" + barCode, ex);//LOG++++++++++++++++++++
            return (null);
        }
    }

    /**
     * BU metod verilen id ile Product-i qaytarir
     *
     * @param id
     * @return
     */
    public static Product getProductById(int id) {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL.Product.PRODUCT_GET_BY_ID_P);
            ps.setInt(1, id);
            //ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();

            return RsToModel.rsToProduct(rs);
        } catch (SQLException ex) {
//            new MyLogger("SQLException in - ProductGetDAO.getProductById(int id)").getLogger().log(Level.SEVERE, "SQLException - id=:" + id, ex);//LOG++++++++++++++++++++
            return (null);
        }
    }

    public static Product getProductByBarCode(String barCode) {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL.Product.PRODUCT_GET_BY_BARCODE_P);
            ps.setString(1, barCode);

            ResultSet rs = ps.executeQuery();

            return RsToModel.rsToProduct(rs);
        } catch (SQLException ex) {
//            new MyLogger("SQLException in - ProductGetDAO.getProductByBarCode(String barCode)").getLogger().log(Level.SEVERE, "SQLException - barCode=:" + barCode, ex);//LOG++++++++++++++++++++
            return (null);
        }
    }
}
