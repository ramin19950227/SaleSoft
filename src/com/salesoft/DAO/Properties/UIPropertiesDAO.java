/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO.Properties;

import com.salesoft.model.Properties.UIProperties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Ramin
 */
public class UIPropertiesDAO {

    public static UIProperties loadUIPropertiesFromFile() {
        Properties properties = new Properties();
        InputStream inputStream;
        UIProperties up = new UIProperties();

        try {
            inputStream = new FileInputStream("properties/UIProperties.properties");
            properties.load(inputStream);

            up.setApplicationTitle(properties.getProperty("application.Title"));
            return up;
        } catch (IOException e) {
            System.out.println("com.salesoft.DAO.AllPropertiesGetDAO.getUIProperty()");
            System.err.println(e);
            return null; // in error
        }
    }

    public static void saveUIPropertiesToFile(UIProperties uip) {
        System.out.println("com.salesoft.DAO.Properties.UIPropertiesDAO.saveURLPropertiesToFile()");
        System.err.println("TODO");
    }

}
