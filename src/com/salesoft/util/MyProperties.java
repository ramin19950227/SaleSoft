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
 * emeliyyat aparilmayacaq, ve alinan Properties obyektleri bunun icinde birge
 * saxlanacaq ve her defe hansisa melumat lazim olanda DAO-dan yeni obyekt
 * alinmayacaq, obyekt bir defe alinacaq ve lazim olanda ise yenilenecek
 *
 * @author Ramin
 */
public class MyProperties {

    private static DBProperties DBProperties;

    public static void init() {
        updateDBProperties();
    }

    /**
     * Metod DBProperties obyektimizi yenileyir, Yenileme ishini MyPropertiesDAO
     * edir, Yenileme - yani properties faylini yeniden oxuyur ve yeni
     * melumatlarla doldurur DBProperties Obyektimizi
     */
    public static void updateDBProperties() {
        DBProperties = MyPropertiesDAO.loadDbPropertyFromFile();
    }

    /**
     * Metod Parametr olaraq verilen DBProperties Obyektini gonderir DAO ya
     * Fayla yazmaq ucun ve Avtomatik olaraq MyProperties-in icinde olan
     * DBProperties OByektini yenileyir
     *
     * @param dbp
     */
    public static void saveDBProperties(DBProperties dbp) {

        // aldigimiz obyekti gonderek DAO-ya
        MyPropertiesDAO.saveDBPropertiesToFile(dbp);

        // yaddasha yazdiqdan sonra yeni melumatlari almaq ucun Obyektimizi yenileyek
        updateDBProperties();
    }

    /**
     * Bu metod Bu Class-in init() funksiyasi ishe dushdukden sonra elde edilen
     * DBProperties obyektini qaytarir, bu obyekti yenilemek ucun
     * updateDBProperties() - funksiyasini cagirmaq lazimdir
     *
     * @return
     */
    public static DBProperties getDBProperties() {
        return DBProperties;
    }

}
