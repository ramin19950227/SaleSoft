package com.salesoft.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bu Class-i Biz Melumat bazamizla elaqe qurmaq ucun istifade edeceyik burada
 * baza ile elaqe ucun lazim olan melumatlar var, irelide ola bilsin bunlari
 * proqramnan daxil elemek imkanida oldu yani proqrami acanda birinci acilish
 * zamani settings bolumu olar orada lazim olan melumatlari yazariq ve bazaya o
 * cur qosulariq amma heleki bu cur olacaq :))
 *
 * @author Ramin
 */
public class DatabaseConnection {

    final static String HOST = "localhost";
    final static String PORT = "3306";
    final static String DB_NAME = "testdb";
    final static String USER = "root";
    final static String PASSWORD = "password";

    static Connection con = null;

    /**
     * metod - Connection tipli obyekt qaytarir bu obyekt ile biz Melumat
     * BAZAMIZA - SQL sorgular gondere bileceik ve lazim olan melumatlari ala
     * bilecceyik
     *
     * @return
     */
    static public Connection getConnection() {
        try {
            String connectionURL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useSSL=false";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(connectionURL, "" + USER + "", "" + PASSWORD + "");

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "SQLException", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "ClassNotFoundException", ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "InstantiationException", ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "IllegalAccessException", ex);
        }
System.out.println("returned");
        return con;
    }

    /**
     * Metod - bu metod parametrinde gonderdiyimiz Connection tipli obyekti
     * baglayir ki, RAM-da artiq yer tutmasin bir sozle elaqe qurub melumatlari
     * aldiqdan sonra obyekt bize lazim deyil ve baglayiriq baglamasaq bir
     * muddet sonra RAM-da yaddash dolmaga bashlayacaq bu Client computerinde
     * cox hiss olunmaya biler amma bu Seyfi(yada boshlugu) SERVER proqraminda
     * buraqsaq o zaman server bir nece saat ishledikden sonra Ya Qeza
     * veiyyetinde sonecek yada her neise yaxshi sheyler olmayacaq :)))))
     *
     * @param con Connection tipli obyekt verin ki, onu baglasin
     */
    public static void close(Connection con) {
        //yoxlayiriq eger verilen obyekt bosh deyilse yani null deyilse baglayiriq
        //yoxlamasaq eger ve verilen null dursa onda NullPointException cixacaq ))
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "SQLException : Error in DatabaseConnection.close(Connection con)", ex);
            }

        }
    }

    /**
     * Metod - bu metodda hemcinin yuxarida qeyd olunna close metodu ile eyni
     * ishi gorur lakin bu metod Connection tipli yox Resultset tipli obyekti
     * baglayir. Siz DatabaseConnection.close(Prametr) yazdiqda parametre hansi
     * obyekti versez yani ikisinnen birini ya Connection Yada ResultSet java
     * ozu sececek hansi metodu ishe salsin yuxaridakini yoxsa bunu
     *
     * @param rs ResultSet - tipli obyekt verinki, onu baglasin
     */
    public static void close(ResultSet rs) {
        //yoxlayiriq eger verilen obyekt bosh deyilse yani null deyilse baglayiriq
        //yoxlamasaq eger ve verilen null dursa onda NullPointException cixacaq ))
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "SQLException : Error in DatabaseConnection.close(ResultSet rs)", ex);
            }
        }

    }

}
