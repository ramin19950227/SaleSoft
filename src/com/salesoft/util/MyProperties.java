/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.DAO.MyPropertiesDAO;
import com.salesoft.model.DBProperties;

/**
 * Bu Class- ile Properties Obyektlerimizle Oxuma ve Yazma emeliyyatlari
 * aparacayiq, MyPopertiesDAO - ile bu Class eyni ishi gorecek amma Bu Class
 * ABALOCKA olacaq yani Layer olacaq, Cunki Burda Fayl seviyyesinde hec bir
 * emeliyyat aparilmayacaq
 *
 * @author Ramin
 */
public class MyProperties {

    public static DBProperties DB;

    public static void init() {
        System.out.println("com.salesoft.util.MyProperties.init()");
        loadDBProperties();
    }

    /**
     * Metod DB obyektimizi yenileyir, Yenileme ishini MyPropertiesDAO edir
     */
    public static void loadDBProperties() {
        System.out.println("com.salesoft.util.MyProperties.loadDBProperties()");
        DB = MyPropertiesDAO.getDbProperty();
    }

    public static void saveDBProperties(DBProperties properties) {
        System.out.println("com.salesoft.util.MyProperties.saveDBProperties()");
        System.err.println("TODO");
    }

}
