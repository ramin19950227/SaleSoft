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
CREATE DATABASE IF NOT EXISTS `testdb` DEFAULT CHARACTER SET utf8;
USE `testdb`;

-- Дамп структуры для таблица testdb.alish_list
CREATE TABLE IF NOT EXISTS `alish_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL,
  `p_name` text,
  `p_qty` int(11) DEFAULT NULL,
  `p_purchasePrice` double DEFAULT NULL,
  `p_totalPrice` double DEFAULT NULL,
  `p_note` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.alish_list: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `alish_list` DISABLE KEYS */;
INSERT INTO `alish_list` (`id`, `p_id`, `p_name`, `p_qty`, `p_purchasePrice`, `p_totalPrice`, `p_note`) VALUES
	(1, 60, 'Baraban', 45, 1.25, 56.25, 'note');
/*!40000 ALTER TABLE `alish_list` ENABLE KEYS */;

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
  `13` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.allproperties: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `allproperties` DISABLE KEYS */;
INSERT INTO `allproperties` (`id`, `type`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`) VALUES
	(1, 'urlKey', 'Login Form', 'ApplicationForm', 'HomeForm', 'ProductTable', 'ProductSaleCart', 'AnbarRootLayout', 'ProductPurchse', 'SaleRootLayout', 'SaleInvoiceTable', 'SaleInvoiceDetailsTable', NULL),
	(2, 'url', 'view/Login.fxml', 'view/Application.fxml', 'view/HOME.fxml', 'view/anbar/ProductTable.fxml', 'view/sale/ProductSaleCart.fxml', 'view/AnbarRootLayout.fxml', 'view/anbar/ProductPurchse.fxml', 'view/SaleRootLayout.fxml', 'view/sale/SaleInvoiceTable.fxml', 'view/sale/SaleInvoiceDetailsTable.fxml', 'view/anbar/PurchaseInvoiceTable.fxml');
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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.product_list: ~5 rows (приблизительно)
/*!40000 ALTER TABLE `product_list` DISABLE KEYS */;
INSERT INTO `product_list` (`id`, `ad`, `say`, `alishqiymeti`, `barcode`, `qeyd`, `timestamp`) VALUES
	(52, 'Baraban Ep27', 10, 1.25, '2', 'Qarishiq', '2017-12-17 18:47:22'),
	(53, 'Baraban HP651A (CE340)', 10, 2.5, '3', 'Qarishiq', '2017-12-17 18:47:21'),
	(54, 'Rezin val 36A  - (Karatron) (1005)', 10, 2.5, '4', 'Kainat', '2017-12-17 18:47:20'),
	(56, 'Termoplyonka 1010', 10, 1.5, '5', 'Qarishiq', '2017-12-17 18:47:19'),
	(64, 'Test', 10, 10, '1', 'kk', '2017-12-17 21:30:55');
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
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.satish_history: ~25 rows (приблизительно)
/*!40000 ALTER TABLE `satish_history` DISABLE KEYS */;
INSERT INTO `satish_history` (`id`, `customer`, `mebleg`, `odenish`, `qaliq`, `timeStamp`) VALUES
	(37, '1', 1, 1, 1, '2017-12-14 11:31:21'),
	(38, 'ramin 3 mehsul 1 b 2n', 15.15, 0, 0, '2017-12-14 11:35:38'),
	(39, 'Milli Bankds', 20, 0, 0, '2017-12-14 11:37:04'),
	(40, 'Test', 96, 0, 0, '2017-12-14 22:14:32'),
	(41, 'Test', 96, 0, 0, '2017-12-14 22:14:35'),
	(42, 'Pullu Mushteri', 716.25, 0, 0, '2017-12-15 01:12:57'),
	(43, 'Ramin Ismayilov', 12.5, NULL, NULL, '2017-12-16 23:41:21'),
	(44, 'Ramin', 18.75, NULL, NULL, '2017-12-16 23:44:21'),
	(45, 'Ramin', 11.25, NULL, NULL, '2017-12-16 23:54:36'),
	(46, 'Test', 12.5, NULL, NULL, '2017-12-17 00:07:23'),
	(47, '12', 3.75, NULL, NULL, '2017-12-17 00:09:14'),
	(48, 'Ramin', 10, NULL, NULL, '2017-12-17 01:38:06'),
	(49, 'Ramin', 22.5, NULL, NULL, '2017-12-17 01:42:24'),
	(50, 'Ramin', 250, NULL, NULL, '2017-12-17 01:52:27'),
	(51, 'Test', 1, NULL, NULL, '2017-12-17 01:55:14'),
	(52, 'Test', 18.75, NULL, NULL, '2017-12-17 03:40:31'),
	(53, 'All sale 10', 102.5, NULL, NULL, '2017-12-17 03:42:18'),
	(54, 'test 2', 37.5, NULL, NULL, '2017-12-17 03:44:01'),
	(55, 'tesssss', 177.5, NULL, NULL, '2017-12-17 16:40:32'),
	(56, 't', 137.5, NULL, NULL, '2017-12-17 17:03:44'),
	(57, 'new', 77.5, NULL, NULL, '2017-12-17 17:21:24'),
	(58, 'r', 0, NULL, NULL, '2017-12-17 18:44:24'),
	(59, '4', 0, NULL, NULL, '2017-12-17 18:46:05'),
	(60, 'Ramin', 0, NULL, NULL, '2017-12-17 18:46:37'),
	(61, 'Im Changed', 0, NULL, NULL, '2017-12-17 18:54:19'),
	(62, 'F', 0, NULL, NULL, '2017-12-17 21:30:14');
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
  `updateTimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.satish_list: ~69 rows (приблизительно)
/*!40000 ALTER TABLE `satish_list` DISABLE KEYS */;
INSERT INTO `satish_list` (`id`, `history_id`, `p_id`, `p_name`, `p_say`, `p_qiymet`, `p_mebleg`, `p_barcode`, `p_qeyd`, `p_satishdan_evvelki_say`, `timeStamp`, `updateTimeStamp`) VALUES
	(77, 37, -1, 'kukuruz', 0, 0.25, 7.5, 'barCode2', 'note', 1000, '2017-12-14 11:31:21', '2017-12-17 03:38:12'),
	(78, 38, -1, 'test', 0, 0.3333333333333333, 7.5, 'barCode', 'note', 1000, '2017-12-14 11:35:38', '2017-12-17 03:38:12'),
	(79, 38, -2, 'kukuruz', 0, 0.33, 7.5, 'barCode', 'note', 1000, '2017-12-14 11:35:38', '2017-12-17 03:38:12'),
	(80, 38, 52, 'Baraban Ep27', 0, 2.5, 7.5, '6', 'Qarishiq', 10, '2017-12-14 11:35:38', '2017-12-17 03:38:12'),
	(81, 39, -1, 'Hp 651 Cip Qara', 0, 10, 7.5, 'barCode', 'note', 1000, '2017-12-14 11:37:04', '2017-12-17 03:38:12'),
	(82, 39, 52, 'Baraban Ep27', 0, 10, 7.5, '6', 'Qarishiq', 5, '2017-12-14 11:37:04', '2017-12-17 03:38:12'),
	(83, 40, -1, 'Kukuruz', 0, 8.5, 7.5, 'barCode', 'note', 1000, '2017-12-14 22:14:32', '2017-12-17 03:38:12'),
	(84, 40, 51, 'Maqnit 05A', 0, 2.6666666666666665, 7.5, '5', 'q', 95, '2017-12-14 22:14:32', '2017-12-17 03:38:12'),
	(85, 40, 62, 'Yeni Mehsul:', 0, 1.5, 7.5, '1', NULL, 2, '2017-12-14 22:14:33', '2017-12-17 03:38:12'),
	(86, 41, -1, 'Kukuruz', 0, 8.5, 7.5, 'barCode', 'note', 1000, '2017-12-14 22:14:35', '2017-12-17 03:38:12'),
	(87, 41, 51, 'Maqnit 05A', 0, 2.6666666666666665, 7.5, '5', 'q', 95, '2017-12-14 22:14:35', '2017-12-17 03:38:12'),
	(88, 41, 62, 'Yeni Mehsul:', 0, 1.5, 7.5, '1', NULL, 2, '2017-12-14 22:14:35', '2017-12-17 03:38:12'),
	(89, 42, -1, 'Tet1', 0, 10, 7.5, 'barCode', 'note', 1000, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(90, 42, -2, 'Kukuruq', 0, 10, 7.5, 'barCode', 'note', 1000, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(91, 42, -3, 'Ne bilimne', 0, 10, 7.5, 'barCode', 'note', 1000, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(92, 42, 51, 'Maqnit 05A', 0, 2.5, 7.5, '1', 'sdjfhksldjf', 91, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(93, 42, -4, 'any Product', 0, 10, 7.5, 'barCode', 'note', 1000, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(94, 42, 52, 'Baraban Ep27', 0, 1.25, 7.5, '2', 'Qarishiq', 5, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(95, 42, -5, ')))))', 0, 10, 7.5, 'barCode', 'note', 1000, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(96, 42, 53, 'Baraban HP651A (CE340)', 0, 2.5, 7.5, '3', 'Qarishiq', 90, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(97, 42, 54, 'Rezin val 36A  - (Karatron) (1005)', 0, 2.5, 7.5, '4', 'Kainat', 80, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(98, 42, 55, 'Rakel 36A (1005)', 0, 1.5, 7.5, '5', 'Kainat', 5, '2017-12-15 01:12:57', '2017-12-17 03:38:12'),
	(99, 47, 52, 'Baraban Ep27', 0, 1.25, 7.5, '2', 'note', 3, '2017-12-17 00:09:14', '2017-12-17 03:38:12'),
	(100, 47, 53, 'Baraban HP651A (CE340)', 0, 2.5, 7.5, '3', 'note', 3, '2017-12-17 00:09:14', '2017-12-17 03:38:12'),
	(101, 48, 51, 'Maqnit 05A', 0, 2.5, 7.5, '1', 'note', 1, '2017-12-17 01:38:07', '2017-12-17 03:38:12'),
	(102, 48, 52, 'Baraban Ep27', 0, 1.25, 7.5, '2', 'note', 2, '2017-12-17 01:38:07', '2017-12-17 03:38:12'),
	(103, 48, 53, 'Baraban HP651A (CE340)', 0, 2.5, 7.5, '3', 'note', 2, '2017-12-17 01:38:07', '2017-12-17 03:38:12'),
	(104, 49, 51, 'Maqnit 05A', 0, 2.5, 7.5, '1', 'note', 1, '2017-12-17 01:42:24', '2017-12-17 03:38:12'),
	(105, 49, 52, 'Baraban Ep27', 0, 1.25, 7.5, '2', 'note', 2, '2017-12-17 01:42:24', '2017-12-17 03:38:12'),
	(106, 49, 53, 'Baraban HP651A (CE340)', 0, 2.5, 7.5, '3', 'note', 3, '2017-12-17 01:42:24', '2017-12-17 03:38:12'),
	(107, 49, 54, 'Rezin val 36A  - (Karatron) (1005)', 0, 2.5, 7.5, '4', 'note', 4, '2017-12-17 01:42:24', '2017-12-17 03:38:12'),
	(108, 50, 51, 'Maqnit 05A', 0, 2.5, 7.5, '1', 'note', 100, '2017-12-17 01:52:27', '2017-12-17 03:38:12'),
	(109, 51, 51, 'Maqnit 05A', 0, 1, 7.5, '1', 'note', 50, '2017-12-17 01:55:14', '2017-12-17 03:38:12'),
	(110, 52, 51, 'Maqnit 05A', 0, 2.5, 12.5, '1', 'note', 49, '2017-12-17 03:40:31', '2017-12-17 03:41:03'),
	(111, 52, 52, 'Baraban Ep27', 0, 1.25, 6.25, '5', 'note', 19, '2017-12-17 03:40:31', '2017-12-17 03:40:57'),
	(112, 53, 51, 'Maqnit 05A', 0, 2.5, 25, '1', 'note', 10, '2017-12-17 03:42:18', '2017-12-17 03:53:15'),
	(113, 53, 52, 'Baraban Ep27', 0, 1.25, 12.5, '2', 'note', 10, '2017-12-17 03:42:18', '2017-12-17 16:39:47'),
	(114, 53, 53, 'Baraban HP651A (CE340)', 0, 2.5, 25, '3', 'note', 10, '2017-12-17 03:42:18', '2017-12-17 04:18:47'),
	(115, 53, 54, 'Rezin val 36A  - (Karatron) (1005)', 0, 2.5, 25, '4', 'note', 10, '2017-12-17 03:42:18', '2017-12-17 04:18:34'),
	(116, 53, 56, 'Termoplyonka 1010', 0, 1.5, 15, '5', 'note', 10, '2017-12-17 03:42:18', '2017-12-17 03:43:05'),
	(117, 54, 51, 'Maqnit 05A', 0, 2.5, 2.5, '1', 'note', 1, '2017-12-17 03:44:02', '2017-12-17 16:31:22'),
	(118, 54, 52, 'Baraban Ep27', 0, 1.25, 2.5, '2', 'note', 2, '2017-12-17 03:44:02', '2017-12-17 16:39:18'),
	(119, 54, 53, 'Baraban HP651A (CE340)', 0, 2.5, 7.5, '3', 'note', 3, '2017-12-17 03:44:02', '2017-12-17 16:06:58'),
	(120, 54, 54, 'Rezin val 36A  - (Karatron) (1005)', 0, 2.5, 10, '4', 'note', 4, '2017-12-17 03:44:02', '2017-12-17 16:35:17'),
	(121, 54, 56, 'Termoplyonka 1010', 0, 1.5, 15, '5', 'note', 10, '2017-12-17 03:44:02', '2017-12-17 16:37:37'),
	(122, 55, 64, 'Test', 0, 10, 0, '1', 'note', 10, '2017-12-17 16:40:32', '2017-12-17 16:57:10'),
	(123, 55, 52, 'Baraban Ep27', 0, 1.25, 0, '2', 'note', 10, '2017-12-17 16:40:32', '2017-12-17 16:57:09'),
	(124, 55, 53, 'Baraban HP651A (CE340)', 0, 2.5, 0, '3', 'note', 10, '2017-12-17 16:40:33', '2017-12-17 16:57:05'),
	(125, 55, 54, 'Rezin val 36A  - (Karatron) (1005)', 0, 2.5, 0, '4', 'note', 10, '2017-12-17 16:40:33', '2017-12-17 16:57:04'),
	(126, 55, 56, 'Termoplyonka 1010', 0, 1.5, 0, '5', 'note', 10, '2017-12-17 16:40:33', '2017-12-17 16:57:08'),
	(127, 56, 64, 'Test', 0, 10, 0, '1', 'note', 10, '2017-12-17 17:03:45', '2017-12-17 17:04:17'),
	(128, 56, 52, 'Baraban Ep27', 0, 1.25, 0, '2', 'note', 10, '2017-12-17 17:03:45', '2017-12-17 17:08:01'),
	(129, 56, 53, 'Baraban HP651A (CE340)', 0, 2.5, 0, '3', 'note', 10, '2017-12-17 17:03:45', '2017-12-17 17:08:09'),
	(130, 57, 64, 'Test', 0, 10, 0, '1', 'note', 10, '2017-12-17 17:21:24', '2017-12-17 17:53:18'),
	(131, 57, 52, 'Baraban Ep27', 0, 1.25, 0, '2', 'note', 10, '2017-12-17 17:21:24', '2017-12-17 17:51:59'),
	(132, 57, 53, 'Baraban HP651A (CE340)', 0, 2.5, 0, '3', 'note', 10, '2017-12-17 17:21:24', '2017-12-17 17:50:30'),
	(133, 57, 54, 'Rezin val 36A  - (Karatron) (1005)', 0, 2.5, 0, '4', 'note', 10, '2017-12-17 17:21:25', '2017-12-17 17:49:35'),
	(134, 57, 56, 'Termoplyonka 1010', 0, 1.5, 0, '5', 'note', 10, '2017-12-17 17:21:25', '2017-12-17 17:48:39'),
	(135, 58, 64, 'Test', 0, 10, 0, '1', 'note', 10, '2017-12-17 18:44:24', '2017-12-17 18:44:51'),
	(136, 58, 52, 'Baraban Ep27', 0, 1.25, 0, '2', 'note', 10, '2017-12-17 18:44:25', '2017-12-17 18:44:50'),
	(137, 58, 53, 'Baraban HP651A (CE340)', 0, 2.5, 0, '3', 'note', 10, '2017-12-17 18:44:25', '2017-12-17 18:44:49'),
	(138, 58, 54, 'Rezin val 36A  - (Karatron) (1005)', 0, 2.5, 0, '4', 'note', 10, '2017-12-17 18:44:25', '2017-12-17 18:44:48'),
	(139, 58, 56, 'Termoplyonka 1010', 0, 1.5, 0, '5', 'note', 10, '2017-12-17 18:44:25', '2017-12-17 18:44:46'),
	(140, 59, 64, 'Test', 0, 10, 0, '1', 'note', 10, '2017-12-17 18:46:05', '2017-12-17 18:46:19'),
	(141, 60, 64, 'Test', 0, 10, 0, '1', 'note', 10, '2017-12-17 18:46:37', '2017-12-17 18:46:45'),
	(142, 60, 52, 'Baraban Ep27', 0, 1.25, 0, '2', 'note', 10, '2017-12-17 18:46:37', '2017-12-17 18:47:22'),
	(143, 60, 53, 'Baraban HP651A (CE340)', 0, 2.5, 0, '3', 'note', 10, '2017-12-17 18:46:37', '2017-12-17 18:47:21'),
	(144, 60, 54, 'Rezin val 36A  - (Karatron) (1005)', 0, 2.5, 0, '4', 'note', 10, '2017-12-17 18:46:37', '2017-12-17 18:47:20'),
	(145, 60, 56, 'Termoplyonka 1010', 0, 1.5, 0, '5', 'note', 10, '2017-12-17 18:46:37', '2017-12-17 18:47:19'),
	(146, 61, 64, 'Test', 0, 10, 0, '1', 'note', 10, '2017-12-17 18:54:19', '2017-12-17 18:55:03'),
	(147, 62, 64, 'Test', 0, 10, 0, '1', 'note', 10, '2017-12-17 21:30:14', '2017-12-17 21:30:55');
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
	(1, '1', 'Ramin', NULL, NULL, NULL, NULL, '1', 1, NULL, '2017-12-10', NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
