
-- --------------------------------------------------------

--
-- Table structure for table `areas`
--

CREATE TABLE `areas` (
  `idn` int(11) NOT NULL,
  `area_name` varchar(200) NOT NULL,
  `area_type` enum('city','town','borough') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `areas`
--

INSERT INTO `areas` (`idn`, `area_name`, `area_type`) VALUES
(1, 'bronx', 'borough');
