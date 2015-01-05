-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Gostitelj: 127.0.0.1
-- Čas nastanka: 05. jan 2015 ob 12.52
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabele `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `geoloc` varchar(100) NOT NULL,
  `group_id` int(11) NOT NULL,
  `event_name` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `time` datetime NOT NULL,
  `description` text NOT NULL,
  `scope` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK_Relationship_2` (`geoloc`),
  KEY `FK_Relationship_3` (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Struktura tabele `event_file`
--

CREATE TABLE IF NOT EXISTS `event_file` (
  `event_file_id` varchar(100) NOT NULL,
  `event_id` int(11) NOT NULL,
  `path_to_file` text NOT NULL,
  `file_name` char(10) NOT NULL,
  PRIMARY KEY (`event_file_id`),
  KEY `FK_Relationship_6` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabele `friend`
--

CREATE TABLE IF NOT EXISTS `friend` (
  `friend_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`friend_id`),
  KEY `FK_Relationship_5` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabele `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) NOT NULL,
  `group_avatar` varchar(100) DEFAULT NULL,
  `groups_owner` varchar(100) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Odloži podatke za tabelo `groups`
--

INSERT INTO `groups` (`group_id`, `group_name`, `group_avatar`, `groups_owner`) VALUES
(1, 'Biologi', NULL, ''),
(2, 'Kemiki', NULL, ''),
(3, 'Matematiki', NULL, '');

-- --------------------------------------------------------

--
-- Struktura tabele `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `geoloc` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`geoloc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Odloži podatke za tabelo `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_last_name`, `username`, `password`, `user_avatar`, `email`) VALUES
(1, 'Jaka', 'Plut', 'jPlut', '123', NULL, 'jplut@gmail.com'),
(2, 'Robert', 'Rozina', 'rRozina', '123', NULL, 'rRozina@gmail.com');

-- --------------------------------------------------------

--
-- Struktura tabele `user_group`
--

CREATE TABLE IF NOT EXISTS `user_group` (
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`group_id`,`user_id`),
  KEY `FK_Relationship_7` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Odloži podatke za tabelo `user_group`
--

INSERT INTO `user_group` (`group_id`, `user_id`) VALUES
(2, 1),
(1, 2);

--
-- Omejitve tabel za povzetek stanja
--

--
-- Omejitve za tabelo `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `FK_Relationship_8` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FK_Relationship_4` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`);

--
-- Omejitve za tabelo `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`),
  ADD CONSTRAINT `FK_Relationship_2` FOREIGN KEY (`geoloc`) REFERENCES `location` (`geoloc`);

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
-- Omejitve za tabelo `user_group`
--
ALTER TABLE `user_group`
  ADD CONSTRAINT `FK_Relationship_7` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
