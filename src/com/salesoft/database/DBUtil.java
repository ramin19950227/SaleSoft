package com.salesoft.database;

import com.salesoft.MainApp;
import com.salesoft.model.Properties.DBProperties;
import com.salesoft.util.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import javafx.stage.Stage;

public class DBUtil {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    private static final String MS_ACCESS_DB_FILE_NAME = "SaleSoftAccessDB.accdb";

    ////////////////////////////////////////////MySQL////////////////////////////////////////////////////////
    public static void mySQLConnect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            new ExceptionShowDialog(ex).showAndWait();
            MyExceptionLogger.logException("ClassNotFoundException - DBUtil.mySQLConnect{}", ex);
        }
        try {
            conn = DriverManager.getConnection(MyProperties.getDBProperties().getDirectUrl());
            rs = null;
            stmt = null;

        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.dbConnect(): " + e);
            MyExceptionLogger.logException("SQLException - DBUtil.dbConnect()", e);
            throw e;
        }
    }

    public static void AllDisconnect() throws SQLException {
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
            MyExceptionLogger.logException("SQLException - DBUtil.allDisconnect()", e);
            throw e;
        }
    }

    public static ResultSet mySQLExecuteQuery(String selectSQLQuery) throws SQLException {

        //old
        //CachedRowSetImpl crs = new CachedRowSetImpl();
        try {
            //Connect to DBProperties (Establish MySQL Connection)
            mySQLConnect();
            System.out.println("selectSQLQuery: " + selectSQLQuery + "\n");

            stmt = conn.createStatement();

            //Execute select (query) operation
            rs = stmt.executeQuery(selectSQLQuery);

            return rs;
        } catch (SQLException e) {
            MyExceptionLogger.logExceptionV2("SQLException - DBUtil.dbExecuteQuery()", "selectSQLQuery: " + selectSQLQuery, "null", e);
            throw e;
        }
    }

    public static void mySQLExecuteUpdate(String updateSQLQuery) throws SQLException {
        try {
            mySQLConnect();
            //System.out.println("updateSQLQuery :" + updateSQLQuery);

            stmt = conn.createStatement();
            stmt.executeUpdate(updateSQLQuery);
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.dbExecuteUpdate(): " + e);
            MyExceptionLogger.logException("SQLException - DBUtil.dbExecuteUpdate()", e);
            throw e;
        } finally {
            AllDisconnect();
        }
    }

    public static boolean mySQLHasConnetion() {
        try {
            conn = DriverManager.getConnection(MyProperties.getDBProperties().getDirectUrl());
            return true;
        } catch (SQLException ex) {
            new ExceptionShowDialog(ex).showAndWait();
            MyExceptionLogger.logException("SQLException - DBUtil.hasConnetion()", ex);
            return false;
        }
    }

    public static boolean mySQLHasDBConnetion() {
        try {
            conn = DriverManager.getConnection(MyProperties.getDBProperties().getDbUrl());
            return true;
        } catch (SQLException ex) {
            new ExceptionShowDialog(ex).showAndWait();
            MyExceptionLogger.logException("SQLException - DBUtil.hasDBConnetion()", ex);
            return false;
        }
    }

    public static Boolean mySQLIsServerRunning() {
        try {

            DBProperties dbp = MyProperties.getDBProperties();

            String host = dbp.getHost();
            int port = dbp.getPort();

            Socket socket = new Socket(host, port);

            socket.close();
            return true;

        } catch (ConnectException ex) {
            System.err.println("ConnectException");
            MyExceptionLogger.logException("ConnectException - DBUtil.isServerRunning()", ex);
            return false;

        } catch (UnknownHostException ex) {
            System.err.println("UnknownHostException");
            MyExceptionLogger.logException("UnknownHostException - DBUtil.isServerRunning()", ex);
            return false;

        } catch (IOException ex) {
            System.err.println("IOException");
            MyExceptionLogger.logException("IOException - DBUtil.isServerRunning()", ex);
            return false;

        }
    }

    public static Boolean showmySQLServerConfigView() {
        Boolean isClosed;
        Stage nStage = new Stage();
        nStage.setScene(MyFXMLLoader.getSceneFromURL(MainApp.class.getResource("view/Server.fxml")));
        nStage.setMaximized(false);
        nStage.setTitle("Serverle Elaqe Ayarlari - Sale Soft");
        nStage.showAndWait();
        return true;
    }

    ////////////////////////////////////////////MS ACCESS////////////////////////////////////////////////////////
    public static void msAccessConnect() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Class.forName error");
        }

        try {
            //conn = DriverManager.getConnection("jdbc:ucanaccess://test.accdb;jackcessOpener=com.salesoft.database.CryptCodecOpener", "hamreen", "12345");
            conn = DriverManager.getConnection("jdbc:ucanaccess://" + MS_ACCESS_DB_FILE_NAME + ";");
        } catch (SQLException e) {
            System.err.println(e);
            new ExceptionShowDialog(e).showAndWait();
            System.exit(0);
        }

    }

    public static ResultSet msAccessExecuteQuery(String selectSQLQuery) throws SQLException {

        //old
        //CachedRowSetImpl crs = new CachedRowSetImpl();
        try {
            //Connect to DBProperties (Establish MySQL Connection)
            msAccessConnect();
            System.out.println("selectSQLQuery: " + selectSQLQuery + "\n");

            stmt = conn.createStatement();

            //Execute select (query) operation
            rs = stmt.executeQuery(selectSQLQuery);

            return rs;
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.msAccessExecuteQuery(): " + e);

            new ExceptionShowDialog(e).showAndWait();
            MyExceptionLogger.logExceptionV2("SQLException - DBUtil.msAccessExecuteUpdate()", "selectSQLQuery: " + selectSQLQuery, "null", e);
            throw e;
        }
    }

    public static void msAccessExecuteUpdate(String updateSQLQuery) throws SQLException {
        try {
            msAccessConnect();
            System.out.println("updateSQLQuery :" + updateSQLQuery);

            stmt = conn.createStatement();
            stmt.executeUpdate(updateSQLQuery);
        } catch (SQLException e) {
            System.out.println("SQLException -  DBUtil.msAccessExecuteUpdate(): " + e);
            new ExceptionShowDialog(e).showAndWait();

            MyExceptionLogger.logException("SQLException - DBUtil.msAccessExecuteUpdate()", e);
            throw e;
        } finally {
            AllDisconnect();
        }
    }

}
