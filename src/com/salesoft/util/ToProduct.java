/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.DAO.DatabaseConnection;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bu Class-da Product mehsulunu ve Mehsul Listini elde etmek ucun lazimi
 * metodlar var. qaytarir
 *
 * @author Ramin
 */
public class ToProduct {

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

            // bu yontem dogru deyil cunki burda bir defe rs.next() edirsen
            // sonra rsToProduct(rs){ bunun icindede rs.next() edirsen buda o demekdirdki
            // cedvelde 14 eded setirimiz var dise onlarin yarisi itecek 1,3,5 itecek 2,4,6 gelecek bize
//            while (rs.next()) {
//                Product p = ToProduct.rsToProduct(rs);
//                list.add(p);
//            }
            return list;
        } catch (SQLException ex) {
            System.err.println("SQLException");
            System.out.println("com.salesoft.util.ToProduct.rsToProductList()");

            Logger.getLogger(ToProduct.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            //Rs-ile bagli problem CIXIB Rs-ile ishimz bitmeden rs.close() olurdu
            //indi ise rs ile ishimizi btirib listimizi aliriq sonra ise baglaya bilerik
            //indi baglaya bilerik
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
            System.err.println("SQLException");
            System.out.println("com.salesoft.util.ToProduct.rsToProduct()");
            ex.getLocalizedMessage();

            Logger.getLogger(ToProduct.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            //Rs-ile bagli problem CIXIB Rs-ile ishimz bitmeden rs.close() olurdu
            // sebeb dbExecuteQuery biten kimi final olaraq rs-i baglayirdi
            //indi ise rs ile ishimizi btirib listimizi aliriq sonra ise ozumuz Baglayiriq
            //indi baglaya bilerik, cunki listimizi aldiq rs-den ve baglayaq
            DBUtil.allDisconnect();
        }
    }

    /**
     *
     * @param ps
     * @param con
     * @return
     * @deprecated Bu Metod Artiq istifadeden qaldirilib Cunki Bu proqramin yeni
     * versiyasinda PreparedStatement ve Connection obyektleri ile ishim
     * olmayacaq her defe onlari yaratmaga ve baglamagada ehtiyyac yoxdur DBUtil
     * bu ishlerii gorecek
     */
    public static Product getProduct(PreparedStatement ps, Connection con) {
        try {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setQty(rs.getInt(3));
                p.setPurchasePrice(rs.getDouble(4));
                p.setBarCode(rs.getString(5));
                p.setNote(rs.getString(6));

                DatabaseConnection.close(con);
                DatabaseConnection.close(rs);
                DatabaseConnection.close(ps);
                return p;
            } else {
                return null; // no entires found
            }
        } catch (SQLException ex) {
//            new RLogger("SQLException in - com.salesof.util.PStoProduct").getLogger().log(Level.SEVERE, null, ex);
            return (null);
        }
    }

}
