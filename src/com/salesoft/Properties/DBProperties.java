/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.Properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramin
 */
public class DBProperties {

    /**
     * Elaqe ucun lazim olan melumatlarimiz
     */
    public static final String HOST;
    public static final String PORT;
    public static final String DB_NAME;
    public static final String USER;
    public static final String PASSWORD;
    public static final String CONNECTION_URL;
    public static final String CONNECTION_URL_WITHOUT_DB;

    /**
     * Melumatlari ilk muracietimizde yukleyirik
     */
    static {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("Properties/DBProperties.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            properties.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }

        HOST = properties.getProperty("db.host");
        PORT = properties.getProperty("db.port");
        DB_NAME = properties.getProperty("db.name");

        USER = properties.getProperty("db.user");
        //shifremiz heleki aciq shekilde qalacaq 
        //amma irelide onuda kodlashdiracam
        PASSWORD = properties.getProperty("db.crypted.password");

        CONNECTION_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useSSL=false";

        CONNECTION_URL_WITHOUT_DB = "jdbc:mysql://" + HOST + ":" + PORT + "/" + "?useSSL=false";

    }

}
