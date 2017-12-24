/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.database;

import com.salesoft.model.Invoice;
import com.salesoft.model.Product;
import com.salesoft.util.MyProperties;
import java.util.ArrayList;

/**
 * Bu Class-da SQL sorgularimi saxlayacam Cox Gozel Bir shekilde, her iki novde
 * biri PreparedStatement-ucun digeri ise, oz yontemim ile yani
 * String.replaceAll metodu ile, bu nece ishledyir? SQL sorguda ? (Sual)
 * isharesi yerine yaziram meselen String TEST_BY_TYPE_R = ("blablabla where
 * type=typeR") sonra hemin SQL-i aliram meselen SQL.TEST_BY_TYPE_R sonra ordaki
 * sozu deyishirem bucur, String SQL =
 * SQL.TEST_BY_TYPE_R.replaceAll("typeR","'url'"); neticede hazir SQL sorgu
 * aliram blablabla where type='url'; ve bu sorgunu gonderirem DBUtil
 * Classimizin dbExecuteQuery Funksiyasina ve bize lazim olan neticeni aliriq.
 * Bunu sirf DBUtil Classimla ishlemek ucun teshkil elemishem, cunki bu class
 * xoshuma geldi, ve bunun sayesinde elave kod yazmayacam, meselen
 * PreparedStatement kimi. Yoxlayaq gorek neqeder kodumuzu azaldacaq ve ishimi
 * asandlashdiracaq.
 *
 * @author Ramin
 */
public class SQL {

    private static final String dbName;

    static {
        //ilk Muracietde Melumat Bazamizin adini alaq
        dbName = MyProperties.getDBProperties().getDbName();
        System.err.println("DB Name: " + dbName);
        //istifade Yontemi 
        //      `" + dbName + "`.
    }

    /**
     * InvoiceItem Modeli ile aparilan emeliyatlarin SQL-sorgulari
     */
    public static class InvoiceItem {

        public static String GET_ALL_BY_INVOICE_ID(Integer id) {
            return "SELECT * FROM `" + dbName + "`.InvoiceItem WHERE history_id=" + id + " ORDER BY `id` DESC";
        }

        public static String INVOICEITEM_ADD_NEW = "INSERT INTO `" + dbName + "`.satish_list (history_id,p_id,p_name,p_say,p_qiymet,p_mebleg,p_barcode,p_qeyd,p_satishdan_evvelki_say) VALUES (history_idR, p_idR, 'p_nameR', p_sayR, p_qiymetR, p_meblegR, 'p_barcodeR', 'p_qeydR', p_satishdan_evvelki_sayR)";

        public static String INVOICEITEM_UPDATE_BY_ID = "UPDATE `" + dbName + "`.satish_list SET p_say='p_sayR', p_mebleg='p_meblegR' WHERE  id=idR;";

    }

    /**
     * InvoiceOldSQL - SQL
     */
    public static class InvoiceSQL {

        public static String CREATE(Invoice invoice) {
            return "INSERT INTO `" + dbName + "`.Invoice (customer,mebleg) VALUES ('" + invoice.getCustomerName() + "', " + invoice.getTotalPrice() + ")";
        }

        public static String UPDATE(Invoice invoice) {
            return "UPDATE `" + dbName + "`.Invoice SET `customerName`='" + invoice.getCustomerName() + "', `totalPrice`='" + invoice.getTotalPrice() + "' WHERE  `id`=" + invoice.getId();
        }

        public static String DELETE(Integer id) {
            return "";
        }

        public static String GET(Integer id) {
            return "SELECT * FROM `" + dbName + "`.Invoice WHERE id=" + id;
        }

        public static String GET_ALL() {
            return "SELECT * FROM `" + dbName + "`.Invoice ORDER BY `id` DESC LIMIT 1000";
        }

        public static String GET_ALL_BY_NAME_LIKE(String name) {
            return "SELECT * FROM `" + dbName + "`.Invoice WHERE customer LIKE '%" + name + "%'";
        }

        public static String GET_LAST_ID() {
            return "SELECT MAX(id) FROM `" + dbName + "`.Invoice LIMIT 1";
        }

    }

    /**
     * User - Obyektinin Sorgulari
     */
    public static class UserSQL {

        public static String LOGIN(String UsrName, String Password) {
            return "SELECT * FROM `" + dbName + "`.User where UsrName='" + UsrName + "' and Password='" + Password + "' and Status=1";
        }

        public static String REGISTRATON(String userName, String userPassword, String fullName, Integer status) {
            return "INSERT INTO `" + dbName + "`.User (`UsrName`, `FullName`, `Password`, `Status`) VALUES ('" + userName + "', '" + fullName + "', '" + userPassword + "', '" + status + "');";
        }

    }

    /**
     * PurchaseProductSQL - Obyektimizin SQL Sorgulari
     */
    public static class PurchaseProductSQL {

        public static String CREATE(String purchaseDate, String totalPrice, String product_id, String product_name, String product_qty, String product_purchasePrice, String product_barCode, String product_note) {
            return "INSERT INTO `" + dbName + "`.PurchaseProduct (`purchaseDate`, `totalPrice`, `product_id`, `product_name`, `product_qty`, `product_purchasePrice`, `product_barCode`, `product_note`) "
                    + "VALUES ('" + purchaseDate + "', '" + totalPrice + "', '" + product_id + "', '" + product_name + "', '" + product_qty + "', '" + product_purchasePrice + "', '" + product_barCode + "','" + product_note + "');";
        }

        public static String UPDATE() {
            return "";
        }

        public static String DELETE(Integer id) {
            return "";
        }

        public static String GET(Integer id) {
            return "SELECT * FROM `" + dbName + "`.PurchaseProduct WHERE id=" + id;
        }

        public static String GET_ALL() {
            return "SELECT * FROM `" + dbName + "`.PurchaseProduct ORDER BY `id` DESC LIMIT 1000";
        }

    }

    /**
     * Bazamizi Qurmaq ucun istifade olunan Sorgular
     */
    public static class SetubDB {

        /**
         * Class-imizin Esas Sorgulari qaytarma Funksiyasidir
         *
         * @return
         */
        public static ArrayList<String> getQueriesList() {

            ArrayList<String> queryList = new ArrayList();

            queryList.add("CREATE DATABASE IF NOT EXISTS `" + dbName + "` DEFAULT CHARACTER SET utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`PurchaseProduct` (\n"
                    // Ilk 3 Sutun PurchaseProduct - Obyektine Mexsusdur
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `purchaseDate` text,\n"
                    + "  `totalPrice` double DEFAULT NULL,\n"
                    // Qalan Propertiler ise PurchaseProduct - un icinde olan ProductSQL obyektine mexsusdur
                    + "  `product_id` int(11) DEFAULT NULL,\n"
                    + "  `product_name` text,\n"
                    + "  `product_qty` int(11) DEFAULT NULL,\n"
                    + "  `product_purchasePrice` double DEFAULT NULL,\n"
                    + "  `product_barCode` text,\n"
                    + "  `product_note` text,\n"
                    // Bu Sutun ise Obyektle hec bir elaqesi yoxdur ve Cedvele Melumatin yazilma Vaxtini Yadda saxlayir
                    + "  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`Product` (\n"
                    //Bunlar Product - Obyektinin Propertileridir 
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `name` text,\n"
                    + "  `qty` int(11) DEFAULT NULL,\n"
                    + "  `purchasePrice` double DEFAULT NULL,\n"
                    + "  `barCode` text,\n"
                    + "  `note` text,\n"
                    //Bunun ise Obyektle hec bir elaqesi yoxdur ))
                    + "  `timeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`Invoice` (\n"
                    //Object Data = Invoice
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `customerName` text,\n"
                    + "  `totalPrice` double DEFAULT NULL,\n"
                    //Additional Data
                    + "  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`InvoiceItem` (\n"
                    //Object DATA = InvoiceItem
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `history_id` int(11) DEFAULT NULL,\n"
                    //Inner Object Data = Product
                    + "  `p_id` int(11) DEFAULT NULL,\n"
                    + "  `p_name` text,\n"
                    + "  `p_say` int(11) DEFAULT NULL,\n"
                    + "  `p_qiymet` double DEFAULT NULL,\n"
                    + "  `p_mebleg` double DEFAULT NULL,\n"
                    + "  `p_barcode` text,\n"
                    + "  `p_qeyd` text,\n"
                    //Additional Data
                    + "  `p_satishdan_evvelki_say` int(11) DEFAULT NULL,\n"
                    + "  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    + "  `updateTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`User` (\n"
                    //Object DATA = User
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `UsrName` text,\n"
                    + "  `FullName` text,\n"
                    + "  `Password` text,\n"
                    + "  `Status` tinyint(1) DEFAULT '0',\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE KEY `Id` (`Id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            return queryList;
        }

    }

    /**
     * ProductSQL Modeli ile aparilan emeliyatlarin SQL-sorgulari
     */
    public static class ProductSQL {

        public static String CREATE(Product product) {
            return "INSERT INTO `" + dbName + "`.`Product` (name, qty, purchasePrice, barCode, note) "
                    + "VALUES ('" + product.getName() + "', '" + product.getQty().toString() + "', '" + product.getPurchasePrice().toString() + "', '" + product.getBarCode() + "', '" + product.getNote() + "')";
        }

        public static String UPDATE(Product product) {
            return "UPDATE `" + dbName + "`.`Product` SET `name`='" + product.getName() + "', `qty`='" + product.getQty().toString() + "', `purchasePrice`='" + product.getPurchasePrice().toString() + "', `barCode`='" + product.getBarCode() + "', `note`='" + product.getNote() + "' WHERE  `id`=" + product.getId() + ";";
        }

        public static String DELETE(Integer id) {
            return "DELETE FROM `" + dbName + "`.`Product` WHERE id=" + id;
        }

        public static String GET(Integer id) {
            return "SELECT * FROM `" + dbName + "`.Product WHERE id=" + id;
        }

        public static String GET(String barCode) {
            return "SELECT * FROM `" + dbName + "`.Product WHERE barCode=" + barCode;
        }

        public static String GET_ALL() {
            return "SELECT * FROM `" + dbName + "`.Product ORDER BY `id` DESC LIMIT 1000";
        }

        public static String SEARCH_BY_NAME_LIKE(String name) {
            return "SELECT * FROM `" + dbName + "`.`Product` WHERE name LIKE '%" + name + "%'";
        }

        public static String SEARCH_BY_BARODE(String barCode) {
            return "SELECT * FROM `" + dbName + "`.Product WHERE barCode=" + barCode;
        }

        /**
         * SQL For PreparedStatement, id=? (SELECT * ... WHERE id=?)
         */
        public static final String PRODUCT_GET_BY_ID_P = "SELECT * FROM `" + dbName + "`.`Product` WHERE id=?";

        /**
         * SQL For ReplaceAll, id=idR (SELECT * ... WHERE id=idR)
         */
        public static final String PRODUCT_GET_BY_ID_R = "SELECT * FROM `" + dbName + "`.`Product` WHERE id=idR";

        /**
         * SQL For PreparedStatement, barcode=?
         */
        public static final String PRODUCT_GET_BY_BARCODE_P = "SELECT * FROM `" + dbName + "`.`Product` WHERE barcode=?";

        /**
         * SQL For ReplaceAll, barcode=barcodeR
         */
        public static final String PRODUCT_GET_BY_BARCODE_R = "SELECT * FROM `" + dbName + "`.`Product` WHERE barcode='barcodeR'";

        /**
         * SQL For PreparedStatement, barcode=?
         */
        public static final String PRODUCT_GEL_ALL_BY_BARCODE_P = "SELECT * FROM `" + dbName + "`.`Product` WHERE barcode=?";

        /**
         * SQL For ReplaceAll, barcode=barcodeR
         */
        public static final String PRODUCT_GEL_ALL_BY_BARCODE_R = "SELECT * FROM `" + dbName + "`.`Product` WHERE barcode=barcodeR";

    }

}
