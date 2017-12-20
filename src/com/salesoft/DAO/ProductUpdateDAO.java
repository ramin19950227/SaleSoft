/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.MainApp;
import com.salesoft.util.MyLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;

/**
 *
 * @author Ramin
 */
public class ProductUpdateDAO {

    /**
     * Melumat bazasinda Product cedvelimizin adi
     */
    private static final String PRODUCT_TABLE_NAME = "product_list";

    /**
     * Productumuzun Bazada adini deyishmek ucun istifade olunan SQL sorgudur id
     * esasinda adi set edir yani deyishir istediyimize
     */
    private static final String SQL_UPDATE_PRODUCT_NAME_BY_ID = "UPDATE " + PRODUCT_TABLE_NAME + " SET ad=? WHERE id=?;";

    /**
     * Productumuzun Bazada Sayini deyishmek ucun istifade olunan SQL sorgudur
     * id esasinda Sayi set edir yani deyishir istediyimize
     */
    private static final String SQL_UPDATE_PRODUCT_QTY_BY_ID = "UPDATE " + PRODUCT_TABLE_NAME + " SET say=? WHERE id=?;";

    /**
     * Productumuzun Bazada Alish Qiymetini deyishmek ucun istifade olunan SQL
     * sorgudur id esasinda deyishir
     */
    private static final String SQL_UPDATE_PRODUCT_PURCHASEPRICE_BY_ID = "UPDATE " + PRODUCT_TABLE_NAME + " SET alishqiymeti=? WHERE id=?;";

    /**
     * Productumuzun Bazada Barcodunu deyishmek ucun istifade olunan SQL
     * sorgudur id esasinda deyishir
     */
    private static final String SQL_UPDATE_PRODUCT_BARCODE_BY_ID = "UPDATE " + PRODUCT_TABLE_NAME + " SET barcode=? WHERE id=?;";

    /**
     * Productumuzun Bazada Barcodunu deyishmek ucun istifade olunan SQL
     * sorgudur id esasinda deyishir
     */
    private static final String SQL_UPDATE_PRODUCT_NOTE_BY_ID = "UPDATE " + PRODUCT_TABLE_NAME + " SET qeyd=? WHERE id=?;";

    /**
     * Product-in adini deyishir. parametrde verdiyimiz ID-ile olan mehsulun
     * adini paramatrde verilen name-ni yazir evvelki ad silinir
     *
     * @param id - deyishmek istediyimiz Product-in id-si
     * @param name - Product-a vermek istediyimiz ad
     */
    public static void updateProductNameById(int id, String name) {
//        MainApp.getLogger().log(Level.SEVERE, "ProductUpdateDAO.updateProductNameById(int id, String name)  \n"
//                + "id=: " + id
//                + "name=:" + name);
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PRODUCT_NAME_BY_ID);
            ps.setString(1, name);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception ex) {
//            new MyLogger("Exception in - ProductUpdateDAO.updateProductNameById(int id, String name)").getLogger().log(Level.SEVERE, "\n"
//                    + "Parametr name=" + name + "\n"
//                    + "Parametr id=" + id + "\n", ex);//LOG++++++++++++++++++++

        }
    }

    /**
     * Product-in Sayini deyishir. parametrde verdiyimiz ID-ile olan mehsulun
     * Sayina paramatrde verilen qty-ni yazir evvelki say silinir
     *
     * @param id - deyishmek istediyimiz Product-in id-si
     * @param qty - Product-a vermek istediyimiz Say
     */
    public static void updateProductQtyById(int id, int qty) {
//        MainApp.getLogger().log(Level.SEVERE, "ProductUpdateDAO.updateProductQtyById(int id, int qty)  \n"
//                + "id=: " + id
//                + "qty=:" + qty);
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PRODUCT_QTY_BY_ID);
            ps.setInt(1, qty);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception ex) {
//            new MyLogger("Exception in - ProductUpdateDAO.updateProductQtyById(int id, int qty)").getLogger().log(Level.SEVERE, "\n"
//                    + "Parametr qty=" + qty + "\n"
//                    + "Parametr id=" + id + "\n", ex);//LOG++++++++++++++++++++

        }
    }

    /**
     * Product-in Sayini deyishir. parametrde verdiyimiz ID-ile olan mehsulun
     * Sayina paramatrde verilen qty-ni yazir evvelki say silinir
     *
     * @param id - deyishmek istediyimiz Product-in id-si
     * @param qty - Product-a vermek istediyimiz Say
     */
    public static void updateProductPurchasePriceById(int id, double purchasePrice) {
//        MainApp.getLogger().log(Level.SEVERE, "ProductUpdateDAO.updateProductPurchasePriceById(int id, double purchasePrice)  \n"
//                + "id=: " + id
//                + "purchasePrice=:" + purchasePrice);
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PRODUCT_PURCHASEPRICE_BY_ID);
            ps.setDouble(1, purchasePrice);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception ex) {
//            new MyLogger("Exception in - ProductUpdateDAO.updateProductPurchasePriceById(int id, double purchasePrice)").getLogger().log(Level.SEVERE, "\n"
//                    + "Parametr purchasePrice=" + purchasePrice + "\n"
//                    + "Parametr id=" + id + "\n", ex);//LOG++++++++++++++++++++

        }
    }

    /**
     * Product-in Barcodunu deyishir. parametrde verdiyimiz ID-ile
     *
     * @param id - deyishmek istediyimiz Product-in id-si
     * @param barCode - Product-a vermek istediyimiz Yeni BarCode
     */
    public static void updateProductBarCodeById(int id, String barCode) {
//        MainApp.getLogger().log(Level.SEVERE, "ProductUpdateDAO.updateProductBarCodeById(int id, String barCode)  \n"
//                + "id=: " + id
//                + "barCode=:" + barCode);
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PRODUCT_BARCODE_BY_ID);
            ps.setString(1, barCode);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception ex) {
//            new MyLogger("Exception in - ProductUpdateDAO.updateProductBarCodeById(int id, String barCode)").getLogger().log(Level.SEVERE, "\n"
//                    + "Parametr barCode=" + barCode + "\n"
//                    + "Parametr id=" + id + "\n", ex);//LOG++++++++++++++++++++

        }
    }

    /**
     * Product-in Barcodunu deyishir. parametrde verdiyimiz ID-ile
     *
     * @param id - deyishmek istediyimiz Product-in id-si
     * @param note
     */
    public static void updateProductNoteById(int id, String note) {
        
//        MainApp.getLogger().log(Level.SEVERE, "ProductUpdateDAO.updateProductNoteById(int id, String note)  \nid=: {0}note=:{1}", new Object[]{id, note});
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PRODUCT_NOTE_BY_ID);
            ps.setString(1, note);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception ex) {
//            new MyLogger("Exception in - ProductUpdateDAO.updateProductNoteById(int id, String note)").getLogger().log(Level.SEVERE, "\n"
//                    + "Parametr note=" + note + "\n"
//                    + "Parametr id=" + id + "\n", ex);//LOG++++++++++++++++++++

        }
    }

}
