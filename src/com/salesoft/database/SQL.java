/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.database;

import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import com.salesoft.model.Product;
import com.salesoft.util.MyDateConverter;
import com.salesoft.util.MyProperties;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Bu Class-da SQL sorgularimi yer alir
 *
 * @author Ramin
 */
public class SQL {

    private static final String DB_NAME = "";

//    static {
//        //ilk Muracietde Melumat Bazamizin adini alaq
//        DB_NAME = MyProperties.getDBProperties().getDbName();
//        System.err.println("DB Name: " + DB_NAME);
//        //istifade Yontemi 
//        //      `" + dbName + "`.
//    }
    /**
     * InvoiceItemSQL Modeli ile aparilan emeliyatlarin SQL-sorgulari
     */
    public static class InvoiceItemSQL {

        public static String CREATE(InvoiceItem item) {
            Product p = item.getProduct();
            return "INSERT INTO `" + DB_NAME + "`.InvoiceItem "
                    + "(`invoiceId`, `totalPrice`, "
                    + "`product_id`, `product_name`, `product_qty`, `product_purchasePrice`, `product_barCode`, `product_note`) "
                    + "VALUES ('" + item.getInvoiceId() + "', '" + item.getTotalPrice() + "', "
                    + "'" + p.getId() + "', '" + p.getName() + "', '" + p.getQty() + "', '" + p.getPurchasePrice() + "', '" + p.getBarCode() + "', '" + p.getNote() + "');";
        }

        public static String CREATE_FOR_ACCESS(InvoiceItem item) {
            Product p = item.getProduct();
            return "INSERT INTO InvoiceItem "
                    + "(`invoiceId`, `totalPrice`, "
                    + "`product_id`, `product_name`, `product_qty`, `product_purchasePrice`, `product_salePrice`, `product_barCode`, `product_note`) "
                    + "VALUES ('" + item.getInvoiceId() + "', '" + item.getTotalPrice() + "', "
                    + "'" + p.getId() + "', '" + p.getName() + "', '" + p.getQty() + "', '" + p.getPurchasePrice() + "', '" + p.getSalePrice() + "', '" + p.getBarCode() + "', '" + p.getNote() + "');";
        }

        public static String UPDATE(InvoiceItem item) {
            return "UPDATE `" + DB_NAME + "`.InvoiceItem SET product_qty='" + item.getProduct().getQty() + "', totalPrice='" + item.getTotalPrice() + "' WHERE  id=" + item.getId();
        }

        public static String UPDATE_FOR_ACCESS(InvoiceItem item) {
            return "UPDATE InvoiceItem SET product_qty='" + item.getProduct().getQty() + "', totalPrice='" + item.getTotalPrice() + "' WHERE  id=" + item.getId();
        }

        public static String GET_ALL_BY_INVOICE_ID(Integer id) {
            return "SELECT * FROM `" + DB_NAME + "`.InvoiceItem WHERE invoiceId=" + id + " ORDER BY `id` DESC";
        }

    }

    /**
     * InvoiceOldSQL - SQL
     */
    public static class InvoiceSQL {

        public static String CREATE(Invoice invoice) {
            return "INSERT INTO `" + DB_NAME + "`.Invoice (customerName,totalPrice) VALUES ('" + invoice.getCustomerName() + "', " + invoice.getTotalPrice() + ")";
        }

        public static String CREATE_FOR_ACCES(Invoice invoice) {
            return "INSERT INTO Invoice (customerName,totalPrice, dateTime) VALUES ('" + invoice.getCustomerName() + "', " + invoice.getTotalPrice() + ", '" + new Timestamp(invoice.getDate().getTime()) + "')";
        }

        public static String UPDATE(Invoice invoice) {
            return "UPDATE `" + DB_NAME + "`.Invoice SET `customerName`='" + invoice.getCustomerName() + "', `totalPrice`='" + invoice.getTotalPrice() + "' WHERE  `id`=" + invoice.getId();
        }

        public static String UPDATE_FOR_ACCESS(Invoice invoice) {
            return "UPDATE Invoice SET `customerName`='" + invoice.getCustomerName() + "', `totalPrice`='" + invoice.getTotalPrice() + "' WHERE  `id`=" + invoice.getId();
        }

        public static String DELETE(Integer id) {
            return "";
        }

        public static String GET(Integer id) {
            return "SELECT * FROM `" + DB_NAME + "`.Invoice WHERE id=" + id;
        }

        public static String GET_ALL() {
            return "SELECT * FROM `" + DB_NAME + "`.Invoice ORDER BY `id` DESC LIMIT 1000";
        }

        public static String GET_ALL_BY_NAME_LIKE(String name) {
            return "SELECT * FROM `" + DB_NAME + "`.Invoice WHERE customer LIKE '%" + name + "%'";
        }

        public static String GET_LAST_ID() {
            return "SELECT MAX(id) FROM `" + DB_NAME + "`.Invoice LIMIT 1";
        }

    }

    /**
     * User - Obyektinin Sorgulari
     */
    public static class UserSQL {

        public static String LOGIN(String UsrName, String Password) {
            return "SELECT * FROM `" + DB_NAME + "`.User where UsrName='" + UsrName + "' and Password='" + Password + "' and Status=1";
        }

        public static String REGISTRATON(String userName, String userPassword, String fullName, Integer status) {
            return "INSERT INTO `" + DB_NAME + "`.User (`UsrName`, `FullName`, `Password`, `Status`) VALUES ('" + userName + "', '" + fullName + "', '" + userPassword + "', '" + status + "');";
        }

    }

    /**
     * PurchaseProductSQL - Obyektimizin SQL Sorgulari
     */
    public static class PurchaseProductSQL {

        public static String CREATE(java.util.Date purchaseDate, String totalPrice, String product_id, String product_name, String product_qty, String product_purchasePrice, String product_barCode, String product_note) {
            return "INSERT INTO `" + DB_NAME + "`.PurchaseProduct (`purchaseDate`, `totalPrice`, `product_id`, `product_name`, `product_qty`, `product_purchasePrice`, `product_barCode`, `product_note`) "
                    + "VALUES ('" + MyDateConverter.utilDate.toStringCustomFormat(purchaseDate, "yyyy-MM-dd") + "', '" + totalPrice + "', '" + product_id + "', '" + product_name + "', '" + product_qty + "', '" + product_purchasePrice + "', '" + product_barCode + "','" + product_note + "');";
        }

        public static String CREATE_FOR_ACCES(java.util.Date purchaseDate, String totalPrice, String product_id, String product_name, String product_qty, String product_purchasePrice, String product_barCode, String product_note) {
            return "INSERT INTO PurchaseProduct VALUES (null, '" + MyDateConverter.utilDate.toStringCustomFormat(purchaseDate, "yyyy-MM-dd") + "', '" + totalPrice + "', '" + product_id + "', '" + product_name + "', '" + product_qty + "', '" + product_purchasePrice + "', '" + product_barCode + "','" + product_note + "');";
        }

        public static String UPDATE() {
            return "";
        }

        public static String DELETE(Integer id) {
            return "";
        }

        public static String GET(Integer id) {
            return "SELECT * FROM `" + DB_NAME + "`.PurchaseProduct WHERE id=" + id;
        }

        public static String GET_ALL() {
            return "SELECT * FROM `" + DB_NAME + "`.PurchaseProduct ORDER BY `id` DESC LIMIT 1000";
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

            queryList.add("CREATE DATABASE IF NOT EXISTS `" + DB_NAME + "` DEFAULT CHARACTER SET utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + DB_NAME + "`.`PurchaseProduct` (\n"
                    //Object DATA = PurchaseProduct
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `purchaseDate` text,\n"
                    + "  `totalPrice` double DEFAULT NULL,\n"
                    //Inner Object Data = Product
                    + "  `product_id` int(11) DEFAULT NULL,\n"
                    + "  `product_name` text,\n"
                    + "  `product_qty` int(11) DEFAULT NULL,\n"
                    + "  `product_purchasePrice` double DEFAULT NULL,\n"
                    + "  `product_barCode` text,\n"
                    + "  `product_note` text,\n"
                    //Additional Data
                    + "  `createTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    + "  `lastUpdateTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + DB_NAME + "`.`Product` (\n"
                    //Object DATA = Product
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `name` text,\n"
                    + "  `qty` int(11) DEFAULT NULL,\n"
                    + "  `purchasePrice` double DEFAULT NULL,\n"
                    + "  `barCode` text,\n"
                    + "  `note` text,\n"
                    //Additional Data
                    + "  `createTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    + "  `lastUpdateTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + DB_NAME + "`.`Invoice` (\n"
                    //Object Data = Invoice
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `customerName` text,\n"
                    + "  `totalPrice` double DEFAULT NULL,\n"
                    + "  `dateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    //Additional Data
                    + "  `createTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    + "  `lastUpdateTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + DB_NAME + "`.`InvoiceItem` (\n"
                    //Object DATA = InvoiceItemSQL
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `invoiceId` int(11) DEFAULT NULL,\n"
                    + "  `totalPrice` double DEFAULT NULL,\n"
                    //Inner Object Data = Product
                    + "  `product_id` int(11) DEFAULT NULL,\n"
                    + "  `product_name` text,\n"
                    + "  `product_qty` int(11) DEFAULT NULL,\n"
                    + "  `product_purchasePrice` double DEFAULT NULL,\n"
                    + "  `product_barCode` text,\n"
                    + "  `product_note` text,\n"
                    //Additional Data
                    + "  `createTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    + "  `lastUpdateTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + DB_NAME + "`.`User` (\n"
                    //Object DATA = User
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `UsrName` text,\n"
                    + "  `FullName` text,\n"
                    + "  `Password` text,\n"
                    + "  `Status` tinyint(1) DEFAULT '0',\n"
                    //Additional Data
                    + "  `createTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    + "  `lastUpdateTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE KEY `Id` (`Id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + DB_NAME + "`.`UserOperationLogger` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `userName` text,\n"
                    + "  `sql` text,\n"
                    + "  `createTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

            return queryList;
        }

    }

    /**
     * ProductSQL Modeli ile aparilan emeliyatlarin SQL-sorgulari
     */
    public static class ProductSQL {

        public static String CREATE(Product product) {
            return "INSERT INTO `" + DB_NAME + "`.`Product` (name, qty, purchasePrice, barCode, note) "
                    + "VALUES ('" + product.getName() + "', '" + product.getQty().toString() + "', '" + product.getPurchasePrice().toString() + "', '" + product.getBarCode() + "', '" + product.getNote() + "')";
        }

        public static String UPDATE(Product product) {
            return "UPDATE `" + DB_NAME + "`.`Product` SET `name`='" + product.getName() + "', `qty`='" + product.getQty().toString() + "', `purchasePrice`='" + product.getPurchasePrice().toString() + "', `barCode`='" + product.getBarCode() + "', `note`='" + product.getNote() + "' WHERE  `id`=" + product.getId() + ";";
        }

        public static String DELETE(Integer id) {
            return "DELETE FROM `" + DB_NAME + "`.`Product` WHERE id=" + id;
        }

        public static String GET(Integer id) {
            return "SELECT * FROM `" + DB_NAME + "`.Product WHERE id=" + id;
        }

        public static String GET(String barCode) {
            return "SELECT * FROM `" + DB_NAME + "`.Product WHERE barCode='" + barCode + "' ORDER BY `id` DESC LIMIT 1";
        }

        public static String GET_ALL() {
            return "SELECT * FROM `" + DB_NAME + "`.Product ORDER BY `id` DESC LIMIT 1000";
        }

        public static String SEARCH_BY_NAME_LIKE(String name) {
            return "SELECT * FROM `" + DB_NAME + "`.`Product` WHERE name LIKE '%" + name + "%'";
        }

        public static String SEARCH_BY_BARODE(String barCode) {
            return "SELECT * FROM `" + DB_NAME + "`.Product WHERE barCode='" + barCode + "'";
        }

        /**
         * SQL For PreparedStatement, id=? (SELECT * ... WHERE id=?)
         */
        public static final String PRODUCT_GET_BY_ID_P = "SELECT * FROM `" + DB_NAME + "`.`Product` WHERE id=?";

        /**
         * SQL For ReplaceAll, id=idR (SELECT * ... WHERE id=idR)
         */
        public static final String PRODUCT_GET_BY_ID_R = "SELECT * FROM `" + DB_NAME + "`.`Product` WHERE id=idR";

        /**
         * SQL For PreparedStatement, barcode=?
         */
        public static final String PRODUCT_GET_BY_BARCODE_P = "SELECT * FROM `" + DB_NAME + "`.`Product` WHERE barcode=?";

        /**
         * SQL For ReplaceAll, barcode=barcodeR
         */
        public static final String PRODUCT_GET_BY_BARCODE_R = "SELECT * FROM `" + DB_NAME + "`.`Product` WHERE barcode='barcodeR'";

        /**
         * SQL For PreparedStatement, barcode=?
         */
        public static final String PRODUCT_GEL_ALL_BY_BARCODE_P = "SELECT * FROM `" + DB_NAME + "`.`Product` WHERE barcode=?";

        /**
         * SQL For ReplaceAll, barcode=barcodeR
         */
        public static final String PRODUCT_GEL_ALL_BY_BARCODE_R = "SELECT * FROM `" + DB_NAME + "`.`Product` WHERE barcode=barcodeR";

    }

}
