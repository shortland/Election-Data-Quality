# Election Data Quality

## Project Description

CSE 416 course project. The goal of the project is to provide users with an easily understandable visual representation of different elections' voting data in a comprehensive and unified manner.

There is currently no mainstream reference in gathering and visualizing election data in the U.S. at the detailed level of a precinct/election ward. The hopeful goal of this project is to provide something that can be the start of exactly that.

Live site: https://shortland.github.io/Election-Data-Quality/

### Project Members

- David Lin [@DavidLSC](https://github.com/DavidLSC)
- Ilan Kleiman [@shortland](https://github.com/shortland)
- Reed Gantz [@reedm121](https://github.com/reedm121)
- Samuel Hoffmann [@SamuelHHoffmann](https://github.com/SamuelHHoffmann)

## Frontend

Developed in Javascript with React and Bootstrap.

[react-mapbox-gl](https://uber.github.io/react-map-gl/docs)

### Frontend Install/Build/Run/Deploy

After cloning the directory and entering the `frontend` directory, follow the below instructions.

#### Install Dependencies

`$ yarn`

#### Run Project

`$ yarn start`

#### Deploy Project to GitHub-Pages

`$ yarn run deploy`

## Backend

Developed in Java with SpringBoot.

### Backend Install/Build/Run/Deploy

After cloning the directory and entering the `backend` directory, follow the below instructions.

**Note**: this project uses gradle - not maven.

#### Building .jar executable

`$ ./gradlew build`

#### Deploying latest server-code to the server

`$ ./gradlew deploy`

More info on how the backend handles the deploy can be found in the branch: `upgrader-servlet`.

#### Executing .jar executable locally

`$ java -jar build/libs/rest-service-0.0.1-SNAPSHOT.jar`

## Database

MySQL Server.

### Database Login

Web Interface: [Login](http://45.55.121.121/phpmyadmin)

```text
Username: jerryman
Password: JerryManderingIsBad123!
IP Address: 45.55.121.121:3306
```

## States

Sample State Data

- Wisconsin
- Utah
- New York

## Data Sources

### Wisconsin

Geographic Data:

- [Precinct Boundries (wards)](https://data-ltsb.opendata.arcgis.com/datasets/d0d0b8de487f431281e2be3d488b0825)

- [Congressional Boundries](https://data-ltsb.opendata.arcgis.com/datasets/wisconsin-congressional-districts-2011/data)

- [Counties](https://data-ltsb.opendata.arcgis.com/datasets/440ac07e5a174e29b28663d15d5f7d95)

Demographic Data:

- [2010 Census](https://www2.census.gov/census_2010/03-Demographic_Profile_with_SF1geos/?#)

Voter Data:

- [2016 Presidential](https://data-ltsb.opendata.arcgis.com/datasets/2012-2020-election-data-with-2018-wards)

- [2016 Congressional](https://data-ltsb.opendata.arcgis.com/datasets/2012-2020-election-data-with-2018-wards)

- [2018 Congressional](https://data-ltsb.opendata.arcgis.com/datasets/2012-2020-election-data-with-2018-wards)

Other Data:

- State Parks

### Utah

Geographic Data:

- [Precinct Boundries](https://gis.utah.gov/data/political/voter-precincts/)

- [Congressional Boundries](https://gis.utah.gov/data/political/2012-2021-house-senate-congressional-districts/)

- [Counties](https://gis.utah.gov/data/boundaries/citycountystate/)

Demographic Data

- [2010 Census](https://www2.census.gov/census_2010/03-Demographic_Profile_with_SF1geos/?#)

- [2010 Census](https://gis.utah.gov/data/demographic/census/#2010Census)

Voter Data

- [2016 Presidential](https://myaccount.dropsend.com/file/1d42ada04fb995c8)

- [2016 Congressional](https://myaccount.dropsend.com/file/1d42ada04fb995c8)

- [2018 Congressional](https://myaccount.dropsend.com/file/1d42ada04fb995c8)

Other Data:

- [State Parks](https://gis.utah.gov/data/boundaries/wilderness/)

### New York

Geographic Data:

- [Precinct Boundries](https://dataverse.harvard.edu/dataset.xhtml?persistentId=hdl:1902.1/16320&studyListingIndex=2_3cfc56a7c5a06219bd1114590f1c)

- Congressional Boundries

- [Counties](http://gis.ny.gov/gisdata/inventories/details.cfm?DSID=927)

Demographic Data:

- [2010 Census](https://www2.census.gov/census_2010/03-Demographic_Profile_with_SF1geos/?#)

Voter Data:

- [2016 Presidential](https://www.elections.ny.gov/2016ElectionResults.html) (in xls format) (only by county)

- [2016 Congressional](https://www.elections.ny.gov/2016ElectionResults.html) (in xls format) (only by county)

- [2018 Congressional](https://www.elections.ny.gov/2018ElectionResults.html) (in xls format) (only by county)

County FIPS Codes:

- County FIPS is a unique number that identifies each county in the whole country.

- Our precinct boundary data (from Harvard) shows each of the precincts with a County FIPS code concatenated with county-level-precinct # identifying the precinct (# starting from 1..n precincts in the whole state), so this code help in identifying what goes where.

- [FIPS to County Code Chart](https://www.nrcs.usda.gov/wps/portal/nrcs/detail/national/home/?cid=nrcs143_013697)

Must visit each individual NYS county's website to get the results of its precincts.

<!-- - [Albany County](#)

- [Allegany County](#)

- [Bronx County](#)

- [Broome County](#)

- [Cattaraugus County ](#)

- [Cayuga County ](#)

- [Chautauqua County](https://chqgov.com/board-elections/historical-election-results)

- [Chemung County](#)

- [Chenango County](#)

- [Clinton County](#)

- [Columbia County](#)

- [Cortland County](#)

- [Delaware County](#)

- [Dutchess County](#)

- [Erie County](#)

- [Essex County](#)

- [Franklin County](#)

- [Fulton County](#)

- [Genesee County](#)

- [Greene County](#)

- [Hamilton County](#)

- [Herkimer County](#)

- [Jefferson County](#)

- [Kings County](#)

- [Kings County](#)

- [Lewis County](#)

- [Livingston County](#)

- [Madison County](#)

- [Monroe County](#)

- [Montgomery County](#)

- [Nassau County](#)

- [New York County](#)

- [Niagara County](#)

- [Oneida County](#)

- [Onondaga County](#)

- [Ontario County](#)

- [Orange County](#)

- [Orleans County](#)

- [Oswego County](#)

- [Otsego County](#)

- [Putnam County](#)

- [Queens County](#)

- [Rensselaer County](#)

- [Richmond County](#)

- [Rockland County](#)

- [St. Lawrence County](#)

- [Saratoga County](#)

- [Schenectady County](#)

- [Schoharie County](#)

- [Schuyler County](#)

- [Seneca County](#)

- [Steuben County](#)

- [Suffolk County](#)

- [Sullivan County](#)

- [Tioga County](#)

- [Tompkins County](#)

- [Ulster County](#)

- [Warren County](#)

- [Washington County](#)

- [Wayne County](#)

- [Westchester County](#)

- [Wyoming County](#)

- [Yates County](#)

Other Data:

- [State Parks](https://gis.ny.gov/gisdata/inventories/details.cfm?DSID=430) -->
