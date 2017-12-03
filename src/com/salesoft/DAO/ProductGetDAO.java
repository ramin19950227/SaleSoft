/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.MainApp;
import com.salesoft.model.Product;
import com.salesoft.util.MyLogger;
import com.salesoft.util.PStoProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * ProductDAO - bu Melumat bazasindan melumatlari almaq ucun istifade olunur.
 * Melumat Bazasinda melumatlari alib obyekt halinda bize qaytaracaq
 *
 * @author Ramin
 */
public class ProductGetDAO {

    /**
     * Melumat bazasinda Product cedvelimizin adi
     */
    private static final String PRODUCT_TABLE_NAME = "product_list";

    /**
     * SQL sorgularimiz, adlarindan bilinir ne ucun istifade olunursa biraz
     * inglis dili bilmek bes eder
     */
    private static final String SQL_GET_ALL_PRODUCTS = "SELECT * FROM " + PRODUCT_TABLE_NAME;
    private static final String SQL_GET_PRODUCT_BY_ID = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE id=?";
    private static final String SQL_GET_PRODUCT_BY_BARCODE = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE barcode=?";

    private static final String SQL_GEL_ALL_PRODUCTS_BY_NAME_LIKE = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE ad LIKE ?";
    private static final String SQL_GEL_ALL_PRODUCTS_BY_BARCODE = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE barcode=?";

    /**
     * getAllProductList - Bazada olan mehsullari Product modeli obyektine yigib
     * ArrayList kimi qaytarir
     *
     * @return arrayList
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
                return arrayList;
            } else {
                return null; // no entires found
            }
        } catch (SQLException ex) {
            new MyLogger("ProductDAO.getAllProductList() - SQLException").getLogger().log(Level.SEVERE, "SQLException - in metod: (ArrayList<Product> getAllProductList())", ex);//LOG++++++++++++++++++++
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
    public static ArrayList<Product> getAllProductListByNameLike(String name) {
        MainApp.getLogger().info("SEARCH By Name: " + name);//LOG++++++++++++++++++++
        Product p = null;
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_GEL_ALL_PRODUCTS_BY_NAME_LIKE);
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
                MainApp.getLogger().info("\nSEARCH result size:" + arrayList.size() + "\n");//LOG++++++++++++++++++++
                for (Product product : arrayList) {
                    MainApp.getLogger().info("SEARCH items:" + "\n ");//LOG++++++++++++++++++++
                    MainApp.getLogger().info(product.getId() + "\n");//LOG++++++++++++++++++++
                    MainApp.getLogger().info(product.getName() + "\n");//LOG++++++++++++++++++++
                }
                return arrayList;
            } else {
                return null; // no entires found
            }
        } catch (SQLException ex) {
            new MyLogger("ProductDAO.getAllProductListByNameLike(String name) - SQLException").getLogger().log(Level.SEVERE, "SQLException - Search word(name)=:" + name, ex);//LOG++++++++++++++++++++
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
        MainApp.getLogger().info("SEARCH By barCode: " + barCode);//LOG++++++++++++++++++++
        Product p = null;
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_GEL_ALL_PRODUCTS_BY_BARCODE);
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
                MainApp.getLogger().info("\n FIND result size:" + arrayList.size() + "\n");//LOG++++++++++++++++++++
                for (Product product : arrayList) {
                    MainApp.getLogger().info("FIND items:" + "\n ");//LOG++++++++++++++++++++
                    MainApp.getLogger().info(product.getId() + "\n");//LOG++++++++++++++++++++
                    MainApp.getLogger().info(product.getName() + "\n");//LOG++++++++++++++++++++
                }
                return arrayList;
            } else {
                return null; // no entires found
            }
        } catch (SQLException ex) {
            new MyLogger("ProductDAO.getAllProductListByBarCode(String BarCode) - SQLException").getLogger().log(Level.SEVERE, "SQLException - Search word(BarCode)=:" + barCode, ex);//LOG++++++++++++++++++++
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
            PreparedStatement ps = con.prepareStatement(SQL_GET_PRODUCT_BY_ID);
            ps.setInt(1, id);
            //ps.setObject(1, id);

            return PStoProduct.getProduct(ps, con);
        } catch (SQLException ex) {
            new MyLogger("SQLException in - ProductGetDAO.getProductById(int id)").getLogger().log(Level.SEVERE, "SQLException - id=:" + id, ex);//LOG++++++++++++++++++++
            return (null);
        }
    }

    public static Product getProductByBarCode(String barCode) {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_GET_PRODUCT_BY_BARCODE);
            ps.setString(1, barCode);

            return PStoProduct.getProduct(ps, con);
        } catch (SQLException ex) {
            new MyLogger("SQLException in - ProductGetDAO.getProductByBarCode(String barCode)").getLogger().log(Level.SEVERE, "SQLException - barCode=:" + barCode, ex);//LOG++++++++++++++++++++
            return (null);
        }
    }

}
