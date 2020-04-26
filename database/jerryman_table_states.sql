
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

--
-- Dumping data for table `states`
--

INSERT INTO `states` (`state_abv`, `state_name`, `state_idn`, `feature_idn`) VALUES
('NY', 'New York', 36, 2),
('UT', 'Utah', 49, 3),
('WI', 'Wisconsin', 55, 1);
