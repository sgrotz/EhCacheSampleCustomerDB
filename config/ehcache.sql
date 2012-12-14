-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 07, 2012 at 09:14 AM
-- Server version: 5.1.44
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ehcache`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE IF NOT EXISTS `customers` (
  `ID` varchar(100) NOT NULL,
  `FIRSTNAME` varchar(200) DEFAULT NULL,
  `LASTNAME` varchar(200) DEFAULT NULL,
  `REGION` varchar(100) NOT NULL,
  `ADDRESS` varchar(250) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`ID`, `FIRSTNAME`, `LASTNAME`, `REGION`, `ADDRESS`) VALUES
('1', 'Stephan', 'Grotz', 'DE', 'Landsberger Strasse 155'),
('2', 'Jan', 'Zahalka', 'DE', 'Landsberger Strasse 156'),
('3', 'Ivo', 'Totev', 'US', 'San Francisco');
