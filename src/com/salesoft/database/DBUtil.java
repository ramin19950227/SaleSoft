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
    private static Statement stmt = null;
    private static ResultSet rs = null;

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
        }

        System.out.println("MySQL JDBC Driver = OK!");
    }

    /**
     * Bazamizla elaqe yaradir. conn obyektini dolduruq ve elaqe acir yani sorgu
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
     * Heap-dan Silinecek ve Ramda yer boshalacaq, Update: Statement ve
     * ResultSet obyektlerinide baglayir
     *
     * @throws SQLException
     */
    public static void dbDisconnect() throws SQLException {
        try {
            if (rs != null) {
                //Close resultSet
                rs.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
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
     * Bu Metoda SQL Soru vereceyik ve necicede ise ResultSet Obyekti
     * Qaytaracaq, meselen ResultSet rs = DBUtl.bdExecuteQuery(SQL);
     *
     * @param selectSQLQuery
     * @return ResultSet Tipli obyekt qaytarir
     * @throws SQLException
     */
    public static ResultSet dbExecuteQuery(String selectSQLQuery) throws SQLException {

        //old
        //CachedRowSetImpl crs = new CachedRowSetImpl();
        try {
            //Connect to DB (Establish MySQL Connection)
            dbConnect();
            System.out.println("selectSQLQuery: " + selectSQLQuery + "\n");

            stmt = conn.createStatement();

            //Execute select (query) operation
            rs = stmt.executeQuery(selectSQLQuery);

            return rs;
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            //Close connection
            dbDisconnect();
        }
    }

    /**
     * DB Execute Update (For Update/Insert/Delete) Operation
     *
     * @param updateSQLQuery
     * @throws SQLException
     */
    public static void dbExecuteUpdate(String updateSQLQuery) throws SQLException {
        try {
            //Connect to DB (Establish MySQL Connection)
            dbConnect();

            System.out.println("updateSQLQuery :" + updateSQLQuery);

            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(updateSQLQuery);
        } catch (SQLException e) {
            System.out.println("com.salesoft.database.DBUtil.dbExecuteUpdate()");
            System.out.println("SQLQuery :" + updateSQLQuery);
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            //Close connection
            dbDisconnect();
        }
    }
}
