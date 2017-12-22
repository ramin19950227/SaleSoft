/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.database;

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

    /**
     * Class - Bazamizi Qurmaq ucun istifade olunan Sorgular burda yer alir
     */
    public static class SetubDB {

        /**
         * Class-imizin Esas Sorgulari qaytarma Funksiyasidir
         *
         * @return
         */
        public static ArrayList<String> getQueriesList() {
            String dbName = MyProperties.getDBProperties().getDbName();
            System.err.println("Setuping DB Name: " + dbName);

            ArrayList<String> queryList = new ArrayList();

            queryList.add("CREATE DATABASE IF NOT EXISTS `" + dbName + "` DEFAULT CHARACTER SET utf8;");

            queryList.add("CREATE TABLE IF NOT EXISTS `" + dbName + "`.`alish_list` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `p_id` int(11) DEFAULT NULL,\n"
                    + "  `p_name` text,\n"
                    + "  `p_qty` int(11) DEFAULT NULL,\n"
                    + "  `p_purchasePrice` double DEFAULT NULL,\n"
                    + "  `p_barCode` text,\n"
                    + "  `p_note` text,\n"
                    + "  `totalPrice` double DEFAULT NULL,\n"
                    + "  `purchase_date` datetime DEFAULT NULL,\n"
                    + "  `timeStamp` timestamp NULL DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `id` (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

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

            return queryList;
        }

    }

    /**
     * Product Modeli ile aparilan emeliyatlarin SQL-sorgulari
     */
    public static class Product {

        /**
         * Butun mehsullari almaq ucun istifade olunur (SELECT * ...)
         */
        public static final String PRODUCT_GET_ALL = "SELECT * FROM " + TableNames.productTableName;

        /**
         * SQL For PreparedStatement, id=? (SELECT * ... WHERE id=?)
         */
        public static final String PRODUCT_GET_BY_ID_P = "SELECT * FROM " + TableNames.productTableName + " WHERE id=?";

        /**
         * SQL For ReplaceAll, id=idR (SELECT * ... WHERE id=idR)
         */
        public static final String PRODUCT_GET_BY_ID_R = "SELECT * FROM " + TableNames.productTableName + " WHERE id=idR";

        /**
         * SQL For PreparedStatement, barcode=?
         */
        public static final String PRODUCT_GET_BY_BARCODE_P = "SELECT * FROM " + TableNames.productTableName + " WHERE barcode=?";

        /**
         * SQL For ReplaceAll, barcode=barcodeR
         */
        public static final String PRODUCT_GET_BY_BARCODE_R = "SELECT * FROM " + TableNames.productTableName + " WHERE barcode='barcodeR'";

        /**
         * SQL For PreparedStatement, ad LIKE=?
         */
        public static final String PRODUCT_GEL_ALL_BY_NAME_LIKE_P = "SELECT * FROM " + TableNames.productTableName + " WHERE ad LIKE ?";

        /**
         * SQL For ReplaceAll, ad LIKE=adR
         */
        public static final String PRODUCT_GEL_ALL_BY_NAME_LIKE_R = "SELECT * FROM " + TableNames.productTableName + " WHERE ad LIKE 'adR'";

        /**
         * SQL For PreparedStatement, barcode=?
         */
        public static final String PRODUCT_GEL_ALL_BY_BARCODE_P = "SELECT * FROM " + TableNames.productTableName + " WHERE barcode=?";

        /**
         * SQL For ReplaceAll, barcode=barcodeR
         */
        public static final String PRODUCT_GEL_ALL_BY_BARCODE_R = "SELECT * FROM " + TableNames.productTableName + " WHERE barcode=barcodeR";
    }

    /**
     * AllProperties Modeli ile aparilan emeliyatlarin SQL-sorgulari
     */
    public static class AllProperties {

        /**
         * SQL for PreparedStatement
         */
        public static final String PROPERTIES_GET_ALL_BY_TYPE_P = "SELECT * FROM allproperties where type=?";

        /**
         * SQL for ReplaceAll
         */
        public static final String PROPERTIES_GET_ALL_BY_TYPE = "SELECT * FROM allproperties where type='typeR'";
    }

    /**
     * Invoice Modeli ile aparilan emeliyatlarin SQL-sorgulari
     */
    public static class Invoice {

        private static final String INVOICE_TABLE_NAME = "satish_history";

        public static final String INVOICE_ADD_NEW = "INSERT INTO " + INVOICE_TABLE_NAME + " (customer,mebleg) VALUES ('customerR', meblegR)";
        public static final String INVOICE_GET_LAST_ID = "SELECT MAX(id) FROM " + INVOICE_TABLE_NAME + " LIMIT 1";

        //Invoice-obyektini yenilemek ucun 
        public static final String INVOICE_UPDATE_BY_ID = "UPDATE " + INVOICE_TABLE_NAME + " SET `customer`='customerR', `mebleg`='meblegR' WHERE  `id`=idR;";
    }

    /**
     * InvoiceItem Modeli ile aparilan emeliyatlarin SQL-sorgulari
     */
    public static class InvoiceItem {

        private static final String INVOICEITEM_TABLE_NAME = "satish_list";

        /**
         * Parametrler
         *
         * @see 1=history_id;
         * @see 2=p_id;
         * @see 3=p_name;
         * @see 4=p_say;
         * @see 5=p_qiymet;
         * @see 6=p_mebleg;
         * @see 7=p_barcode;
         * @see 8=p_qeyd;
         * @see 9=p_satishdan_evvelki_say;
         */
        public static final String INVOICEITEM_ADD_NEW = "INSERT INTO " + INVOICEITEM_TABLE_NAME + " (history_id,p_id,p_name,p_say,p_qiymet,p_mebleg,p_barcode,p_qeyd,p_satishdan_evvelki_say) VALUES (history_idR, p_idR, 'p_nameR', p_sayR, p_qiymetR, p_meblegR, 'p_barcodeR', 'p_qeydR', p_satishdan_evvelki_sayR)";

        /**
         * @see ".replaceAll("p_sayR",
         * invoiceItem.getQty().toString()).replaceAll("p_meblegR",
         * invoiceItem.getTotalPrice().toString()).replaceAll("id=idR",
         * invoiceItem.getId().toString())"
         */
        public static final String INVOICEITEM_UPDATE_BY_ID = "UPDATE " + INVOICEITEM_TABLE_NAME + " SET p_say='p_sayR', p_mebleg='p_meblegR' WHERE  id=idR;";
    }

}
