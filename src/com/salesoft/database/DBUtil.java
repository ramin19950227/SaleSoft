/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.database;

import com.salesoft.Properties.DBProperties;
import com.salesoft.util.RLogger;
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
     * Connection Obyetimizi elan edirik irelide istifade edeceyik
     */
    private static Connection conn = null;
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
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            RLogger.logException("ClassNotFoundException", e);

            System.out.println("Where is You MySql JDBC Driver? ;) " + e);
        }
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
            conn = DriverManager.getConnection(DBProperties.CONNECTION_URL, "" + DBProperties.USER + "", "" + DBProperties.PASSWORD);
            rs = null;
            stmt = null;

        } catch (SQLException e) {
            System.out.println("Elaqe Ugursuz alindi! " + e);
            RLogger.logException("DBUtil.dbConnect() - SQLException", e);
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
     * @exception rs ve ya stmt ve conn her hansi birini bagliyanda rs-de
     * avtomatik baglanir ve (java.sql.SQLException: Operation not allowed after
     * ResultSet closed) Problemi cixir, Netice etibari ile her emeliyyatdan
     * sonra dbDisconnect() metodunu ozum cagirmaliyam (DIQQET!!!!!! Yalnizca rs
     * ile ishim bitdikden sonra)
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
            RLogger.logException("DBUtil.dbExecuteQuery() - SQLException", e);
            throw e;
        } finally {
            //Close connection
            // OLMAZZZZZZ:
            //java.sql.SQLException: Operation not allowed after ResultSet closed
            // problem cixardir bu finally (her nedirse metod ve ya ne bilim ne)- qaytarilan
            // ResultSet ile ishe bashlamadan evvel rs-i, conn-u, stmt-i ve ya her hansi biriin ve ya ucunude 
            // hemen baglayir. yani bir soznen Metod ishini bitirmemish ve tam return elememish bele cixirki
            //finally {} ishe dushur ve baglayir, etice etibari ile
            // DIQQET: dbDisconnect()-i avtomatik cagirmaq olmaz onu rs-i qebul eden metodun icinde rs-ile
            // ishim bitdikden sonra baglamaliyam. Cox uzun oldu ))
            //dbDisconnect(); - Avtomatik olmaz
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

    public static boolean hasConnetion() throws SQLException {
        try {
            conn = DriverManager.getConnection(DBProperties.CONNECTION_URL_WITHOUT_DB, "" + DBProperties.USER + "", "" + DBProperties.PASSWORD);
            dbDisconnect();
            return true;
        } catch (SQLException ex) {
            throw ex;
        }
    }
}
