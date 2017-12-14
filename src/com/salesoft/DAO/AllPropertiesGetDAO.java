/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.Properties.AllProperties;
import com.salesoft.Properties.UIProperty;
import com.salesoft.Properties.URLProperty;
import com.salesoft.util.MyLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramin
 */
public class AllPropertiesGetDAO {

    private static final String SQL_GET_PROPERTIES_BY_TYPE = "SELECT * FROM allproperties where type=?";

    public static AllProperties getAllProperties() {
        AllProperties AllProp = new AllProperties();
        URLProperty urlProp = getURLProperty();
        UIProperty uIProperty = getUIProperty();

        AllProp.setURLProperty(urlProp);
        AllProp.setUIProperty(uIProperty);

        return AllProp;

    }

    public static URLProperty getURLProperty() {

        URLProperty urlProp = null;

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_GET_PROPERTIES_BY_TYPE);
            ps.setString(1, "url");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                urlProp = new URLProperty(
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5), 
                        rs.getString(6), 
                        rs.getString(7), 
                        rs.getString(8), 
                        rs.getString(9), 
                        rs.getString(10), 
                        rs.getString(11), 
                        rs.getString(12)
                );
            }

            return urlProp;
        } catch (SQLException ex) {
            new MyLogger("SQLException in - AllPropertiesGetDAO.getURLProperty()").getLogger().log(Level.SEVERE, "SQLException", ex);
            return (null);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AllPropertiesGetDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static UIProperty getUIProperty() {

        Properties properties = new Properties();

        //old
        InputStream inputStream;

        UIProperty up = new UIProperty();

        try {
            /*old
            inputStream = new FileInputStream("UIProperty.properties");
            properties.load(inputStream);

             */

            // new
            InputStream input = new FileInputStream(new File("UIProperty.properties"));
            properties.load(new InputStreamReader(input, "UTF-8"));
            System.out.println(new InputStreamReader(input, Charset.forName("UTF-8")));
            up.setApplicationTitle(properties.getProperty("applicationtitle"));
            return up;
        } catch (IOException e) {
            System.out.println("error in UIProperty");
            return null; // in error
        }

    }
}
