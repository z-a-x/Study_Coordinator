-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Gostitelj: 127.0.0.1
-- Čas nastanka: 11. jan 2015 ob 22.32
-- Različica strežnika: 5.6.17
-- Različica PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Zbirka podatkov: `study_coordinator`
--

-- --------------------------------------------------------

--
-- Struktura tabele `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK_Relationship_4` (`event_id`),
  KEY `FK_Relationship_8` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Odloži podatke za tabelo `comment`
--

INSERT INTO `comment` (`comment_id`, `event_id`, `user_id`, `date_time`, `text`) VALUES
(1, 5, 5, '2015-01-09 09:00:00', 'dwqddqwqqdw'),
(2, 5, 5, '2015-01-09 09:00:00', 'dwqddqwqqdw'),
(3, 5, 5, '2015-01-09 09:00:00', 'dwqddqwqqdw'),
(4, 5, 5, '2015-01-09 09:00:00', 'dwqddqwqqdw'),
(5, 5, 5, '2015-01-09 09:00:00', 'dwqddqwqqdw');

-- --------------------------------------------------------

--
-- Struktura tabele `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `event_name` varchar(100) NOT NULL,
  `time` datetime NOT NULL,
  `description` text NOT NULL,
  `scope` enum('private','protected','public') DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK_Relationship_3` (`group_id`),
  KEY `FK_Relationship_2` (`location_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Odloži podatke za tabelo `event`
--

INSERT INTO `event` (`event_id`, `location_id`, `group_id`, `event_name`, `time`, `description`, `scope`) VALUES
(1, 1, 1, 'Dogodek za biologe', '0000-00-00 00:00:00', 'Opis dogodka tukaj', NULL),
(2, 2, 6, 'Dogodek za Strojnike', '0000-00-00 00:00:00', 'Opis dogodka tukaj', NULL),
(3, 3, 4, 'Dogodek za Obramboslovce 1', '0000-00-00 00:00:00', 'Opis dogodka tukaj', NULL),
(4, 3, 4, 'Dogodek za Obramboslovce 2', '0000-00-00 00:00:00', 'Opis dogodka tukaj', NULL),
(5, 5, 3, 'Dogodek za Matematike 1', '0000-00-00 00:00:00', 'Opis dogodka tukaj', NULL),
(6, 5, 3, 'Dogodek za Matematike 2', '0000-00-00 00:00:00', 'Opis dogodka tukaj', NULL),
(7, 5, 3, 'Dogodek za Matematike 3', '0000-00-00 00:00:00', 'Opis dogodka tukaj', NULL);

-- --------------------------------------------------------

--
-- Struktura tabele `event_file`
--

CREATE TABLE IF NOT EXISTS `event_file` (
  `event_file_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `path_to_file` text NOT NULL,
  `file_name` char(100) NOT NULL,
  PRIMARY KEY (`event_file_id`),
  KEY `FK_Relationship_6` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabele `friend`
--

CREATE TABLE IF NOT EXISTS `friend` (
  `friend_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`friend_id`,`user_id`),
  KEY `FK_Relationship_5` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo `friend`
--

INSERT INTO `friend` (`friend_id`, `user_id`) VALUES
(2, 1),
(3, 1),
(4, 1),
(1, 2),
(5, 2),
(1, 3),
(6, 3),
(1, 4),
(3, 5),
(10, 5),
(4, 6),
(10, 6),
(8, 7),
(7, 8),
(9, 8),
(8, 9),
(5, 10),
(6, 10),
(13, 11),
(14, 11),
(14, 12),
(11, 13),
(11, 14),
(12, 14);

-- --------------------------------------------------------

--
-- Struktura tabele `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) NOT NULL,
  `group_avatar` varchar(100) DEFAULT NULL,
  `groups_owner` int(11) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Odloži podatke za tabelo `groups`
--

INSERT INTO `groups` (`group_id`, `group_name`, `group_avatar`, `groups_owner`) VALUES
(1, 'Biologi', NULL, 1),
(2, 'Kemiki', NULL, 1),
(3, 'Matematiki', NULL, 1),
(4, 'Strojniki', NULL, 1),
(5, 'Filozofi', NULL, 1),
(6, 'Zdravstveniki', NULL, 1),
(7, 'Medicinci', NULL, 1),
(8, 'Elektrotehniki', NULL, 1),
(9, 'Glasbeniki', NULL, 1),
(10, 'Likovniki', NULL, 1),
(11, 'Pedagogi', NULL, 1),
(12, 'Obramboslovci', NULL, 1);

-- --------------------------------------------------------

--
-- Struktura tabele `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Odloži podatke za tabelo `location`
--

INSERT INTO `location` (`location_id`, `name`, `latitude`, `longitude`) VALUES
(1, 'Ljubljana', 46.0569, 14.5058),
(2, 'Berlin', 52.52, 13.405),
(3, 'Zagreb', 45.815, 15.9819),
(4, 'Tokyo', 35.709, 139.732),
(5, 'London', 51.5074, -0.127758);

-- --------------------------------------------------------

--
-- Struktura tabele `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `user_last_name` varchar(100) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `user_avatar` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Odloži podatke za tabelo `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_last_name`, `username`, `password`, `user_avatar`, `email`) VALUES
(1, 'robert', 'rozina', 'rr', 'rr', 'images/IMG_20150103_163748.jpg', 'rr@gmail.com'),
(2, 'ales', 'bokal', 'ab', 'ab', NULL, 'ab@gmail.com'),
(3, 'jure', 'sorn', 'js', 'js', NULL, 'js@gmail.com'),
(4, 'jaka', 'plut', 'jp', 'jp', NULL, 'jp@gmail.com'),
(5, 'france', 'presern', 'fp', 'fp', NULL, 'fp@gmail.com'),
(6, 'valentino', 'rossi', 'vr', 'vr', NULL, 'vr@gmail.com'),
(7, 'anton', 'askerc', 'aa', 'aa', NULL, 'aa@gmail.com'),
(8, 'ghostface', 'killa', 'gk', 'gk', NULL, 'gk@gmail.com'),
(9, 'slim', 'shady', 'ss', 'ss', NULL, 'ss@gmail.com'),
(10, 'charles', 'xavier', 'cx', 'cx', NULL, 'cx@gmail.com'),
(11, 'stric', 'bedanc', 'sb', 'sb', NULL, 'sb@gmail.com'),
(12, 'jules', 'winnfield', 'jw', 'jw', NULL, 'jw@gmail.com'),
(13, 'jack', 'sparrow', 'js', 'js', NULL, 'js@gmail.com'),
(14, 'vincent', 'vega', 'vv', 'vv', NULL, 'vv@gmail.com');

-- --------------------------------------------------------

--
-- Struktura tabele `user_event`
--

CREATE TABLE IF NOT EXISTS `user_event` (
  `user_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  KEY `user_id` (`user_id`,`event_id`),
  KEY `event_id` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Odloži podatke za tabelo `user_event`
--

INSERT INTO `user_event` (`user_id`, `event_id`) VALUES
(1, 4),
(2, 3),
(2, 4),
(2, 5),
(3, 4),
(4, 4),
(4, 5),
(4, 5),
(5, 4),
(5, 5),
(6, 4);

-- --------------------------------------------------------

--
-- Struktura tabele `user_group`
--

CREATE TABLE IF NOT EXISTS `user_group` (
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`group_id`,`user_id`),
  KEY `FK_Relationship_7` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo `user_group`
--

INSERT INTO `user_group` (`group_id`, `user_id`) VALUES
(2, 1),
(1, 2),
(4, 2),
(6, 2),
(2, 3),
(3, 3),
(2, 4),
(1, 5),
(2, 6),
(3, 6),
(1, 7),
(4, 7),
(1, 8),
(2, 8),
(4, 8),
(5, 9),
(1, 10),
(2, 10),
(3, 10),
(4, 11),
(5, 11),
(6, 11),
(4, 12),
(6, 12),
(3, 13),
(6, 13);

--
-- Omejitve tabel za povzetek stanja
--

--
-- Omejitve za tabelo `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `FK_Relationship_4` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
  ADD CONSTRAINT `FK_Relationship_8` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Omejitve za tabelo `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `FK_Relationship_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`),
  ADD CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`);

--
-- Omejitve za tabelo `event_file`
--
ALTER TABLE `event_file`
  ADD CONSTRAINT `FK_Relationship_6` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`);

--
-- Omejitve za tabelo `friend`
--
ALTER TABLE `friend`
  ADD CONSTRAINT `FK_Relationship_5` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Omejitve za tabelo `user_event`
--
ALTER TABLE `user_event`
  ADD CONSTRAINT `user_event_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `user_event_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`);

--
-- Omejitve za tabelo `user_group`
--
ALTER TABLE `user_group`
  ADD CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`),
  ADD CONSTRAINT `FK_Relationship_7` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
