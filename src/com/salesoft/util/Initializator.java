package com.salesoft.util;

import com.salesoft.MainApp;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ButtonType;

/**
 * Bu Class Proqramimizin ishlemesi ucun lazim olan Temel Fayllari ve Obyektleri
 * hazirlayir, Meselen Bazayla Elaqe ayarlari yerleshen fayli, Istifadeci
 * interfeysinin data-lari ve s. Hansi ki olmasa, Proqram ferqli ferqli
 * Exceptionlar cixaracaq ve Sonra Problem cixdiqda hell etmekde Cetinleshecek
 *
 * @author Ramin
 */
public class Initializator {

    public static void initFoldersAndFiles() {
        //qovluqlarimizi yoxlayaq her shey yerindedirse
        initFolders();

        //properties fayllarmizi yoxlayaq
//        initProperties();
    }

    private static void initFolders() {
        initLogFolders();
//        initPropertiesFolders();

    }

    private static void initLogFolders() {
        File f = new File("Log\\Exceptions\\");
        if (!f.exists()) {
            System.err.println("Creating Folder for Exception Logs ");
            f.mkdirs();
        }

        File f2 = new File("Log\\UserOperations\\");
        if (!f2.exists()) {
            System.err.println("Creating Folder for UserOperation Logs ");
            f2.mkdirs();
        }

    }
//
//    private static void initPropertiesFolders() {
//        File folder = new File("Properties\\");
//        if (!folder.exists()) {
//            System.err.println("Creating Folder for Properties");
//            folder.mkdirs();
//        }
//    }

    //    private static void initProperties() {
//        initDBProperties();
//        initUIProperties();
//
//    }
//
//    /**
//     * DBProperties.properties - faylimiz eger yoxdursa onu hazirlayir, Bu
//     * Proqrami ilk defe ishe salanda olur, ve ilkin melumatlari yazir fayla
//     */
//    private static void initDBProperties() {
//        File propertiesFile = new File("Properties\\DBProperties.properties");
//        if (!propertiesFile.exists()) {
//            System.err.println("Creating 'DBProperties.properties' - file  in 'Properties' folder");
//
//            OutputStream output;
//            Properties properties = new Properties();
//
//            try {
//                output = new FileOutputStream("Properties/DBProperties.properties");
//                properties.setProperty("db.host", "localhost");
//                properties.setProperty("db.port", "3306");
//                properties.setProperty("db.name", "SaleSoft");
//                properties.setProperty("db.user", "root");
//                properties.setProperty("db.crypted.password", "password");
//                properties.store(output, null);
//                output.close();
//            } catch (FileNotFoundException ex) {
//                MyExceptionLogger.logException("FileNotFoundException - Initializator.initDBProperties()", ex);
//
//                MyAlert.alertAndExitByCodeAndContent(0, "Initializator.initDBProperties() - FileNotFoundException");
//            } catch (IOException ex) {
//                MyExceptionLogger.logException("IOException - Initializator.initDBProperties()", ex);
//
//                MyAlert.alertAndExitByCodeAndContent(0, "Initializator.initDBProperties() - IOException");
//
//            }
//
//        }
//
//    }
//
//    /**
//     * UIProperties initialize
//     */
//    private static void initUIProperties() {
//
//        File propertiesFile = new File("Properties\\UIProperties.properties");
//        if (!propertiesFile.exists()) {
//            System.err.println("Creating 'UIProperties.properties'  in 'Properties' folder");
//
//            OutputStream output;
//            Properties properties = new Properties();
//
//            try {
//                output = new FileOutputStream("Properties/UIProperties.properties");
//                properties.setProperty("application.Title", "SaleSoft - a Xos G\u0259lmisiniz - Login Bolumu");
//                properties.store(output, null);
//                output.close();
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
//                System.out.println("");
//                MyAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initUIProperties() - FileNotFoundException");
//            } catch (IOException ex) {
//                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
//                MyAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initUIProperties() - IOException");
//
//            }
//
//        }
//
//    }
    public static void initMyProperties() {
        MyProperties.init();

    }

//    /**
//     * Metod Serverin Veziyyetini yoxlayir sonra Serverle elaqe qurmaga
//     * calishir, daha sonra ise Melumat bazasinin varligini yoxlayir Eger
//     * yoxdursa Qurmagi teklif edir.
//     */
//    public static void initDataBase() {
//
//        // 1- ci Serverimizin Ishlek veziyyetde olub olmadigini yoxlayaq
//        checkServer();
//
//        // 2-ci Serverimizle Elaqe Qurmaga calishaq
//        checkConnetion();
//
//        //3-ci Melumat Bazamiza qoshulmaga calishaq Baza Adini istifade ederek
//        checkDBConnetion();
//
//        //4 - indi ise yoxlayaq DB ve cedvellerimiz yoxdursa avtomatiq qurrashdiraq
//        //setupDataBase();
//
//    }
//    public static void checkServer() {
//
//        if (DBUtil.mySQLIsServerRunning()) {
//            System.out.println("Initializator.initDataBase() - Server Is Running");
//
//        } else {
//            System.out.println("Initializator.initDataBase() - Server Is NOT Running");
//            Optional<ButtonType> result = MyAlert.alertOptionalConfirmation(150,
//                    "DBUtil.isServerRunning() = false",
//                    "Server ile Elaqe Qura Bilmirem",
//                    "Problemi Hell etmek ucun Serveri ishe salin eger bununla hell olmursa"
//                    + "\nAyarlari Redakte etmek ucun OK duymesine tiklayin");
//
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                System.out.println("ok");
//                DBUtil.showmySQLServerConfigView();
//
//                //Bu sebebden Yoxlamalar metoda kecirtdim
//                // yoxsa initDataBase() metodunun icinde hele if else ile hell ede bilirdim
//                // amma bele bir mesele var 
//                //serverle elaqe yoxdur ve istifadeciye ya cixmagi yada ob basib ayarlamagi teklfi edirem
//                // adam cixanda problem yoxdur X-basr yada cancell
//                //ammaadamok basanda Server ayarlama penceresi acilir
//                // adam ayarlari daxil etdikden sonra tekrar yoxlamaq lazimdir axi
//                //aydaa indi aglima gelir bu metodun yerine gorum hele
//                // initDataBase()-in ozunu cagirsaydim nece olardi?
//                checkServer();
//
//            } else {
//                System.exit(271);
//            }
//        }
//    }
//    public static void checkConnetion() {
//
//        if (DBUtil.mySQLHasConnetion()) {
//            System.out.println("Initializator.initDataBase() - Connection is Correct");
//
//        } else {
//            System.out.println("Initializator.initDataBase() - Connection is NOT Correct");
//
//            Optional<ButtonType> result = MyAlert.alertOptionalConfirmation(164,
//                    "DBUtil.hasConnetion() = false",
//                    "Server ishlek veziyyetdedir, Amma baglanti Qurmaq olmur",
//                    "Ayarlari Control etmek ucun OK duymesine tiklayin");
//
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                DBUtil.showmySQLServerConfigView();
//                checkConnetion();
//
//            } else {
//                System.exit(305);
//            }
//        }
//    }
//
//    public static void checkDBConnetion() {
//
//        if (DBUtil.mySQLHasDBConnetion()) {
//            System.out.println("Initializator.initDataBase() - DBConnection is Correct");
//
//        } else {
//            System.out.println("Initializator.initDataBase() - DBConnection is NOT Correct");
//        }
//
////        } else {
////            System.out.println("Initializator.initDataBase() - DBConnection is NOT Correct");
////
////            Optional<ButtonType> result = MyAlert.alertOptionalConfirmation(164,
////                    "DBUtil.mySQLHasDBConnetion() = false",
////                    "Server ishlek veziyyetdedir, Ve Serverle Baglanti Qurulub",
////                    "AMMA Melumat Bazasi Qurulmayib "
////                    + "\nMelumat bazasini Qurmaq ucun OK duymesini basin"
////                    + "\nAyatlar Penceresini acmaq ucun Cancell duymesini basin"
////                    + "\nEger Proqramnan cixmaq Isteyirsinizse Cancell duymesini basdiqdan sonra acilan pencereni baglayin");
////
////            if (result.isPresent() && result.get() == ButtonType.OK) {
////                // OK duymesini basdiqda BAzani QUR
////                setupDataBase();
////                System.exit(233);
////
////            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
////
////                //bu metod Acilan pencerede X-i basaraq baglandiqda true qaytarir
////                Boolean isClosed = DBUtil.showmySQLServerConfigView();
////                if (isClosed) {
////                    System.exit(241);
////                }
////
////                checkDBConnetion();
////            }
//    }
//    private static void setupDataBase() {
//
//        //sorgularimiz olan ArrayList-i aliriq
//        ArrayList<String> queryList = SQL.SetubDB.getQueriesList();
//
//        //Sorgularimizi ArrayListe yigib Sonra tek tek Gonderirem Sorgularimi ))
//        queryList.forEach((query) -> {
//            try {
//                DBUtil.mySQLExecuteUpdate(query);
//            } catch (SQLException ex) {
//                MyExceptionLogger.logException("SQLException - Initializator.setupDataBase()", ex);
//                MyAlert.alertAndExitByCodeAndContent(263, "Məlumat bazasının qurrashdirilması zamanı Xəta bash verdi\n"
//                        + "Xətanın Detayları: " + ex.getMessage());
//            }
//        });
//
//    }
}
