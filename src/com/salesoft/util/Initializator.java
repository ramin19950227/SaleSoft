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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

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
        System.out.println("com.salesoft.util.Initializator.initFoldersAndFiles()");
        //qovluqlarimizi yoxlayaq her shey yerindedirse
        initFolders();

        //properties fayllarmizi yoxlayaq
        initProperties();

    }

    private static void initFolders() {
        System.out.println("com.salesoft.util.Initializator.initFolders()");
        initLogFolders();
        initPropertiesFolders();

    }

    private static void initProperties() {
        System.out.println("com.salesoft.util.Initializator.initProperties()");
        initDBProperties();
        initUIProperties();

    }

    private static void initLogFolders() {
        System.out.println("com.salesoft.util.Initializator.initLogFolders()");
        File f = new File("Log\\Exceptions\\");
        if (!f.exists()) {
            System.err.println("Creating Folder for Exception Logs ");
            f.mkdirs();
        }
    }

    private static void initPropertiesFolders() {
        System.out.println("com.salesoft.util.Initializator.initPropertiesFolders()");
        File folder = new File("Properties\\");
        if (!folder.exists()) {
            System.err.println("Creating Folder for Properties");
            folder.mkdirs();
        }
    }

    /**
     * DBProperties.properties - faylimiz eger yoxdursa onu hazirlayir, Bu
     * Proqrami ilk defe ishe salanda olur
     */
    private static void initDBProperties() {
        System.out.println("com.salesoft.util.Initializator.initDBProperties()");
        File propertiesFile = new File("Properties\\DBProperties.properties");
        if (!propertiesFile.exists()) {
            System.err.println("Creating 'DBProperties.properties'  in 'Properties' folder");

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
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("");
                MyAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initDBProperties() - FileNotFoundException");
            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                MyAlert.alertAndExitByCodeAndContent(0, "FirstStartInitialization.initDBProperties() - IOException");

            }

        }

    }

    /**
     * UIProperties initialize
     */
    private static void initUIProperties() {
        System.out.println("com.salesoft.util.Initializator.initUIProperties()");

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

    public static void initDataBase() {
        System.out.println("com.salesoft.util.Initializator.initDataBase()");

        //baza ile elaqeni yoxlayaq varsa davam edek
        //yoxdursa ayarlama penceresini acacaq
        while (!DBUtil.hasConnetion()) {

            MyAlert.alertContent(134, "Baza Ayarlari Dogru Deyil, Zehmet Olmasa Dogru ayarlari Daxil Edin");

            Stage nStage = new Stage();
            nStage.setScene(MyFXMLLoader.getSceneFromURL(MainApp.class.getResource("view/Server.fxml")));
            nStage.setMaximized(false);
            nStage.setTitle("Servere Qoshulma Ayarlari - Sale Soft");
            nStage.showAndWait();

            // bu metodu cagiraraq Istifadecinin daxil etdiyi properties faylindaki yeni melumatlari yukleyirik
            // ki, yeni melumatlarla yeniden qoshulmaga calishaq
            MyProperties.loadDBProperties();

        }
        // Elaqe Quruldu Deye
        MyAlert.alertAndExitByCodeAndContent(134, "Baza Qoshuldu OK");

        String db = "CREATE DATABASE IF NOT EXISTS `testdb`.`testdb` DEFAULT CHARACTER SET utf8;";

        String table1 = "CREATE TABLE IF NOT EXISTS `testdb`.`alish_list` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `p_id` int(11) DEFAULT NULL,\n"
                + "  `p_name` text,\n"
                + "  `p_qty` int(11) DEFAULT NULL,\n"
                + "  `p_purchasePrice` double DEFAULT NULL,\n"
                + "  `p_totalPrice` double DEFAULT NULL,\n"
                + "  `p_note` text,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE KEY `id` (`id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        String table2 = "CREATE TABLE IF NOT EXISTS `testdb`.`allproperties` (\n"
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
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        String table2i = "INSERT INTO `testdb`.`allproperties` (`id`, `type`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`) VALUES\n"
                + "	(1, 'urlKey', 'Login Form', 'ApplicationForm', 'HomeForm', 'ProductTable', 'ProductSaleCart', 'AnbarRootLayout', 'ProductPurchse', 'SaleRootLayout', 'SaleInvoiceTable', 'SaleInvoiceDetailsTable', NULL),\n"
                + "	(2, 'url', 'view/Login.fxml', 'view/Application.fxml', 'view/HOME.fxml', 'view/anbar/ProductTable.fxml', 'view/sale/ProductSaleCart.fxml', 'view/AnbarRootLayout.fxml', 'view/anbar/ProductPurchse.fxml', 'view/SaleRootLayout.fxml', 'view/sale/SaleInvoiceTable.fxml', 'view/sale/SaleInvoiceDetailsTable.fxml', 'view/anbar/PurchaseInvoiceTable.fxml');";

        String table3 = "CREATE TABLE IF NOT EXISTS `testdb`.`product_list` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `ad` text NOT NULL,\n"
                + "  `say` int(11) DEFAULT NULL,\n"
                + "  `alishqiymeti` double DEFAULT NULL,\n"
                + "  `barcode` text,\n"
                + "  `qeyd` text,\n"
                + "  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE KEY `id` (`id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        String table4 = "CREATE TABLE IF NOT EXISTS `testdb`.`satish_history` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `customer` text,\n"
                + "  `mebleg` double DEFAULT NULL,\n"
                + "  `odenish` double DEFAULT NULL,\n"
                + "  `qaliq` double DEFAULT NULL,\n"
                + "  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE KEY `id` (`id`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        String table5 = "CREATE TABLE IF NOT EXISTS `testdb`.`satish_list` (\n"
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
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        String table6 = "CREATE TABLE IF NOT EXISTS `testdb`.`user` (\n"
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
                + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

    }

    public static void initMyProperties() {
        System.out.println("com.salesoft.util.Initializator.initMyProperties()");
        MyProperties.init();

    }
}
