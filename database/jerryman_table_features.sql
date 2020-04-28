
-- --------------------------------------------------------

--
-- Table structure for table `features`
--

CREATE TABLE `features` (
  `idn` int(11) NOT NULL,
  `notes` text NOT NULL,
  `geometry` json NOT NULL,
  `properties` json NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
