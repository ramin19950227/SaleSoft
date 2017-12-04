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
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.product_list: ~17 rows (приблизительно)
/*!40000 ALTER TABLE `product_list` DISABLE KEYS */;
INSERT INTO `product_list` (`id`, `ad`, `say`, `alishqiymeti`, `barcode`, `qeyd`, `timestamp`) VALUES
	(47, 'Baraban 36A (1005)', 0, 1.8, '1', 'Qarishiq', '2017-12-04 06:35:29'),
	(48, 'Baraban 12A', 0, 1.8, '2', 'Qarishiq', '2017-12-04 06:35:28'),
	(49, 'Maqnit 36A (1005)', 0, 1.5, '3', 'Qarishiq', '2017-12-04 06:35:29'),
	(50, 'Maqnit 12A', 0, 1.5, '4', 'Qarishiq', '2017-12-04 06:35:29'),
	(51, 'Maqnit 05A', 0, 0, '5', 'Qarishiq', '2017-12-04 06:35:29'),
	(52, 'Baraban Ep27', 0, 0, '6', 'Qarishiq', '2017-12-04 06:33:49'),
	(53, 'Baraban HP651A (CE340)', 1, 0, '7', 'Qarishiq', '2017-11-29 10:29:44'),
	(54, 'Rezin val 36A  - (Karatron) (1005)', 0, 1.5, '8', 'Kainat', '2017-12-04 06:36:24'),
	(55, 'Rakel 36A (1005)', 1, 1.5, '9', 'Kainat', '2017-11-29 10:31:28'),
	(56, 'Termoplyonka 1010', 1, 0, '10', 'Qarishiq', '2017-11-29 22:52:31'),
	(57, 'Termo Element Pec ucun Canon 4410', 1, 0, '11', 'Qarishiq', '2017-11-29 10:33:02'),
	(58, 'Baraban 201A', 2, 0, '12', 'Qarishiq', '2017-11-29 10:33:50'),
	(59, 'Baraban 130A 310A 350A', 1, 0, '13', 'Qarishiq', '2017-11-29 10:34:26'),
	(60, 'Baraban Namelum', 1, 0, '14', 'Qarishiq', '2017-11-29 10:34:44'),
	(61, 'T b15', 9, 10, '15', '10', '2017-12-03 16:38:27'),
	(62, 'Test b16', 5, 3, '16', '3', '2017-12-03 16:38:05'),
	(63, 'Test Mal b17', 1, 2, '17', '2', '2017-12-04 06:25:16');
/*!40000 ALTER TABLE `product_list` ENABLE KEYS */;

-- Дамп структуры для таблица testdb.product_list_barcodsuz
CREATE TABLE IF NOT EXISTS `product_list_barcodsuz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.product_list_barcodsuz: ~2 rows (приблизительно)
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.satish_history: ~23 rows (приблизительно)
/*!40000 ALTER TABLE `satish_history` DISABLE KEYS */;
INSERT INTO `satish_history` (`id`, `customer`, `mebleg`, `odenish`, `qaliq`, `timeStamp`) VALUES
	(1, 'Ramin123 456 7890', 200, 199, 1, '2017-11-19 01:56:26'),
	(2, 'Test Kukuru ALAN ))', 50, 25, 25, '2017-11-19 01:59:15'),
	(3, 'Ramin TEST', 108, 108, 0, '2017-11-19 02:14:13'),
	(4, '', 1, 0, 1, '2017-11-19 02:21:32'),
	(5, '', 1, 0, 1, '2017-11-19 02:22:41'),
	(6, '', 1, 0, 1, '2017-11-19 02:24:35'),
	(7, '', 1, 0, 1, '2017-11-19 02:25:11'),
	(8, '', 1, 0, 1, '2017-11-19 02:25:39'),
	(9, '', 1, 0, 1, '2017-11-19 02:25:39'),
	(10, '', 1, 0, 1, '2017-11-19 02:26:27'),
	(11, '', 210, 210, 0, '2017-11-19 02:27:07'),
	(12, '', 3449, 5000, -1551, '2017-11-19 02:32:47'),
	(13, 'Ramin', 28, 28, 0, '2017-11-19 02:34:47'),
	(14, 'Ramin', 504, 0, 0, '2017-11-24 01:57:37'),
	(15, 'a', 2, 0, 0, '2017-12-03 16:25:36'),
	(16, 'Ramindasfsf12424324234234234244344242', 198, 0, 0, '2017-12-03 16:31:39'),
	(17, 'Ramindasfsf12424324234234234244344242', 198, 0, 0, '2017-12-03 16:31:40'),
	(18, 'TESH@@@@', 199.43, 0, 0, '2017-12-03 16:32:58'),
	(19, 'TESH@@@@', 199.43, 0, 0, '2017-12-03 16:32:59'),
	(20, 'Test', 3.3600000000000003, 0, 0, '2017-12-03 16:34:19'),
	(21, 'Test', 3.3600000000000003, 0, 0, '2017-12-03 16:34:20'),
	(22, 'Test new ', 135, 0, 0, '2017-12-03 16:35:41'),
	(23, 'Ramin', 112.8, 0, 0, '2017-12-03 16:38:27'),
	(24, 'Ramin', 2, 0, 0, '2017-12-03 16:39:31'),
	(25, 'Test 99 manatlq', 99, 0, 0, '2017-12-04 06:25:16'),
	(26, 'Milli Bank ; Ramin', 45, 0, 0, '2017-12-04 06:29:03'),
	(27, 'Ehmedli neise', 50, 0, 0, '2017-12-04 06:33:49'),
	(28, 'Hamsi', 72.3, 0, 0, '2017-12-04 06:35:28'),
	(29, '123', 1.5, 0, 0, '2017-12-04 06:36:24');
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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы testdb.satish_list: ~13 rows (приблизительно)
/*!40000 ALTER TABLE `satish_list` DISABLE KEYS */;
INSERT INTO `satish_list` (`id`, `history_id`, `p_id`, `p_name`, `p_say`, `p_qiymet`, `p_mebleg`, `p_barcode`, `p_qeyd`, `p_satishdan_evvelki_say`, `timeStamp`) VALUES
	(47, 20, 61, 'T b15', 1, 1.11, 1.11, '15', '10', 10, '2017-12-03 16:34:19'),
	(48, 20, 62, 'Test b16', 1, 1.12, 1.12, '16', '3', 10, '2017-12-03 16:34:19'),
	(49, 20, 63, 'Test Mal b17', 1, 1.13, 1.13, '17', '2', 10, '2017-12-03 16:34:19'),
	(50, 21, 61, 'T b15', 1, 1.11, 1.11, '15', '10', 10, '2017-12-03 16:34:20'),
	(51, 21, 62, 'Test b16', 1, 1.12, 1.12, '16', '3', 10, '2017-12-03 16:34:21'),
	(52, 21, 63, 'Test Mal b17', 1, 1.13, 1.13, '17', '2', 10, '2017-12-03 16:34:21'),
	(53, 22, 61, 'T b15', 9, 10, 90, '15', '10', 9, '2017-12-03 16:35:41'),
	(54, 22, 62, 'Test b16', 9, 3, 27, '16', '3', 9, '2017-12-03 16:35:41'),
	(55, 18, 63, 'Test Mal b17', 9, 2, 18, '17', '2', 9, '2017-12-03 16:35:42'),
	(56, 23, 61, 'T b15', 1, 111, 111, '15', '10', 10, '2017-12-03 16:38:27'),
	(57, 23, 47, 'Baraban 36A (1005)', 1, 1.8, 1.8, '1', 'Qarishiq', 2, '2017-12-03 16:38:27'),
	(58, 24, 63, 'Test Mal b17', 1, 2, 2, '17', '2', 3, '2017-12-03 16:39:31'),
	(59, 25, 63, 'Test Mal b17', 1, 99, 99, '17', '2', 2, '2017-12-04 06:25:17'),
	(60, 26, 50, 'Maqnit 12A', 4, 11.25, 45, '4', 'Qarishiq', 17, '2017-12-04 06:29:03'),
	(61, 27, 52, 'Baraban Ep27', 1, 50, 50, '6', 'Qarishiq', 1, '2017-12-04 06:33:49'),
	(62, 28, 48, 'Baraban 12A', 15, 1.8, 27, '2', 'Qarishiq', 15, '2017-12-04 06:35:28'),
	(63, 28, 49, 'Maqnit 36A (1005)', 16, 1.5, 24, '3', 'Qarishiq', 16, '2017-12-04 06:35:29'),
	(64, 28, 50, 'Maqnit 12A', 13, 1.5, 19.5, '4', 'Qarishiq', 13, '2017-12-04 06:35:29'),
	(65, 28, 51, 'Maqnit 05A', 2, 0, 0, '5', 'Qarishiq', 2, '2017-12-04 06:35:29'),
	(66, 28, 47, 'Baraban 36A (1005)', 1, 1.8, 1.8, '1', 'Qarishiq', 1, '2017-12-04 06:35:29'),
	(67, 29, 54, 'Rezin val 36A  - (Karatron) (1005)', 1, 1.5, 1.5, '8', 'Kainat', 1, '2017-12-04 06:36:25');
/*!40000 ALTER TABLE `satish_list` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
