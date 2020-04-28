
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
