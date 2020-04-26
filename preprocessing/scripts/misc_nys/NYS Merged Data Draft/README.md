# Merge Geo & Election Data

Script that merges Geo & Election data. So we can associate a precinct with the NYS Shapefile precinct boundaries.
"NAME10" and "GEOID10" are identifiers pre-existing in the Shapefile - we can now easily associate this data with those identifiers.

## How to run

`$ python3 JoinGeoElection.py NyCountyFips.csv ny_geo.tab ny_elect.tab`

Or to output to json file, simply redirect output:

`$ python3 JoinGeoElection.py NyCountyFips.csv ny_geo.tab ny_elect.tab > output.json`

## File Sources

[NyCountyFips.csv](https://www.nrcs.usda.gov/wps/portal/nrcs/detail/national/home/?cid=nrcs143_013697)

[ny_geo.tab](https://dataverse.harvard.edu/dataset.xhtml?persistentId=hdl:1902.1/16320&studyListingIndex=2_3cfc56a7c5a06219bd1114590f1c)

[ny_elect.tab](https://dataverse.harvard.edu/dataset.xhtml?persistentId=hdl:1902.1/16320&studyListingIndex=2_3cfc56a7c5a06219bd1114590f1c)