
--
-- Indexes for dumped tables
--

--
-- Indexes for table `areas`
--
ALTER TABLE `areas`
  ADD PRIMARY KEY (`idn`);

--
-- Indexes for table `congressional_districts`
--
ALTER TABLE `congressional_districts`
  ADD KEY `state_abv` (`state_abv`);

--
-- Indexes for table `counties`
--
ALTER TABLE `counties`
  ADD UNIQUE KEY `UNIQUE` (`fips_code`),
  ADD KEY `state_abv` (`state_abv`);

--
-- Indexes for table `features`
--
ALTER TABLE `features`
  ADD PRIMARY KEY (`idn`);

--
-- Indexes for table `precincts`
--
ALTER TABLE `precincts`
  ADD PRIMARY KEY (`idn`),
  ADD KEY `county_fips_code` (`county_fips_code`),
  ADD KEY `area_idn` (`area_idn`);

--
-- Indexes for table `states`
--
ALTER TABLE `states`
  ADD PRIMARY KEY (`state_idn`),
  ADD UNIQUE KEY `UNIQUE` (`state_abv`),
  ADD KEY `feature_idn` (`feature_idn`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `areas`
--
ALTER TABLE `areas`
  MODIFY `idn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `features`
--
ALTER TABLE `features`
  MODIFY `idn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `precincts`
--
ALTER TABLE `precincts`
  MODIFY `idn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `congressional_districts`
--
ALTER TABLE `congressional_districts`
  ADD CONSTRAINT `congressional_districts_ibfk_1` FOREIGN KEY (`state_abv`) REFERENCES `states` (`state_abv`);

--
-- Constraints for table `counties`
--
ALTER TABLE `counties`
  ADD CONSTRAINT `counties_ibfk_1` FOREIGN KEY (`state_abv`) REFERENCES `states` (`state_abv`);

--
-- Constraints for table `precincts`
--
ALTER TABLE `precincts`
  ADD CONSTRAINT `precincts_ibfk_1` FOREIGN KEY (`county_fips_code`) REFERENCES `counties` (`fips_code`),
  ADD CONSTRAINT `precincts_ibfk_2` FOREIGN KEY (`area_idn`) REFERENCES `areas` (`idn`);

--
-- Constraints for table `states`
--
ALTER TABLE `states`
  ADD CONSTRAINT `states_ibfk_1` FOREIGN KEY (`feature_idn`) REFERENCES `features` (`idn`);
