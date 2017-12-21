/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.MainApp;
import com.salesoft.database.DBUtil;
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
        initProperties();

    }

    private static void initFolders() {
        initLogFolders();
        initPropertiesFolders();

    }

    private static void initProperties() {
        initDBProperties();
        initUIProperties();

    }

    private static void initLogFolders() {
        File f = new File("Log\\Exceptions\\");
        if (!f.exists()) {
            System.err.println("Creating Folder for Exception Logs ");
            f.mkdirs();
        }
    }

    private static void initPropertiesFolders() {
        File folder = new File("Properties\\");
        if (!folder.exists()) {
            System.err.println("Creating Folder for Properties");
            folder.mkdirs();
        }
    }

    /**
     * DBProperties.properties - faylimiz eger yoxdursa onu hazirlayir, Bu
     * Proqrami ilk defe ishe salanda olur, ve ilkin melumatlari yazir fayla
     */
    private static void initDBProperties() {
        File propertiesFile = new File("Properties\\DBProperties.properties");
        if (!propertiesFile.exists()) {
            System.err.println("Creating 'DBProperties.properties' - file  in 'Properties' folder");

            OutputStream output;
            Properties properties = new Properties();

            try {
                output = new FileOutputStream("Properties/DBProperties.properties");
                properties.setProperty("db.host", "localhost");
                properties.setProperty("db.port", "3306");
                properties.setProperty("db.name", "testdb");
                properties.setProperty("db.user", "root");
                properties.setProperty("db.crypted.password", "password");
                properties.store(output, null);
                output.close();
            } catch (FileNotFoundException ex) {
                System.out.println("FileNotFoundException -  Initializator.initDBProperties(): " + ex);
                MyLogger.logException("FileNotFoundException - Initializator.initDBProperties()", ex);

                MyAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initDBProperties() - FileNotFoundException");
            } catch (IOException ex) {
                System.out.println("IOException -  Initializator.initDBProperties(): " + ex);
                MyLogger.logException("IOException - Initializator.initDBProperties()", ex);

                MyAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initDBProperties() - IOException");

            }

        }

    }

    /**
     * UIProperties initialize
     */
    private static void initUIProperties() {

        File propertiesFile = new File("Properties\\UIProperties.properties");
        if (!propertiesFile.exists()) {
            System.err.println("Creating 'UIProperties.properties'  in 'Properties' folder");

            OutputStream output;
            Properties properties = new Properties();

            try {
                output = new FileOutputStream("Properties/UIProperties.properties");
                properties.setProperty("application.Title", "SaleSoft - a Xos G\u0259lmisiniz - Login Bolumu");
                properties.store(output, null);
                output.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("");
                MyAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initUIProperties() - FileNotFoundException");
            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                MyAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initUIProperties() - IOException");

            }

        }

    }

    public static void initMyProperties() {
        MyProperties.init();

    }

    /**
     * Metod Serverin Veziyyetini yoxlayir sonra Serverle elaqe qurmaga
     * calishir, daha sonra ise Melumat bazasinin varligini yoxlayir Eger
     * yoxdursa Qurmagi teklif edir.
     */
    public static void initDataBase() {

        // 1- ci Serverimizin Ishlek veziyyetde olub olmadigini yoxlayaq
        checkServer();

        // 2-ci Serverimizle Elaqe Qurmaga calishaq
        checkConnetion();

        //3-ci Melumat Bazamiza qoshulmaga calishaq Baza Adini istifade ederek
        checkDBConnetion();

    }

    public static void checkServer() {

        if (DBUtil.isServerRunning()) {
            System.out.println("Initializator.initDataBase() - Server Is Running");

        } else {
            System.out.println("Initializator.initDataBase() - Server Is NOT Running");
            Optional<ButtonType> result = MyAlert.alertOptionalConfirmation(150,
                    "DBUtil.isServerRunning() = false",
                    "Server ile Elaqe Qura Bilmirem",
                    "Problemi Hell etmek ucun Serveri ishe salin eger bununla hell olmursa"
                    + "\nAyarlari Redakte etmek ucun OK duymesine tiklayin");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("ok");
                DBUtil.showServerConfigView();

                //Bu sebebden Yoxlamalar metoda kecirtdim
                // yoxsa initDataBase() metodunun icinde hele if else ile hell ede bilirdim
                // amma bele bir mesele var 
                //serverle elaqe yoxdur ve istifadeciye ya cixmagi yada ob basib ayarlamagi teklfi edirem
                // adam cixanda problem yoxdur X-basr yada cancell
                //ammaadamok basanda Server ayarlama penceresi acilir
                // adam ayarlari daxil etdikden sonra tekrar yoxlamaq lazimdir axi
                //aydaa indi aglima gelir bu metodun yerine gorum hele
                // initDataBase()-in ozunu cagirsaydim nece olardi?
                checkServer();

            } else {
                System.exit(271);
            }
        }
    }

    public static void checkConnetion() {

        if (DBUtil.hasConnetion()) {
            System.out.println("Initializator.initDataBase() - Connection is Correct");

        } else {
            System.out.println("Initializator.initDataBase() - Connection is NOT Correct");

            Optional<ButtonType> result = MyAlert.alertOptionalConfirmation(164,
                    "DBUtil.hasConnetion() = false",
                    "Server ishlek veziyyetdedir, Amma baglanti Qurmaq olmur",
                    "Ayarlari Control etmek ucun OK duymesine tiklayin");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBUtil.showServerConfigView();
                checkConnetion();

            } else {
                System.exit(305);
            }
        }
    }

    public static void checkDBConnetion() {

        if (DBUtil.hasDBConnetion()) {
            System.out.println("Initializator.initDataBase() - DBConnection is Correct");

        } else {
            System.out.println("Initializator.initDataBase() - DBConnection is NOT Correct");

            Optional<ButtonType> result = MyAlert.alertOptionalConfirmation(164,
                    "DBUtil.hasDBConnetion() = false",
                    "Server ishlek veziyyetdedir, Ve Serverle Baglanti Qurulub",
                    "AMMA Melumat Bazasi Qurulmayib "
                    + "\nMelumat bazasini Qurmaq ucun OK duymesini basin"
                    + "\nAyatlar Penceresini acmaq ucun Cancell duymesini basin"
                    + "\nEger Proqramnan cixmaq Isteyirsinizse Cancell duymesini basdiqdan sonra acilan pencereni baglayin");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // OK duymesini basdiqda BAzani QUR
                setupDataBase();
                System.exit(233);

            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {

                //bu metod Acilan pencerede X-i basaraq baglandiqda true qaytarir
                Boolean isClosed = DBUtil.showServerConfigView();
                if (isClosed) {
                    System.exit(241);
                }

                checkDBConnetion();
            }
        }
    }

    private static void setupDataBase() {
        String dbName = MyProperties.getDBProperties().getDbName();
        System.err.println("DB Name: " + dbName);

        ArrayList<String> queryList = new ArrayList();

        queryList.add("CREATE DATABASE IF NOT EXISTS `" + dbName + "` DEFAULT CHARACTER SET utf8;");

        queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`alish_list` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `p_id` int(11) DEFAULT NULL,\n"
                + "  `p_name` text,\n"
                + "  `p_qty` int(11) DEFAULT NULL,\n"
                + "  `p_purchasePrice` double DEFAULT NULL,\n"
                + "  `p_totalPrice` double DEFAULT NULL,\n"
                + "  `p_note` text,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE KEY `id` (`id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

        queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`allproperties` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `type` text,\n"
                + "  `3` text,\n"
                + "  `4` text,\n"
                + "  `5` text,\n"
                + "  `6` text,\n"
                + "  `7` text,\n"
                + "  `8` text,\n"
                + "  `9` text,\n"
                + "  `10` text,\n"
                + "  `11` text,\n"
                + "  `12` text,\n"
                + "  `13` text,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE KEY `id` (`id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

        queryList.add("INSERT INTO `" + dbName + "`.`allproperties` (`id`, `type`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`) VALUES\n"
                + "	(1, 'urlKey', 'Login Form', 'ApplicationForm', 'HomeForm', 'ProductTable', 'ProductSaleCart', 'AnbarRootLayout', 'ProductPurchse', 'SaleRootLayout', 'SaleInvoiceTable', 'SaleInvoiceDetailsTable', NULL),\n"
                + "	(2, 'url', 'view/Login.fxml', 'view/Application.fxml', 'view/HOME.fxml', 'view/anbar/ProductTable.fxml', 'view/sale/ProductSaleCart.fxml', 'view/AnbarRootLayout.fxml', 'view/anbar/ProductPurchse.fxml', 'view/SaleRootLayout.fxml', 'view/sale/SaleInvoiceTable.fxml', 'view/sale/SaleInvoiceDetailsTable.fxml', 'view/anbar/PurchaseInvoiceTable.fxml');");

        queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`product_list` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `ad` text NOT NULL,\n"
                + "  `say` int(11) DEFAULT NULL,\n"
                + "  `alishqiymeti` double DEFAULT NULL,\n"
                + "  `barcode` text,\n"
                + "  `qeyd` text,\n"
                + "  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE KEY `id` (`id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

        queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`satish_history` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `customer` text,\n"
                + "  `mebleg` double DEFAULT NULL,\n"
                + "  `odenish` double DEFAULT NULL,\n"
                + "  `qaliq` double DEFAULT NULL,\n"
                + "  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE KEY `id` (`id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

        queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`satish_list` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `history_id` int(11) DEFAULT NULL,\n"
                + "  `p_id` int(11) DEFAULT NULL,\n"
                + "  `p_name` text,\n"
                + "  `p_say` int(11) DEFAULT NULL,\n"
                + "  `p_qiymet` double DEFAULT NULL,\n"
                + "  `p_mebleg` double DEFAULT NULL,\n"
                + "  `p_barcode` text,\n"
                + "  `p_qeyd` text,\n"
                + "  `p_satishdan_evvelki_say` int(11) DEFAULT NULL,\n"
                + "  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                + "  `updateTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE KEY `id` (`id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

        queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`user` (\n"
                + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `UsrName` varchar(20) NOT NULL,\n"
                + "  `FullName` varchar(100) DEFAULT NULL,\n"
                + "  `EmailAddress` varchar(100) DEFAULT NULL,\n"
                + "  `ContactNumber` varchar(100) DEFAULT NULL,\n"
                + "  `Salary` double DEFAULT NULL,\n"
                + "  `Address` text,\n"
                + "  `Password` varchar(45) DEFAULT NULL,\n"
                + "  `Status` tinyint(1) NOT NULL DEFAULT '0',\n"
                + "  `UserImage` mediumblob,\n"
                + "  `Date` date NOT NULL,\n"
                + "  `CreatorId` int(11) DEFAULT NULL,\n"
                + "  PRIMARY KEY (`Id`),\n"
                + "  UNIQUE KEY `Id` (`Id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

        //Sorgularimizi ArrayListe yigib Sonra tek tek Gonderirem Sorgularimi ))
        queryList.forEach((query) -> {
            try {
                DBUtil.directExecuteUpdate(query);
            } catch (SQLException ex) {
                System.out.println("SQLException -  Initializator.setupDataBase(): " + ex);
                MyLogger.logException("SQLException - Initializator.setupDataBase()", ex);
            }
        });

    }
}
