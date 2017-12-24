package com.salesoft.DAO.impl;

import com.salesoft.DAO.DatabaseConnection;
import com.salesoft.DAO.intf.InvoiceDAOIntf;
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

public class InvoiceDAO  implements InvoiceDAOIntf{

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
    @Override
    public Invoice getInvoiceById(int id) {
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

    /**
     *
     * @return
     */
    @Override
    public   ArrayList<Invoice> getAllInvoice() {
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

    @Override
    public   ArrayList<InvoiceItem> getAllInvoiceItemListById(Integer id) {
        InvoiceItem invoiceItem;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(getAllInvoiceItemById);
            ArrayList<InvoiceItem> arrayList = new ArrayList<>();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                invoiceItem = new InvoiceItem(rs.getInt(1), rs.getString(4), rs.getInt(5), rs.getDouble(7), rs.getInt(3), rs.getString(8));

                arrayList.add(invoiceItem);

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
//            new MyLogger("ProductDAO.getAllProductList() - SQLException").getLogger().log(Level.SEVERE, "SQLException - in metod: (ArrayList<Product> getAllProductList())", ex);//LOG++++++++++++++++++++
            return (null);
        }
    }

    /**
     * Bu metod Evvel Istifade elediyimdir bunu Silecem yaxinda
     *
     * @param id
     * @param customerName
     * @deprecated
     */
    public   void updateInvoiceCustoemerNameById(Integer id, String customerName) {
//        MainApp.getLogger().log(Level.SEVERE, "InvoiceDAO.updateInvoiceCustoemerNameById(int id, String customerName)  \n"
//                + "id=: " + id
//                + "customerName=:" + customerName);
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_INVOICE_CUSTOMERNAME_BY_ID);
            ps.setString(1, customerName);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception ex) {
//            new MyLogger("Exception in - InvoiceDAO.updateInvoiceCustoemerNameById(int id, String customerName)").getLogger().log(Level.SEVERE, "\n"
//                    + "Parametr customerName=" + customerName + "\n"
//                    + "Parametr id=" + id + "\n", ex);//LOG++++++++++++++++++++

        }
    }

    public   ArrayList<Invoice> getInvoiceListByNameLike(String name) {
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

    public   void insertNewInvoice(String customer, Double mebleg) {
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

    public   void insertNewInvoiceItem(Integer history_id, CartItem c) {
        try {
            DBUtil.dbExecuteUpdate(
                    SQL.InvoiceItem.INVOICEITEM_ADD_NEW
                            .replaceAll("history_idR", history_id.toString())
                            .replaceAll("p_idR", c.getId().toString())
                            .replaceAll("p_nameR", c.getName())
                            .replaceAll("p_sayR", c.getQty().toString())
                            .replaceAll("p_qiymetR", c.getSalePrice().toString())
                            .replaceAll("p_meblegR", c.getTotalPrice().toString())
                            .replaceAll("p_barcodeR", c.getProduct().getBarCode())
                            .replaceAll("p_qeydR", "note")
                            .replaceAll("p_satishdan_evvelki_sayR", c.getProduct().getQty().toString())
            );
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public   Integer getLastIdInInvoiceTable() {
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

    public   void updateInvoiceItem(InvoiceItem invoiceItem) {
        try {
            DBUtil.dbExecuteUpdate(
                    SQL.InvoiceItem.INVOICEITEM_UPDATE_BY_ID
                            .replaceAll("p_sayR", invoiceItem.getQty().toString())
                            .replaceAll("p_meblegR", invoiceItem.getTotalPrice().toString())
                            .replaceAll("idR", invoiceItem.getId().toString())
            );
        } catch (SQLException ex) {
            System.out.println("com.salesoft.DAO.InvoiceDAO.updateInvoiceItemQtyById()");
            System.err.println("SQLException");
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * BU metod Invoice - Obyetinin ozunu yenileyir ID-esasinda, id-ni de hele
     * obyektden alir
     *
     * @param invoice - yenileyeceyimiz Invoice-obyekti hele hemin obyektin
     * ID-sini alib onunesasinda yenileyir
     * @see
     *
     */
    public   void updateInvoice(Invoice invoice) {

        try {
            //`customer`='customerR', `mebleg`='meblegR', `odenish`='', `qaliq`='' WHERE  `id`=idR;";
            DBUtil.dbExecuteUpdate(
                    SQL.Invoice.INVOICE_UPDATE_BY_ID
                            .replaceAll("customerR", invoice.getCustomerName())
                            .replaceAll("meblegR", invoice.getTotalPrice().toString())
                            .replaceAll("idR", invoice.getId().toString())
            );
        } catch (SQLException ex) {
            System.out.println("com.salesoft.DAO.InvoiceDAO.updateInvoice()");
            System.err.println("SQLException");
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
