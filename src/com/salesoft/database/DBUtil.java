/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.database;

import com.salesoft.MainApp;
import com.salesoft.model.DBProperties;
import com.salesoft.util.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import javafx.stage.Stage;

/**
 * Bu Class Məlumat Bazası ilə Əməlyyatlar aparmaq üçün istifadə olunur
 *
 * @author Ramin İsmayılov
 * @since 21.12.2017
 */
public class DBUtil {

    /**
     * Connection Obyetimizi elan edirik irelide istifade edeceyik
     */
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    /**
     * Driveri bundan evvel her qoshulmada teyin edirdi yada hazirlayirdi her ne
     * edirdise, amma mene ele gelir bunu her qoshulmada etmek yersiz ola biler,
     * o sebebden bunu Class-a ilk muraciet zamani edirik ve 1-defe edirik her
     * defe yox
     */
    static {
        // MySQL driverini hazirlayiriq teyin edirik
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException -  DBUtil.static{}: " + e);
            MyLogger.logException("ClassNotFoundException - DBUtil.static{}", e);
        }
    }

    /**
     * Bazamizla elaqe yaradir. conn obyektini dolduruq ve elaqe acir yani sorgu
     * gonderme ucun hazirlayir
     *
     * @throws java.sql.SQLException
     * @deprecated - Metodu yenilemek lazimdir
     */
    public static void dbConnect() throws SQLException {

        // DBProperties Obyektimizdeki URL ile bazamiza qoshulmaga calishiriq
        try {
            // Elde etdyimz Elaqe (Connection) Obyektini conn adli yuxarida elan etdiyimiz unvana yerleshdiririk;
            conn = DriverManager.getConnection(MyProperties.getDBProperties().getDbUrl());
            rs = null;
            stmt = null;

        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.dbConnect(): " + e);
            MyLogger.logException("SQLException - DBUtil.dbConnect()", e);
            throw e;
        }
        // ve Bitdi. Bize lazim olan bu qeder idi elaqe qurduq ve yerleshdirdik conn adli unvana
        // ve Obyektimiz artiq istifadeye hazirdir. ishimiz bitirdikden sonra baglamagi unutmayin
        // ve Bitdi. Bize lazim olan bu qeder idi elaqe qurduq ve yerleshdirdik conn adli unvana
        // ve Obyektimiz artiq istifadeye hazirdir. ishimiz bitirdikden sonra baglamagi unutmayin
    }

    /**
     * Metodun Connection URL-de Melumat Bazasi adi istifade olunmur
     *
     * @throws SQLException
     */
    public static void directConnect() throws SQLException {

        // DBProperties Obyektimizdeki URL ile bazamiza qoshulmaga calishiriq
        try {
            // Elde etdyimz Elaqe (Connection) Obyektini conn adli yuxarida elan etdiyimiz unvana yerleshdiririk;
            conn = DriverManager.getConnection(MyProperties.getDBProperties().getDirectUrl());
            rs = null;
            stmt = null;

        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.dbConnect(): " + e);
            MyLogger.logException("SQLException - DBUtil.dbConnect()", e);
            throw e;
        }
        // ve Bitdi. Bize lazim olan bu qeder idi elaqe qurduq ve yerleshdirdik conn adli unvana
        // ve Obyektimiz artiq istifadeye hazirdir. ishimiz bitirdikden sonra baglamagi unutmayin
        // ve Bitdi. Bize lazim olan bu qeder idi elaqe qurduq ve yerleshdirdik conn adli unvana
        // ve Obyektimiz artiq istifadeye hazirdir. ishimiz bitirdikden sonra baglamagi unutmayin
    }

    //
    /**
     * Metod aciq olan elaqeleri baglayir
     *
     * @throws SQLException
     */
    public static void allDisconnect() throws SQLException {
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
            System.out.println("SQLException -  DBUtil.allDisconnect(): " + e);
            MyLogger.logException("SQLException - DBUtil.allDisconnect()", e);
            throw e;
        }
    }

    /**
     * Bu Metoda SQL Soru vereceyik ve necicede ise ResultSet Obyekti
     * Qaytaracaq, meselen ResultSet rs = DBUtl.bdExecuteQuery(SQL);, Diqqet Bu
     * Metodla ishimiz bitdikden sonra mutleq baglamaq lazimdir
     *
     * @param selectSQLQuery
     * @return ResultSet Tipli obyekt qaytarir
     * @throws SQLException
     * @deprecated
     */
    public static ResultSet dbExecuteQuery(String selectSQLQuery) throws SQLException {

        //old
        //CachedRowSetImpl crs = new CachedRowSetImpl();
        try {
            //Connect to DBProperties (Establish MySQL Connection)
            dbConnect();
            System.out.println("selectSQLQuery: " + selectSQLQuery + "\n");

            stmt = conn.createStatement();

            //Execute select (query) operation
            rs = stmt.executeQuery(selectSQLQuery);

            return rs;
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.dbExecuteQuery(): " + e);
            MyLogger.logException("SQLException - DBUtil.dbExecuteQuery()", e);
            throw e;
        }
    }

    /**
     * Metod Connection URL-inde yani qoshulma unvaninda DB name Baza adindan
     * istifade etmir
     *
     * @param selectSQLQuery
     * @return
     * @throws SQLException
     */
    public static ResultSet directExecuteQuery(String selectSQLQuery) throws SQLException {

        //old
        //CachedRowSetImpl crs = new CachedRowSetImpl();
        try {
            //Connect to DBProperties (Establish MySQL Connection)
            directConnect();
            System.out.println("selectSQLQuery: " + selectSQLQuery + "\n");

            stmt = conn.createStatement();

            //Execute select (query) operation
            rs = stmt.executeQuery(selectSQLQuery);

            return rs;
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.dbExecuteQuery(): " + e);
            MyLogger.logException("SQLException - DBUtil.dbExecuteQuery()", e);
            throw e;
        }
    }

    /**
     * DBProperties Execute Update (For Update/Insert/Delete) Operation
     *
     * @param updateSQLQuery
     * @throws SQLException
     * @deprecated
     */
    public static void dbExecuteUpdate(String updateSQLQuery) throws SQLException {
        try {
            //Connect to DBProperties (Establish MySQL Connection)
            dbConnect();

            System.out.println("updateSQLQuery :" + updateSQLQuery);

            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(updateSQLQuery);
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.dbExecuteUpdate(): " + e);
            MyLogger.logException("SQLException - DBUtil.dbExecuteUpdate()", e);
            throw e;
        } finally {
            //Close connection
            allDisconnect();
        }
    }

    /**
     * Metodun Connection URL-de Melumat Bazasi adi istifade olunmur, belece
     * CREATE sorgularini rahatliqla ede bilerem
     *
     * @param updateSQLQuery
     * @throws SQLException
     */
    public static void directExecuteUpdate(String updateSQLQuery) throws SQLException {
        try {
            //Connect to DBProperties (Establish MySQL Connection)
            directConnect();

            System.out.println("updateSQLQuery :" + updateSQLQuery);

            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(updateSQLQuery);
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.dbExecuteUpdate(): " + e);
            MyLogger.logException("SQLException - DBUtil.dbExecuteUpdate()", e);
            throw e;
        } finally {
            //Close connection
            allDisconnect();
        }
    }

    /**
     * Metod Server Ile Elaqe olub olmadigini yoxlayir
     *
     * @return
     */
    public static boolean hasConnetion() {
        try {
            conn = DriverManager.getConnection(MyProperties.getDBProperties().getDirectUrl());
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.hasConnetion(): " + e);
            MyLogger.logException("SQLException - DBUtil.hasConnetion()", e);
            return false;
        }
    }

    /**
     * Metod Melumat Bazasinin Qurulu olub olmadigini yoxlayir (Yani Serverin
     * icinde melumat bazasinin olub olmadigini yoxlayir, Server ishlek olsa
     * bele eger Baza Qurulu deyilse false qaytaracaq), Baza Quruludursa true
     * qaytarir deyilse exception cixir ve false qaytarir
     *
     * @return
     */
    public static boolean hasDBConnetion() {
        try {
            conn = DriverManager.getConnection(MyProperties.getDBProperties().getDbUrl());
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.hasDBConnetion(): " + e);
            MyLogger.logException("SQLException - DBUtil.hasDBConnetion()", e);
            return false;
        }
    }

    /**
     * Server Yanili olub olmadigini Yoxlayir, eger server yanilidirsa true
     * qaytarir eks teqdirde false qaytarir
     *
     * @return
     */
    public static Boolean isServerRunning() {
        try {

            /**
             * Melumatlarimizi MyProperties Obyektinde olan ve Properties
             * Faylindan yuklenmish DBProperties Obyektini aliriq ve ondanda
             * host ve portumuz hada melumati aliriq
             */
            DBProperties dbp = MyProperties.getDBProperties();

            String host = dbp.getHost();
            int port = dbp.getPort();

            /**
             * Socket ile elaqe qurmaga calishaq, elaqe qura bilsek true
             * qaytaracayiq, olmasa false
             */
            Socket socket = new Socket(host, port);

            // Socketimizi baglayaq her ehtimala qarshi
            socket.close();
            return true;

        } catch (ConnectException e) {
            System.out.println("ConnectException -  DBUtil.isServerRunning(): " + e);
            MyLogger.logException("ConnectException - DBUtil.isServerRunning()", e);
            return false;

        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException -  DBUtil.isServerRunning(): " + e);
            MyLogger.logException("UnknownHostException - DBUtil.isServerRunning()", e);
            return false;

        } catch (IOException e) {
            System.out.println("IOException -  DBUtil.isServerRunning(): " + e);
            MyLogger.logException("IOException - DBUtil.isServerRunning()", e);
            return false;

        }
    }

    /**
     * Medot Cagirildiqda Serveri Ayarlama Penceresi Ekrana gelecek, ve eger
     * pencereni yuxari sag kuncdeki X-isharesi ile baglasaniz true qaytaracaq
     *
     * @return
     */
    public static Boolean showServerConfigView() {
        //metodun istifade telimati 
        //bu cur istifade ede bilersiz
//        Boolean isClosed = DBUtil.showServerConfigView();
//        if (isClosed) {
//            System.exit(241);
//        }

        Boolean isClosed;
        Stage nStage = new Stage();
        nStage.setScene(MyFXMLLoader.getSceneFromURL(MainApp.class.getResource("view/Server.fxml")));
        nStage.setMaximized(false);
        nStage.setTitle("Serverle Elaqe Ayarlari - Sale Soft");
        nStage.showAndWait();
        return true;

    }
}
