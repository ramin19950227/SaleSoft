/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * MyLogger - bu Class- ile biz loglarimizi yazacayiq irelide bunu deyishdirmek
 * ve dahada mukemmelleshdiremk olar amma heleki bashlanqic ucun bu da bes eder
 * mence, bu class-in obyektini yaradanda yeni txt fayl yaradir C diskinde Log
 * qovlugunda Faylin adi Date() classinin getTime() metodu ile indiki tarixi
 * milli saniyeler ile tesvir edir (seyf etmiremse) millli saniyeleri qaytarir
 * bununla fyllarin adlari unikal olur yani tekrarlanmasi ola bilmez, Istifade
 * yontemi beledir, Exception olanda yeni obyek yaradib onun Contructoruna
 * String tipli obyekt vermek lazimdir hemin String faylin adinin sonuna
 * yazilacaq meselen faylin adinin bashinda 123456789 yazilacaqsa bu tarixdir
 * sonra ise Construktora verdiyimiz String yazilacaq meselen new
 * MyLogger("Test") yazzaq, bizim Log qovlugumuzda 1234567890-Test.txt adli fayl
 * yaranacaq ve icine istediyimizi yaza bilerik , Main appda yeni obyekt qurub
 * adini AppLog qoyuram ve getLogger ederek her defe MyLogger obyektimi her defe
 * log(//////) yazadnda ve neise loqlayanda hemin faylayazacaq bir soznen yeni
 * fayl yaratmayacaq. Yeni fayli Sadece Exceptionlarda istifade etmek
 * meslehetdir ki goze deysin birde Logfaylin icine baxmaga ehtiyyac olmasin ve
 * diger gormek istediyimiz hadiseleri Yeni fayl yaradaraq da goze carpici ede
 * bilerik meselen Silinmeleri ve s...
 *
 * @author Ramin
 */
public class MyLogger {

    //heleki bunu ne ucun istifade olundugunu bilmirem
    //amma neise adlandirmaya oxshayir her neise oyrenende yazaram heleki lazim deyil
    Logger logger = Logger.getLogger("MyLog");

    // bu fayllarla ishlemek ucundur yaqin
    FileHandler fh;

    /**
     * Construstor - Construktora String tipli ne versek Obyekt yarananda fayla
     * verilecek ada yazilacaq
     *
     * @param s - Faylin adina yazilacaq Metin
     */
    public MyLogger(String s) {
        try {

            // This block configure the logger with handler and formatter  
            fh = new FileHandler("C:/Log/" + (new Date().getTime()) + " - " + s + ".txt");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // ilk yazimizi yaziriq Log faylimizin icine  
            logger.info("Bismilləh - İlk Sətir");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logger-i qaytarir bunu aciqlamaga ehtiyyac hiss etmirem adi Getter dir
     * :))
     *
     * @return
     */
    public Logger getLogger() {
        return logger;
    }

}
