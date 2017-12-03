package com.salesoft.DAO;

import com.salesoft.model.CartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryDAO {

    private static final String HISTORY_TABLE_NAME = "satish_history";
    private static final String HISTORY_SATISH_LIST_TABLE_NAME = "satish_list";

    private static final String SQL_START_HISTORY = "INSERT INTO " + HISTORY_TABLE_NAME + " (customer,mebleg,odenish,qaliq) VALUES (?, ?, ?, ?)";
    private static final String SQL_ADD_HISTORY_TO_SATISH_LIST = "INSERT INTO " + HISTORY_SATISH_LIST_TABLE_NAME + " (history_id,p_id,p_name,p_say,p_qiymet,p_mebleg,p_barcode,p_qeyd,p_satishdan_evvelki_say) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_LAST_ID_IN_HISTORY_TABLE = "SELECT MAX(id) FROM satish_history LIMIT 1";

    public static int getLastIdInHISTORY_TABLE() {
        int result = 0;

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_GET_LAST_ID_IN_HISTORY_TABLE);

            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                result = rs.getInt("MAX(id)");
                found = true;
            }
            DatabaseConnection.close(con);
            DatabaseConnection.close(rs);
            DatabaseConnection.close(ps);
            if (found) {
                return result;
            } else {
                return -1; // no entires found
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return (-2);
        }
    }

    public static int startSatishHistoryAndGetHistoryId(
            String customer,
            Double mebleg) {

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_START_HISTORY);
            ps.setString(1, customer);
            ps.setDouble(2, mebleg);
            ps.setDouble(3, 0.0);
            ps.setDouble(4, 0.0);

            ps.executeUpdate();
            DatabaseConnection.close(con);
            DatabaseConnection.close(ps);
        } catch (SQLException ex) {
        }

        // TODO Auto-generated method stub
        return getLastIdInHISTORY_TABLE();
    }

    public static void insertSaleDetailsIntoSATISH_LIST(Integer history_id, CartItem c) {
        int p_id = c.getId();
        String p_name = c.getName();
        int p_say = c.getQty();
        double p_qiymet = c.getSalePrice();
        double p_mebleg = c.getTotalPrice();
        String p_barcode = c.getProduct().getBarCode();
        String p_qeyd = c.getProduct().getNote();
        int p_satishdan_evvelki_say = c.getProduct().getQty();

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_ADD_HISTORY_TO_SATISH_LIST);
            ps.setInt(1, history_id);
            ps.setInt(2, p_id);
            ps.setString(3, p_name);
            ps.setInt(4, p_say);
            ps.setDouble(5, p_qiymet);
            ps.setDouble(6, p_mebleg);
            ps.setString(7, p_barcode);
            ps.setString(8, p_qeyd);
            ps.setInt(9, p_satishdan_evvelki_say);

            ps.executeUpdate();
            DatabaseConnection.close(con);
            DatabaseConnection.close(ps);
        } catch (SQLException ex) {
        }

    }

}
