
-- --------------------------------------------------------

--
-- Table structure for table `counties`
--

CREATE TABLE `counties` (
  `fips_code` varchar(5) NOT NULL,
  `state_abv` varchar(2) NOT NULL,
  `county_name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `counties`
--

INSERT INTO `counties` (`fips_code`, `state_abv`, `county_name`) VALUES
('36005', 'NY', 'bronx');
