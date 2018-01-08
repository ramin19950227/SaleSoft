/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.DAO.Properties.DBPropertiesDAO;
import com.salesoft.DAO.Properties.UIPropertiesDAO;
import com.salesoft.DAO.Properties.URLPropertiesDAO;
import com.salesoft.model.Properties.DBProperties;
import com.salesoft.model.Properties.UIProperties;
import com.salesoft.model.Properties.URLProperties;

/**
 * Bu Class- ile Properties Obyektlerimizle Oxuma ve Yazma emeliyyatlari
 * aparacayiq, MyPopertiesDAO - ile bu Class eyni ishi gorecek amma Bu Class
 * ABALOCKA olacaq yani Layer olacaq, Cunki Burda Fayl seviyyesinde hec bir
 * emeliyyat aparilmayacaq, ve alinan Properties obyektleri bunun icinde birge
 * saxlanacaq ve her defe hansisa melumat lazim olanda DAO-dan yeni obyekt
 * alinmayacaq, obyekt bir defe alinacaq ve lazim olanda ise yenilenecek
 *
 * @author Ramin
 */
public class MyProperties {

    private static DBProperties DBProperties;
    private static URLProperties URLProperties;
    private static UIProperties UIProperties;

    public static void init() {
        //updateDBProperties();
        updateURLProperties();
        //updateUIProperties();
    }

    /**
     * Metod dBProperties obyektimizi yenileyir, Yenileme ishini DBPropertiesDAO
     * edir, Yenileme - yani properties faylini yeniden oxuyur ve yeni
     * melumatlarla doldurur dBProperties Obyektimizi
     */
    public static void updateDBProperties() {
        DBProperties = DBPropertiesDAO.loadDbPropertiesFromFile();
    }

    public static void updateURLProperties() {
        URLProperties = URLPropertiesDAO.loadURLPropertiesFromFile();
    }

    public static void updateUIProperties() {
        UIProperties = UIPropertiesDAO.loadUIPropertiesFromFile();
    }

    /**
     * Metod Parametr olaraq verilen dBProperties Obyektini gonderir DAO ya
     * Fayla yazmaq ucun ve Avtomatik olaraq MyProperties-in icinde olan
     * dBProperties OByektini yenileyir
     *
     * @param dbp
     */
    public static void saveDBProperties(DBProperties dbp) {

        // aldigimiz obyekti gonderek DAO-ya
        DBPropertiesDAO.saveDBPropertiesToFile(dbp);

        // yaddasha yazdiqdan sonra yeni melumatlari almaq ucun Obyektimizi yenileyek
        updateDBProperties();
    }

    public static void saveURLProperties(URLProperties urlp) {
        URLPropertiesDAO.saveURLPropertiesToFile(urlp);
        updateURLProperties();
    }

    public static void saveUIProperties(UIProperties uip) {
        UIPropertiesDAO.saveUIPropertiesToFile(uip);
        updateURLProperties();
    }

    /**
     * Bu metod Bu Class-in init() funksiyasi ishe dushdukden sonra elde edilen
     * dBProperties obyektini qaytarir, bu obyekti yenilemek ucun
     * updateDBProperties() - funksiyasini cagirmaq lazimdir
     *
     * @return
     */
    public static DBProperties getDBProperties() {
        return DBProperties;
    }

    public static URLProperties getURLProperties() {
        return URLProperties;
    }

    public static UIProperties getUIProperties() {
        return UIProperties;
    }

}
