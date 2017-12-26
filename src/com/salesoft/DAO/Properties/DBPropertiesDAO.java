/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO.Properties;

import com.salesoft.model.Properties.DBProperties;
import com.salesoft.util.MyAlert;
import com.salesoft.util.MyExceptionLogger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 *
 * @author Ramin
 */
public class DBPropertiesDAO {

    /**
     * Metod DBProperties Modelini Melumat Bazasi .properties - faylinda olan
     * melumatlarla doldurur ve hazır DBProperties obyektini qaytarir
     *
     * @return DBProperties
     */
    public static DBProperties loadDbPropertiesFromFile() {

        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("Properties/DBProperties.properties");
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException -  MyPropertiesDAO.loadDbPropertyFromFile(): " + e);
            MyExceptionLogger.logException("FileNotFoundException - MyPropertiesDAO.loadDbPropertyFromFile()", e);

            //Niyese mene ele gelir ki bu exception hec vaxt cixmayacaq
            //cunki proqram ishe dushende Init Class-i oz ishini gorur axi
            // init classinin ishi bitdikden sonra bu classlar ishe dushur
            MyAlert.alertContent(36, "Məlumat Bazasının Properties faylı mövcut deyil "
                    + "\n Bu Xəta ilk dəfə cıxırsa Proqramı Yenidən işə salın");
        }

        try {
            //test ucun 
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("IOException -  MyPropertiesDAO.loadDbPropertyFromFile(): " + e);
            MyExceptionLogger.logException("IOException - MyPropertiesDAO.loadDbPropertyFromFile()", e);

            //Niyese mene ele gelir ki bu exception hec vaxt cixmayacaq
            //cunki proqram ishe dushende Init Class-i oz ishini gorur axi
            // init classinin ishi bitdikden sonra bu classlar ishe dushur
            MyAlert.alertAndExitByCodeAndContent(36, "Məlumat Bazasının Properties faylınin yuklenmesinde Problemcixdi "
                    + "\nBu Xəta ilk dəfə cıxırsa Proqramı Yenidən işə salın");
        }

        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.crypted.password");

        String host = properties.getProperty("db.host");
        Integer port;
        try {
            port = Integer.parseInt(properties.getProperty("db.port"));
        } catch (NumberFormatException ex) {
            System.out.println("NumberFormatException -  MyPropertiesDAO.loadDbPropertyFromFile(): " + ex);
            MyExceptionLogger.logException("NumberFormatException - MyPropertiesDAO.loadDbPropertyFromFile()", ex);

            MyAlert.alertContent(46, "Server Ayarlarında Qeyd olunan Port Keçərsizdir "
                    + "\n Default olaraq 3306 secilecek db.port=3306"
                    + "\n Amma Yenede Ayarlari Duzeltmek lazimdir");
            port = 3306;
        }

        String dbName = properties.getProperty("db.name");

        DBProperties dbp = new DBProperties(user, password, host, port, dbName);

        return dbp;
    }

    /**
     * Metod Parametr olaraq verilen DBProperties Obyektini yazir Properties
     * faylina
     *
     * @param dbp
     */
    public static void saveDBPropertiesToFile(DBProperties dbp) {
        Properties properties;
        OutputStream output;

        try {
            properties = new Properties();
            output = new FileOutputStream("Properties/DBProperties.properties");

            properties.setProperty("db.host", dbp.getHost());
            properties.setProperty("db.port", dbp.getPort().toString());
            properties.setProperty("db.name", dbp.getDbName());
            properties.setProperty("db.user", dbp.getUser());
            properties.setProperty("db.crypted.password", dbp.getPassword());
            properties.store(output, null);
            output.close();

        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException -  MyPropertiesDAO.saveDBPropertiesToFile(DBProperties dbp): " + ex);
            MyExceptionLogger.logException("FileNotFoundException - MyPropertiesDAO.saveDBPropertiesToFile(DBProperties dbp)", ex);
        } catch (IOException ex) {
            System.out.println("IOException -  MyPropertiesDAO.saveDBPropertiesToFile(DBProperties dbp): " + ex);
            MyExceptionLogger.logException("IOException - MyPropertiesDAO.saveDBPropertiesToFile(DBProperties dbp)", ex);
        }
    }
    
    

}
