package com.salesoft.DAO;

import com.salesoft.MainApp;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.CartItem;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import com.salesoft.util.MyLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceDAO {

    private static final String PRODUCT_TABLE_NAME = "satish_history";

    private static final String getInvoiceById = "SELECT * FROM satish_history WHERE id=?";
    private static final String getAllInvoice = "SELECT * FROM satish_history ORDER BY `id` DESC"; //yeniler yuxarida
    private static final String getAllInvoiceItemById = "SELECT * FROM satish_list WHERE history_id=? ORDER BY `id` DESC";
    private static final String SQL_UPDATE_INVOICE_CUSTOMERNAME_BY_ID = "UPDATE " + PRODUCT_TABLE_NAME + " SET customer=? WHERE id=?;";
    private static final String SQL_GEL_ALL_INVOICE_BY_NAME_LIKE = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE customer LIKE ?";

    /**
     *
     * @param id id ile history den melumatari alacaq ve invoice obyektini qurur
     * sonra Satish_list den satish melumatlarini alib add edecek bir bir liste
     * sonra onuda add edecek invoice obyektine Sonra Qaytaracaq
     * @return Invoice - obyekti qaytarir incinde propertilerile ve InvoiceItem
     * lerle birge
     */
    public static Invoice getInvoiceById(int id) {
        Invoice invoice = null;
        try {
            ArrayList<InvoiceItem> list = getAllInvoiceItemListById(id);

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(getInvoiceById);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                invoice = new Invoice(rs.getInt(1), rs.getString(6), rs.getDouble(3), rs.getString(2), list);

            }

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoice;
    }

    public static ArrayList<Invoice> getAllInvoice() {
        ArrayList<Invoice> list = new ArrayList<>();

        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(getAllInvoice);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Invoice(rs.getInt(1), rs.getString(6), rs.getDouble(3), rs.getString(2)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public static ArrayList<InvoiceItem> getAllInvoiceItemListById(int id) {
        InvoiceItem ii;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(getAllInvoiceItemById);
            ArrayList<InvoiceItem> arrayList = new ArrayList<>();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                ii = new InvoiceItem(rs.getInt(1), rs.getString(4), rs.getInt(5), rs.getDouble(7));

                arrayList.add(ii);

                found = true;
            }
            DatabaseConnection.close(con);
            DatabaseConnection.close(rs);
            DatabaseConnection.close(ps);
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

    public static void updateInvoiceCustoemerNameById(Integer id, String customerName) {
        MainApp.getLogger().log(Level.SEVERE, "InvoiceDAO.updateInvoiceCustoemerNameById(int id, String customerName)  \n"
                + "id=: " + id
                + "customerName=:" + customerName);
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_INVOICE_CUSTOMERNAME_BY_ID);
            ps.setString(1, customerName);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception ex) {
            new MyLogger("Exception in - InvoiceDAO.updateInvoiceCustoemerNameById(int id, String customerName)").getLogger().log(Level.SEVERE, "\n"
                    + "Parametr customerName=" + customerName + "\n"
                    + "Parametr id=" + id + "\n", ex);//LOG++++++++++++++++++++

        }
    }

    public static ArrayList<Invoice> getInvoiceListByNameLike(String name) {
        ArrayList<Invoice> list = new ArrayList<>();
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_GEL_ALL_INVOICE_BY_NAME_LIKE);
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Invoice(rs.getInt(1), rs.getString(6), rs.getDouble(3), rs.getString(2)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void insertNewInvoice(String customer, Double mebleg) {
        try {
            DBUtil.dbExecuteUpdate(
                    SQL.Invoice.INVOICE_ADD_NEW.
                            replaceAll("customerR", customer).
                            replaceAll("meblegR", mebleg.toString())
            );
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertNewInvoiceItem(Integer history_id, CartItem c) {
        try {
            DBUtil.dbExecuteUpdate(
                    SQL.InvoiceItem.INVOICEITEM_ADD_NEW
                            .replaceAll("history_id", history_id.toString())
                            .replaceAll("p_id", c.getId().toString())
                            .replaceAll("p_name", c.getName())
                            .replaceAll("p_say", c.getQty().toString())
                            .replaceAll("p_qiymet", c.getSalePrice().toString())
                            .replaceAll("p_mebleg", c.getTotalPrice().toString())
                            .replaceAll("p_barcode", c.getProduct().getBarCode())
                            .replaceAll("p_qeyd", c.getProduct().getNote())
                            .replaceAll("p_satishdan_evvelki_say", c.getProduct().getQty().toString())
            );
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Integer getLastIdInInvoiceTable() {
        try {
            ResultSet rs = DBUtil.dbExecuteQuery(SQL.Invoice.INVOICE_GET_LAST_ID);
            if (rs.next()) {
                return rs.getInt("MAX(id)");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void updateInvoiceItem(InvoiceItem invoiceItem) {
        try {
            DBUtil.dbExecuteUpdate(
                    SQL.InvoiceItem.INVOICEITEM_UPDATE_BY_ID
                            .replaceAll("p_sayR", invoiceItem.getQty().toString())
                            .replaceAll("p_meblegR", invoiceItem.getTotalPrice().toString())
                            .replaceAll("id=idR", invoiceItem.getId().toString())
            );
        } catch (SQLException ex) {
            System.out.println("com.salesoft.DAO.InvoiceDAO.updateInvoiceItemQtyById()");
            System.err.println("SQLException");
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
