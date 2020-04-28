
-- --------------------------------------------------------

--
-- Table structure for table `areas`
--

CREATE TABLE `areas` (
 `idn` int(11) NOT NULL AUTO_INCREMENT,
 `area_name` varchar(200) NOT NULL,
 `area_type` enum('city','town','borough') NOT NULL,
 PRIMARY KEY (`idn`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1
