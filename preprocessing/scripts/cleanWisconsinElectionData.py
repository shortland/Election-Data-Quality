import geopandas
import sys
import json
import urllib
import ssl
import time
import requests

# This script takes a precinct geojson file with neighbors and determines which are fully enclosed.

if __name__ == '__main__':
    demographicDataName = ""
    outputFileName = ""
    try:
        demographicDataName : str = sys.argv[1]
        outputFileName: str = sys.argv[2]
    except:
        print("INVALID USAGE: Please input an demographic data file, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/cleanWisconsinElectionData.py \"data/<demographic_data_file>.GeoJSON\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)



    file = open(demographicDataName, mode="r")
    demographicData= json.load(file)

    # Utah = 49, New York = 36, Wisconsin = 55
    filteredData = {"STATE_ID": "55", "PRECINCT_ELECTION_DATA": []}
    errorData = {"STATE_ID": "55", "PRECINCT_ELECTION_DATA_NO_DATA_ERROR": []}

    gcontext = ssl.SSLContext()
    count = 0
    for precinct in demographicData['PRECINCT_DEMOGRAPHICS']:
        count += 1
        id = precinct['PRECINCT_ID']
        url = "https://mapservices.legis.wisconsin.gov/arcgis/rest/services/Election_Data/2012_2020_Election_Data_with_2018_Wards/FeatureServer/0/query?where=WARD_FIPS%20%3D%20%27"+id+"%27&outFields=CNTY_NAME,COUSUBFP,MCD_FIPS,MCD_NAME,WARD_FIPS,WARDID,SUPERID,SUPER_FIPS,ALDERID,ALDER_FIPS,NOTES,PREDEM16,PREREP16,PREGRN16,PRELIB16,PRECON16,PREIND16,PREIND216,PREIND316,PREIND416,PREIND516,PREIND616,PREIND716,PREIND816,PREIND916,PREIND1016,PREIND1116,PRESCT16,OBJECTID_1,CNTY_FIPS,CTV,USSREP216,USSDEM16,USSLIB16,USSREP16,USSSCT16,USHDEM216,USHLIB16,USHIND16,USHREP16,USHDEM16,USHGRN16,USHSCT16,USHIND18,USHSCT18,USHREP18,USHIND218,USHDEM18,USHDEM218,USSIND18,USSREP18,USSSCT18,USSDEM18,USSIND218&returnGeometry=false&outSR=4326&f=json"
        try:
            with urllib.request.urlopen(url, context=gcontext, timeout=20) as url:
                try:
                    data = json.loads(url.read().decode())
                    originalData = data['features'][0]['attributes']

                    zeroCount = 0


                    data = originalData
                    #2016 pres
                    precinctID = data['WARD_FIPS']
                    republicanVote = 0
                    for key in data.keys():
                        if "PREREP" in key:
                            republicanVote += data[key]
                    democratVote = 0
                    for key in data.keys():
                        if "PREDEM" in key:
                            democratVote += data[key]
                    libertarianVote = 0
                    for key in data.keys():
                        if "PRELIB" in key:
                            libertarianVote += data[key]
                    otherVote = 0
                    for key in data.keys():
                        if ("PRE" in key) and ("PREREP" not in key) and ("PREDEM" not in key) and ("PRELIB" not in key):
                            otherVote += data[key]
                    election = 'PRES2016'
                    dataID = ""+precinctID+"_"+election

                    filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinctID,
                                                                  "republican_vote": republicanVote,
                                                                  "democrat_vote": democratVote,
                                                                  "libratarian_vote": libertarianVote,
                                                                  "other_vote": otherVote,
                                                                  "election": election,
                                                                  "data_id": dataID})

                    if (republicanVote + democratVote + libertarianVote + otherVote) == 0:
                        zeroCount += 1


                    #2016 cong
                    precinctID = data['WARD_FIPS']
                    republicanVote = 0
                    for key in data.keys():
                        if ("USHREP" in key or "USSREP" in key) and '16' in key:
                            republicanVote += data[key]
                    democratVote = 0
                    for key in data.keys():
                        if ("USHDEM" in key or "USSDEM" in key) and '16' in key:
                            democratVote += data[key]
                    libertarianVote = 0
                    for key in data.keys():
                        if ("USHLIB" in key or "USSLIB" in key) and '16' in key:
                            libertarianVote += data[key]
                    otherVote = 0
                    for key in data.keys():
                        if '16' in key and ("USH" in key or "USS" in key) and ("USHLIB" not in key) and ("USSLIB" not in key) and ("USHDEM" not in key) and ("USSDEM" not in key) and ("USHREP" not in key) and ("USSREP" not in key):
                            otherVote += data[key]
                    election = 'CONG2016'
                    dataID = ""+precinctID+"_"+election

                    filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinctID,
                                                                   "republican_vote": republicanVote,
                                                                   "democrat_vote": democratVote,
                                                                   "libratarian_vote": libertarianVote,
                                                                   "other_vote": otherVote,
                                                                   "election": election,
                                                                   "data_id": dataID})

                    if (republicanVote + democratVote + libertarianVote + otherVote) == 0:
                        zeroCount += 1

                    #2018 cong
                    precinctID = data['WARD_FIPS']
                    republicanVote = 0
                    for key in data.keys():
                        if ("USHREP" in key or "USSREP" in key) and '18' in key:
                            republicanVote += data[key]
                    democratVote = 0
                    for key in data.keys():
                        if ("USHDEM" in key or "USSDEM" in key) and '18' in key:
                            democratVote += data[key]
                    libertarianVote = 0
                    for key in data.keys():
                        if ("USHLIB" in key or "USSLIB" in key) and '18' in key:
                            libertarianVote += data[key]
                    otherVote = 0
                    for key in data.keys():
                        if '18' in key and ("USH" in key or "USS" in key) and ("USHLIB" not in key) and (
                                "USSLIB" not in key) and ("USHDEM" not in key) and ("USSDEM" not in key) and (
                                "USHREP" not in key) and ("USSREP" not in key):
                            otherVote += data[key]
                    election = 'CONG2018'
                    dataID = ""+precinctID+"_"+election

                    filteredData["PRECINCT_ELECTION_DATA"].append({"precinct_idn": precinctID,
                                                               "republican_vote": republicanVote,
                                                               "democrat_vote": democratVote,
                                                               "libratarian_vote": libertarianVote,
                                                               "other_vote": otherVote,
                                                               "election": election,
                                                               "data_id": dataID})

                    if (republicanVote + democratVote + libertarianVote + otherVote) == 0:
                        zeroCount += 1

                    if zeroCount == 3:
                        errorData["PRECINCT_ELECTION_DATA_NO_DATA_ERROR"].append({"precinct_idn": precinctID})

                except:
                    errorData["PRECINCT_ELECTION_DATA_NO_DATA_ERROR"].append({"precinct_idn": precinctID})
        except:
            print("uh oh")
            print(count)
            count = 0
            text = input("type anything to continue")


    outputFile = open(outputFileName, mode="w")
    outputFile.write(json.dumps(filteredData))
    outputFile.close()

    outputFile = open("Wisconsin_Election_Data_Errors.json", mode="w")
    outputFile.write(json.dumps(errorData))
    outputFile.close()


    file.close()