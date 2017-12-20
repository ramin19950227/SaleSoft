/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.model.DBProperties;
import com.salesoft.util.MyAlert;
import com.salesoft.util.MyLogger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

/**
 *
 * @author Ramin
 */
public class MyPropertiesDAO {

    /**
     * Metod DBProperties Modelini Melumat Bazasi .properties - faylinda olan
     * melumatlarla doldurur ve hazır DBProperties obyektini qaytarir
     *
     * @return
     */
    public static DBProperties getDbProperty() {

        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("Properties/DBProperties.properties");
        } catch (FileNotFoundException ex) {

            //Niyese mene ele gelir ki bu exception hec vaxt cixmayacaq
            //cunki proqram ishe dushende Init Class-i oz ishini gorur axi
            // init classinin ishi bitdikden sonra bu classlar ishe dushur
            MyAlert.alertContent(36, "Məlumat Bazasının Properties faylı mövcut deyil "
                    + "\n Bu Xəta ilk dəfə cıxırsa Proqramı Yenidən işə salın");
        }

        try {
            //test ucun 
            properties.load(inputStream);
        } catch (IOException ex) {
            System.out.println("");
            PrintStream logExceptionFromConsole = MyLogger.logExceptionFromConsole();
            System.out.println("com.salesoft.DAO.MyPropertiesDAO.getDbProperty()");
            System.err.println("Exception Message: " + ex.getMessage());

            //Niyese mene ele gelir ki bu exception hec vaxt cixmayacaq
            //cunki proqram ishe dushende Init Class-i oz ishini gorur axi
            // init classinin ishi bitdikden sonra bu classlar ishe dushur
            MyAlert.alertAndExitByCodeAndContent(36, "Məlumat Bazasının Properties faylı mövcut deyil "
                    + "\n Bu Xəta ilk dəfə cıxırsa Proqramı Yenidən işə salın");
        }

        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.crypted.password");

        String host = properties.getProperty("db.host");
        Integer port;
        try {
            port = Integer.parseInt(properties.getProperty("db.port"));
        } catch (NumberFormatException e) {
            MyAlert.alertContent(46, "Server Ayarlarında Qeyd olunan Port Keçərsizdir \n db.port=????");
            port = 3306;
        }

        String dbName = properties.getProperty("db.name");

        DBProperties property = new DBProperties(user, password, host, port, dbName);

        return property;
    }

    public static void setDBProperty(DBProperties property) {

    }

}
