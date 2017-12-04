package com.salesoft.DAO;

import com.salesoft.MainApp;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import com.salesoft.model.Product;
import com.salesoft.util.MyLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceDAO {

    private static final String getInvoiceById = "SELECT * FROM satish_history WHERE id=?";
    private static final String getAllInvoice = "SELECT * FROM satish_history ORDER BY `id` DESC"; //yeniler yuxarida
    private static final String getAllInvoiceItemById = "SELECT * FROM satish_list WHERE history_id=? ORDER BY `id` DESC";

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
        InvoiceItem ii = null;
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

}
