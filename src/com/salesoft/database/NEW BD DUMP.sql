-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.7.20-log - MySQL Community Server (GPL)
-- Операционная система:         Win64
-- HeidiSQL Версия:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных testdb
CREATE DATABASE IF NOT EXISTS `testdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `testdb`;

-- Дамп структуры для таблица testdb.allproperties
CREATE TABLE IF NOT EXISTS `allproperties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` text,
  `3` text,
  `4` text,
  `5` text,
  `6` text,
  `7` text,
  `8` text,
  `9` text,
  `10` text,
  `11` text,
  `12` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.allproperties: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `allproperties` DISABLE KEYS */;
INSERT INTO `allproperties` (`id`, `type`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`) VALUES
	(1, 'urlKey', 'Login Form', 'ApplicationForm', 'HomeForm', 'ProductTable', 'ProductSaleCart', 'AnbarRootLayout', 'ProductPurchse', 'SaleRootLayout', 'SaleInvoiceTable', 'SaleInvoiceDetailsTable'),
	(2, 'url', 'view/Login.fxml', 'view/Application.fxml', 'view/HOME.fxml', 'view/anbar/ProductTable.fxml', 'view/sale/ProductSaleCart.fxml', 'view/AnbarRootLayout.fxml', 'view/anbar/ProductPurchse.fxml', 'view/SaleRootLayout.fxml', 'view/sale/SaleInvoiceTable.fxml', 'view/sale/SaleInvoiceDetailsTable.fxml');
/*!40000 ALTER TABLE `allproperties` ENABLE KEYS */;

-- Дамп структуры для таблица testdb.product_list
CREATE TABLE IF NOT EXISTS `product_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ad` text NOT NULL,
  `say` int(11) DEFAULT NULL,
  `alishqiymeti` double DEFAULT NULL,
  `barcode` text,
  `qeyd` text,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.product_list: ~9 rows (приблизительно)
/*!40000 ALTER TABLE `product_list` DISABLE KEYS */;
INSERT INTO `product_list` (`id`, `ad`, `say`, `alishqiymeti`, `barcode`, `qeyd`, `timestamp`) VALUES
	(51, 'Maqnit 05A', 0, 0, '5', 'Qarishiq', '2017-12-04 06:35:29'),
	(52, 'Baraban Ep27', 5, 0, '6', 'Qarishiq', '2017-12-14 12:26:32'),
	(53, 'Baraban HP651A (CE340)', 89, 0, '7', 'Qarishiq', '2017-12-09 13:01:31'),
	(54, 'Rezin val 36A  - (Karatron) (1005)', 8, 1.5, '8', 'Kainat', '2017-12-14 12:26:36'),
	(55, 'Rakel 36A (1005)', 0, 1.5, '9', 'Kainat', '2017-12-09 09:41:51'),
	(56, 'Termoplyonka 1010', 0, 0, '10', 'Qarishiq', '2017-12-09 09:41:52'),
	(57, 'Termo Element Pec ucun Canon 4410', 0, 0, '11', 'Qarishiq', '2017-12-09 09:41:53'),
	(59, 'Baraban 130A 310A 350A', 0, 0, '13', 'Qarishiq', '2017-12-09 09:41:54'),
	(62, 'Yeni Mehsul:', 2, 1.5, '1', NULL, '2017-12-14 12:25:05');
/*!40000 ALTER TABLE `product_list` ENABLE KEYS */;

-- Дамп структуры для таблица testdb.product_list_barcodsuz
CREATE TABLE IF NOT EXISTS `product_list_barcodsuz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.product_list_barcodsuz: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `product_list_barcodsuz` DISABLE KEYS */;
INSERT INTO `product_list_barcodsuz` (`id`, `name`) VALUES
	(1, 'kukuruz'),
	(2, 'dirnaq kesen'),
	(3, 'rucka 12');
/*!40000 ALTER TABLE `product_list_barcodsuz` ENABLE KEYS */;

-- Дамп структуры для таблица testdb.qiymet_history
CREATE TABLE IF NOT EXISTS `qiymet_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) NOT NULL,
  `ad` text NOT NULL,
  `say` int(11) NOT NULL,
  `alishqiymeti` double NOT NULL,
  `barcode` text NOT NULL,
  `gelishtarixi` date NOT NULL,
  `qeyd` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.qiymet_history: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `qiymet_history` DISABLE KEYS */;
INSERT INTO `qiymet_history` (`id`, `p_id`, `ad`, `say`, `alishqiymeti`, `barcode`, `gelishtarixi`, `qeyd`, `timestamp`) VALUES
	(1, -1, 'Product', 100, 4.2, '1008', '2017-11-09', 'Kainat', '2017-11-11 11:36:24'),
	(2, -1, 'Product', 100, 3.5, '1008', '2017-11-09', 'Kainat', '2017-11-11 11:36:24');
/*!40000 ALTER TABLE `qiymet_history` ENABLE KEYS */;

-- Дамп структуры для таблица testdb.satish_history
CREATE TABLE IF NOT EXISTS `satish_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer` text,
  `mebleg` double DEFAULT NULL,
  `odenish` double DEFAULT NULL,
  `qaliq` double DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.satish_history: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `satish_history` DISABLE KEYS */;
INSERT INTO `satish_history` (`id`, `customer`, `mebleg`, `odenish`, `qaliq`, `timeStamp`) VALUES
	(37, 'Kiosk 1', 0.75, 0, 0, '2017-12-14 11:31:21'),
	(38, 'ramin 3 mehsul 1 b 2n', 15.15, 0, 0, '2017-12-14 11:35:38'),
	(39, 'Milli Bank', 20, 0, 0, '2017-12-14 11:37:04');
/*!40000 ALTER TABLE `satish_history` ENABLE KEYS */;

-- Дамп структуры для таблица testdb.satish_list
CREATE TABLE IF NOT EXISTS `satish_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `history_id` int(11) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  `p_name` text,
  `p_say` int(11) DEFAULT NULL,
  `p_qiymet` double DEFAULT NULL,
  `p_mebleg` double DEFAULT NULL,
  `p_barcode` text,
  `p_qeyd` text,
  `p_satishdan_evvelki_say` int(11) DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.satish_list: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `satish_list` DISABLE KEYS */;
INSERT INTO `satish_list` (`id`, `history_id`, `p_id`, `p_name`, `p_say`, `p_qiymet`, `p_mebleg`, `p_barcode`, `p_qeyd`, `p_satishdan_evvelki_say`, `timeStamp`) VALUES
	(77, 37, -1, 'kukuruz', 3, 0.25, 0.75, 'barCode', 'note', 1000, '2017-12-14 11:31:21'),
	(78, 38, -1, 'test', 3, 0.3333333333333333, 1, 'barCode', 'note', 1000, '2017-12-14 11:35:38'),
	(79, 38, -2, 'kukuruz', 5, 0.33, 1.6500000000000001, 'barCode', 'note', 1000, '2017-12-14 11:35:38'),
	(80, 38, 52, 'Baraban Ep27', 5, 2.5, 12.5, '6', 'Qarishiq', 10, '2017-12-14 11:35:38'),
	(81, 39, -1, 'Hp 651 Cip Qara', 1, 10, 10, 'barCode', 'note', 1000, '2017-12-14 11:37:04'),
	(82, 39, 52, 'Baraban Ep27', 1, 10, 10, '6', 'Qarishiq', 5, '2017-12-14 11:37:04');
/*!40000 ALTER TABLE `satish_list` ENABLE KEYS */;

-- Дамп структуры для таблица testdb.user
CREATE TABLE IF NOT EXISTS `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UsrName` varchar(20) NOT NULL,
  `FullName` varchar(100) DEFAULT NULL,
  `EmailAddress` varchar(100) DEFAULT NULL,
  `ContactNumber` varchar(100) DEFAULT NULL,
  `Salary` double DEFAULT NULL,
  `Address` text,
  `Password` varchar(45) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '0',
  `UserImage` mediumblob,
  `Date` date NOT NULL,
  `CreatorId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Id` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.user: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`Id`, `UsrName`, `FullName`, `EmailAddress`, `ContactNumber`, `Salary`, `Address`, `Password`, `Status`, `UserImage`, `Date`, `CreatorId`) VALUES
	(1, 'Ramin', 'Ramin', NULL, NULL, NULL, NULL, '123', 1, NULL, '2017-12-10', NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
