-- phpMyAdmin SQL Dump
-- version 4.5.0.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 24, 2015 at 09:57 AM
-- Server version: 10.0.17-MariaDB
-- PHP Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `GCMDemo`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `location` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`id`, `name`, `description`, `time`, `location`) VALUES
(7, 'eventDemo', 'presentMeFree', '2015-11-27 07:30:00', 'HW312'),
(11, 'eventDemo', 'presentMeFree', '2015-11-27 07:30:00', 'HW312'),
(12, 'eventDemo', 'presentMeFree', '2015-11-27 07:30:00', 'HW312');

-- --------------------------------------------------------

--
-- Table structure for table `activityAssoc`
--

CREATE TABLE `activityAssoc` (
  `event_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `activityAssoc`
--

INSERT INTO `activityAssoc` (`event_id`, `username`) VALUES
(7, 'braden'),
(11, 'braden'),
(12, 'braden');

-- --------------------------------------------------------

--
-- Table structure for table `contacts`
--

CREATE TABLE `contacts` (
  `name` varchar(50) NOT NULL DEFAULT '',
  `friendname` varchar(50) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contacts`
--

INSERT INTO `contacts` (`name`, `friendname`) VALUES
('abv', 'braden'),
('berry', 'berry'),
('braden', 'abv'),
('braden', 'xyz'),
('ddddf', 'ggh'),
('ddddf', 'ttttt'),
('ddddf', 'xyz'),
('ggh', 'ddddf'),
('how', 'how'),
('howwww', 'tret'),
('tret', 'howwww'),
('ttttt', 'ddddf'),
('xyz', 'braden'),
('xyz', 'ddddf');

-- --------------------------------------------------------

--
-- Table structure for table `gcm_users`
--

CREATE TABLE `gcm_users` (
  `name` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gcm_regid` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gcm_users`
--

INSERT INTO `gcm_users` (`name`, `created_at`, `gcm_regid`) VALUES
('abe', '2015-11-24 07:06:32', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('abv', '2015-11-24 07:22:26', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('berry', '2015-11-24 08:21:27', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('braden', '0000-00-00 00:00:00', '2015-11-20 17:55:39'),
('ddddf', '2015-11-20 09:24:15', 'd6epLZDqLpY:APA91bErSEBtE-hN4FhRXsNZ2EOIjvMdqxGI0dNNjt9zQB76-KmyU8zrn3qQMzOie9cTeXYG9zoNLWMSZEB14oHm21M0YDLlTkr7fwL2sNlb4C7oZ7WteAMvoucxEFHWIZk6cOM6tNYK'),
('faster', '2015-11-24 06:49:57', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('ggh', '2015-11-24 07:26:11', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('hi', '2015-11-24 08:47:25', 'dWTj7R4Cs24:APA91bF_8vrxwVXwLuU4Vi3Wp3pPLxalWLCnw17hwi8Zwo6cpBeTG3yl38xyElJdw2RDqdhdD-xNCFZn9HvbBMUprZr_LjDVspQ1CZnhk1BpO4d1XhTwAJk8jVUxB3n7KeKKtX7Rt-2D'),
('how', '2015-11-24 08:13:33', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('howwww', '2015-11-24 08:34:26', 'dWTj7R4Cs24:APA91bF_8vrxwVXwLuU4Vi3Wp3pPLxalWLCnw17hwi8Zwo6cpBeTG3yl38xyElJdw2RDqdhdD-xNCFZn9HvbBMUprZr_LjDVspQ1CZnhk1BpO4d1XhTwAJk8jVUxB3n7KeKKtX7Rt-2D'),
('res', '2015-11-24 07:37:53', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('rewq', '2015-11-24 08:00:28', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('slow', '2015-11-24 06:53:57', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('souravc12', '2015-11-24 07:43:00', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('tret', '2015-11-24 08:34:43', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('ttttt', '2015-11-24 07:35:01', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('tuft', '2015-11-24 07:56:04', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj'),
('wheee', '2015-11-24 07:59:20', 'cagg1Q_bG1Q:APA91bEMJJ0C2N5FxDEKRQVBn3EXkvfJj1Vsg2n2trQCmbVoLJ20rVoZrWxg8N0dHLiiBubTsd-DAycCfIQDUbrZToKg3njV6yNMXdeKiIqlZxbk3_JdjHYlVwlezZNxI7q6S7kHmrJM'),
('xyz', '2015-11-24 07:13:09', 'fjIf5DYiaA8:APA91bGDLu3xvu02rXiTD1d3JPti4dmxGipsispMVYwQoVs-nzecob1mnTX104Jz_HrtEB6G6aS0nUTM2HA4YqSxn77oMpZyczbHvH4ugQnhg9YX-Tmv2y3WfZrg9WAozE0bZivG--Bj');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `activityAssoc`
--
ALTER TABLE `activityAssoc`
  ADD PRIMARY KEY (`event_id`,`username`),
  ADD KEY `username` (`username`);

--
-- Indexes for table `contacts`
--
ALTER TABLE `contacts`
  ADD PRIMARY KEY (`name`,`friendname`),
  ADD KEY `friendname` (`friendname`);

--
-- Indexes for table `gcm_users`
--
ALTER TABLE `gcm_users`
  ADD PRIMARY KEY (`name`),
  ADD KEY `gcm_regid` (`gcm_regid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `activityAssoc`
--
ALTER TABLE `activityAssoc`
  ADD CONSTRAINT `activityassoc_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `activity` (`id`),
  ADD CONSTRAINT `activityassoc_ibfk_2` FOREIGN KEY (`username`) REFERENCES `gcm_users` (`name`);

--
-- Constraints for table `contacts`
--
ALTER TABLE `contacts`
  ADD CONSTRAINT `contacts_ibfk_1` FOREIGN KEY (`name`) REFERENCES `gcm_users` (`name`),
  ADD CONSTRAINT `contacts_ibfk_2` FOREIGN KEY (`friendname`) REFERENCES `gcm_users` (`name`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
