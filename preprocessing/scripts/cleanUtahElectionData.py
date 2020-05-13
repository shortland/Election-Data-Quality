import geopandas
import sys
import json
import urllib
import ssl
import time
import requests

# This script takes a precinct geojson file with neighbors and determines which are fully enclosed.

if __name__ == '__main__':

    precinctFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Utah/Utah_Precinct_Data.GeoJSON"
    cong18FileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Original Data/utah_elections/utah18senate.json"
    cong16FileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Original Data/utah_elections/utah16senate.json"
    pres16FileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Original Data/utah_elections/utah16pres.json"
    outputFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Utah/Utah_Election_Data.json"
    errorOutputFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Utah/Utah_Election_Data_Errors.json"

    file = open(cong18FileName, mode="r")
    cong18Data = json.load(file)
    file.close()
    # index 2 - 30
    # cong18Data[i]['2018 General Election Results'] = county

    file = open(cong16FileName, mode="r")
    cong16Data = json.load(file)
    file.close()
    #index 2 - 30
    #cong16Data[i]['2016 General Election Results'] = county

    file = open(pres16FileName, mode="r")
    pres16Data = json.load(file)
    file.close()
    # index 2 - 30
    # pres16Data[i]['2016 General Election Results'] = county

    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)

    # Utah = 49, New York = 36, Wisconsin = 55
    filteredData = {"STATE_ID": "49", "PRECINCT_ELECTION_DATA": []}
    errorData = {"STATE_ID": "49", "PRECINCT_ELECTION_DATA_NO_DATA_ERROR": []}

    for index, precinct in precinctGDF.iterrows():
        other = (precinct['CNTY_NAME']).upper()

        eCount = 0
        found = False

        #2016 pres
        for x in range(2, 31):
            county = (pres16Data[x]['2016 General Election Results']+"").upper()
            if county.__eq__(other):
                found = True
                filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinct['GEOID'],
                                                               "republican_vote": int("0"+pres16Data[x]['field7']),
                                                               "democrat_vote": int("0"+pres16Data[x]['field9']),
                                                               "libratarian_vote": int("0"+pres16Data[x]['field5']),
                                                               "other_vote": int("0"+pres16Data[x]['field3'])+int("0"+pres16Data[x]['field4'])+int("0"+pres16Data[x]['field6'])+int("0"+pres16Data[x]['field8'])+int("0"+pres16Data[x]['field10'])+int("0"+pres16Data[x]['field11'])+int("0"+pres16Data[x]['field13'])+int("0"+pres16Data[x]['field14'])+int("0"+pres16Data[x]['field15'])+int("0"+pres16Data[x]['field16'])+int("0"+pres16Data[x]['field17'])+int("0"+pres16Data[x]['field18'])+int("0"+pres16Data[x]['field19'])+int("0"+pres16Data[x]['field20'])+int("0"+pres16Data[x]['field21'])+int("0"+pres16Data[x]['field22'])+int("0"+pres16Data[x]['field23'])+int("0"+pres16Data[x]['field24'])+int("0"+pres16Data[x]['field25'])+int("0"+pres16Data[x]['field26']),
                                                               "election": "PRES2016",
                                                               "data_id": precinct['GEOID']+"_PRES2016"})
        if not found:
            eCount += 1
        found = False

        #2016 cong
        for x in range(2, 31):
            county = (cong16Data[x]['2016 General Election Results']+"").upper()
            if county.__eq__(other):
                filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinct['GEOID'],
                                                               "republican_vote": int("0"+'0'+cong16Data[x]['field5'])+int("0"+cong16Data[x]['field14'])+int("0"+cong16Data[x]['field17']),
                                                               "democrat_vote": int("0"+cong16Data[x]['field9'])+int("0"+cong16Data[x]['field11'])+int("0"+cong16Data[x]['field16']),
                                                               "libratarian_vote": int("0"+cong16Data[x]['field7']),
                                                               "other_vote": int("0"+cong16Data[x]['field3'])+int("0"+cong16Data[x]['field4'])+int("0"+cong16Data[x]['field8'])+int("0"+cong16Data[x]['field12']),
                                                               "election": "CONG2016",
                                                               "data_id": precinct['GEOID']+"_CONG2016"})
        if not found:
            eCount += 1
        found = False

        #2018 cong
        for x in range(2, 31):
            county = (cong18Data[x]['2018 General Election Results']+"").upper()
            if county.__eq__(other):
                filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinct['GEOID'],
                                                               "republican_vote": int("0"+cong18Data[x]['field6']),
                                                               "democrat_vote": int("0"+cong18Data[x]['field5']),
                                                               "libratarian_vote": int("0"+cong18Data[x]['field3']),
                                                               "other_vote": int("0"+cong18Data[x]['field4'])+int("0"+cong18Data[x]['field7'])+int("0"+cong18Data[x]['field8'])+int("0"+cong18Data[x]['field9'])+int("0"+cong18Data[x]['field10'])+int("0"+cong18Data[x]['field11'])+int("0"+cong18Data[x]['field12'])+int("0"+cong18Data[x]['field13']),
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
