import geopandas
import sys
import json
import urllib
import ssl
import time
import requests

# This script takes a precinct geojson file with neighbors and determines which are fully enclosed.

if __name__ == '__main__':

    precinctFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/New York/New_York_Precinct_Data.GeoJSON"
    cong18FileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Original Data/new_york_elections/18congNY.json"
    cong16FileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Original Data/new_york_elections/16congNY.json"
    pres16FileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Original Data/new_york_elections/16presNY.json"
    outputFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/New York/New_York_Election_Data.json"
    errorOutputFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/New York/New_York_Election_Data_Errors.json"
    countyMapFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Original Data/new_york_elections/county_To_Fips.json"

    file = open(countyMapFileName, mode='r')
    countyMapJSON = json.load(file)
    file.close()

    countyDataMap = {}

    for dataItem in countyMapJSON['data']:
        if dataItem[10] not in countyDataMap.keys():
            countyDataMap[dataItem[10]] = dataItem[8]


    file = open(cong18FileName, mode="r")
    cong18Data = json.load(file)
    file.close()

    file = open(cong16FileName, mode="r")
    cong16Data = json.load(file)
    file.close()

    file = open(pres16FileName, mode="r")
    pres16Data = json.load(file)
    file.close()

    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)

    # Utah = 49, New York = 36, Wisconsin = 55
    filteredData = {"STATE_ID": "36", "PRECINCT_ELECTION_DATA": []}
    errorData = {"STATE_ID": "36", "PRECINCT_ELECTION_DATA_NO_DATA_ERROR": []}

    for index, precinct in precinctGDF.iterrows():
        other = (countyDataMap[precinct['COUNTYFP10']]).upper()

        eCount = 0
        found = False

        #2016 pres
        for x in range(1, 63):
            county = (pres16Data[x]['County']+"").upper()
            if county.__eq__(other):
                found = True
                filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinct['GEOID'],
                                                               "republican_vote": int("0"+pres16Data[x]['Trump    Pence']),
                                                               "democrat_vote": int("0"+pres16Data[x]['Clinton   Kaine']),
                                                               "libratarian_vote": int("0"+pres16Data[x]['Johnson  Weld']),
                                                               "other_vote": int("0"+pres16Data[x]['Stein   Baraka']),
                                                               "election": "PRES2016",
                                                               "data_id": precinct['GEOID']+"_PRES2016"})
        if not found:
            eCount += 1
        found = False

        #2016 cong
        for x in range(3, 65):
            county = (cong16Data[x]['NYS Board of Elections U']['S'][' Senator Election Returns November 8, 2016']+"").upper()
            if county.__eq__(other):
                filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinct['GEOID'],
                                                               "republican_vote": int("0"+'0'+cong16Data[x]['field3']),
                                                               "democrat_vote": int("0"+cong16Data[x]['field2']),
                                                               "libratarian_vote": int("0"+cong16Data[x]['field10']),
                                                               "other_vote": int("0"+cong16Data[x]['field4'])+int("0"+cong16Data[x]['field5'])+int("0"+cong16Data[x]['field6'])+int("0"+cong16Data[x]['field7'])+int("0"+cong16Data[x]['field8'])+int("0"+cong16Data[x]['field9']),
                                                               "election": "CONG2016",
                                                               "data_id": precinct['GEOID']+"_CONG2016"})
        if not found:
            eCount += 1
        found = False

        #2018 cong
        for x in range(1, 63):
            county = (cong18Data[x]['US Senator - General Election - November 6, 2018']+"").upper()
            if other in county:
                filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinct['GEOID'],
                                                               "republican_vote": int("0"+cong18Data[x]['field3']),
                                                               "democrat_vote": int("0"+cong18Data[x]['field2']),
                                                               "libratarian_vote": 0,
                                                               "other_vote": int("0"+cong18Data[x]['field4'])+int("0"+cong18Data[x]['field5'])+int("0"+cong18Data[x]['field6'])+int("0"+cong18Data[x]['field7'])+int("0"+cong18Data[x]['field8']),
                                                               "election": "CONG2018",
                                                               "data_id": precinct['GEOID']+"_CONG2018"})

        if not found:
            eCount += 1
        found = False

        if eCount == 3:
            errorData["PRECINCT_ELECTION_DATA_NO_DATA_ERROR"].append({"precinct_idn": precinct['GEOID']})

    outputFile = open(outputFileName, mode="w")
    outputFile.write(json.dumps(filteredData))
    outputFile.close()

    outputFile = open(errorOutputFileName, mode="w")
    outputFile.write(json.dumps(errorData))
    outputFile.close()
