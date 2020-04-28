
-- --------------------------------------------------------

--
-- Table structure for table `states`
--

CREATE TABLE `states` (
  `state_abv` varchar(2) NOT NULL,
  `state_name` text NOT NULL,
  `state_idn` int(11) NOT NULL,
  `feature_idn` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
