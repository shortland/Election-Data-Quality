
-- --------------------------------------------------------

--
-- Table structure for table `precincts`
--

CREATE TABLE `precincts` (
  `idn` int(11) NOT NULL,
  `county_fips_code` varchar(5) NOT NULL,
  `area_idn` int(11) NOT NULL,
  `geo_id_10` varchar(10) NOT NULL,
  `name_10` varchar(10) NOT NULL,
  `assembly_district_num` varchar(100) NOT NULL,
  `election_district_num` varchar(100) NOT NULL,
  `congressional_district_num` varchar(100) NOT NULL,
  `senate_district_num` varchar(100) NOT NULL,
  `county_legislative_district` varchar(100) NOT NULL,
  `election_or_assembly_num` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `precincts`
--

INSERT INTO `precincts` (`idn`, `county_fips_code`, `area_idn`, `geo_id_10`, `name_10`, `assembly_district_num`, `election_district_num`, `congressional_district_num`, `senate_district_num`, `county_legislative_district`, `election_or_assembly_num`) VALUES
(1, '36005', 1, '360051', '7600001', '76', '1', '16', '33', '15', '76,001');
