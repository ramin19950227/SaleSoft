/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bazaile Emelyyatlar aparmaq ucun istifade olunr
 *
 * @author Ramin
 */
public class DBUtil {

    /**
     * MySql drayverimizi elan edirik
     */
    private static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";

    /**
     * Elaqe ucun lazim olan melumatlarimiz,Irelide meumatlari Properti
     * faylimizda alacayiq
     */
    public final static String HOST = "localhost";
    /**
     * Elaqe ucun lazim olan melumatlarimiz
     */
    public final static String PORT = "3306";
    /**
     * Elaqe ucun lazim olan melumatlarimiz
     */
    public final static String DB_NAME = "testdb";
    /**
     * Elaqe ucun lazim olan melumatlarimiz
     */
    public final static String USER = "root";
    /**
     * Elaqe ucun lazim olan melumatlarimiz
     */
    public final static String PASSWORD = "password";

    /**
     * Connection url elaqe ucun laim olan melumatlarimiz
     */
    private static final String CONNECTION_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useSSL=false";

    /**
     * Connection Obyetimizi elan edirik irelideistifade edeceyik
     */
    private static Connection conn = null;
    // Lazimi Obyektlerimizi Elan edirik ve null teyin edirik
    private static Statement stmt;
    private static ResultSet rs;

    /**
     * Driveri bundan evvel herqoshulmada teyin edirdi yada hazirlayirdi her ne
     * edirdise, amma mene ele gelir bunu her qoshulmada etmek yersiz ola biler
     * o sebebden bunu ilk Class obyektine muraciet zamani edirik, ki her defe
     * etmeyek, yoxlayaq gorek nece olur, mence PIS olmayacaq
     */
    static {
        // MySQL driverini hazirlayiriq teyin edirik
        try {
            Class.forName(DRIVER_MYSQL);
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException");
            System.out.println("com.salesoft.database.DBUtil.static{initialization block}");

            System.out.println("Where is You MySql JDBC Driver? ;) ");
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("END");
        }

        System.out.println("MySQL JDBC Driver = OK!");
    }

    /**
     * Bazamizla elaqe yaradir. conn obyektin dolduruq ve elaqe acir yani sorgu
     * gonderme ucun hazirlayir
     *
     * @throws java.sql.SQLException
     */
    public static void dbConnect() throws SQLException {

        // CONNECTION_URL - addressimizden stifade ederek bazamizla elaqe qurmaga calishiriq;
        try {
            // Elde etdyimz Elaqe (Connection) Obyektini conn adli yuxarida elan etdiyimiz unvana yerleshdiririk;
            conn = DriverManager.getConnection(CONNECTION_URL, "" + USER + "", "" + PASSWORD);
            rs = null;
            stmt = null;

        } catch (SQLException e) {
            System.out.println("com.salesoft.database.DBUtil.dbConnect()");
            System.out.println("Elaqe Ugursuz alindi! " + e);
            String message = e.getMessage();
            System.out.println(message);
            throw e;
        }
        // ve Bitdi. Bize lazim olan bu qeder idi elaqe qurduq ve yerleshdirdik conn adli unvana
        // ve Obyektimiz artiq istifadeye hazirdir. ishimiz bitirdikden sonra baglamagi unutmayin
    }

    //
    /**
     * Close Connection, conn unvanina bagli olan Elaqe(Connection) obyektini
     * baglayir yani sifirlayir bu sifirlama emeliyyatindan sonra obyekte hec
     * bir unvan yollanmadigi ucun obyekt olu sayilir ve bir muddet sonra Garbac
     * Collector deyilen Bir Yontemle ve ya bir Metodla her nedirse onunla
     * Heap-dan Silinecek ve Ramda yer boshalacaq,
     *
     * @throws SQLException
     */
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("com.salesoft.database.DBUtil.dbDisconnect()");
            System.out.println("SQLException details is: " + e.getLocalizedMessage());
            throw e;
        }
    }

    // Bu metod biraz problem cixartdi
    // CachedRowSetImpl - deyilir kohnelib bu 
    // crs - evezine ResultSeti qaytarsam bu metod 
    //finally olaraq rs-i baglayir rs bagli oldugu halda onu alan metod oxumaga calishsa onda ne ola biler?
    // amma rs qaytariram heleki normal ishleyir gorek ne olacaq
    /**
     * Bu Metoda SQL Soru vereceyik ve necicede ise ChachedRowSetImpl Obyekti
     * Qaytaracaq, bu obyekti ResultSet- ile alacayiq yani meselen ResultSet rs
     * = DBUtl.bdExecuteQuery(SQL); bu cur, mence bunun ustunluyu odurki MENCE:
     * connection yani baza le elaqe baglandirqdan sonra qaytrila melumatlar
     * itme ehtimali olmur, cunki melumatlari aldiqdan sonra yaddashda saxlayir
     * ve Elaqe baglansa bele o melumatlarlaishleme olacaq.
     *
     * @param queryStmt
     * @return
     * @throws SQLException
     */
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {

        //old
        //CachedRowSetImpl crs = new CachedRowSetImpl();
        try {
            //Connect to DB (Establish MySQL Connection)
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");

            //Create statement
            PreparedStatement ps = conn.prepareStatement(queryStmt);

            //Execute select (query) operation
            rs = ps.executeQuery();

            return rs;
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        }
    }

    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish MySQL Connection)
            dbConnect();
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
}
