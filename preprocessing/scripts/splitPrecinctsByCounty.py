import geopandas
import sys

# This script

if __name__ == '__main__':
    precinctFileName = ""
    outputFileNamePrefix = ""
    try:
        precinctFileName : str = sys.argv[1]
        outputFileNamePrefix = precinctFileName[:precinctFileName.find(".")]
    except:
        print("INVALID USAGE: Please input an state, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/splitPrecinctsByCounty.py \"data/<precinct_data_file>.GeoJSON\" ")
        exit(1)


    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)

    #Note: precincts must have a unique GEOID per precinct for this neighbor test to work.
    # This code will not pass if it does not.

    if "CNTY_NAME" not in precinctGDF.keys():
        print("INVALID FILE: File must have unique CNTY_NAME to split")
        exit(1)


    splitDataFrameDict = {}

    # tempGDF : geopandas.GeoDataFrame = geopandas.GeoDataFrame()

    for index, precinct in precinctGDF.iterrows():

        if precinct.CNTY_NAME in splitDataFrameDict.keys():
            splitDataFrameDict[precinct.CNTY_NAME].append(index)
        else:
            splitDataFrameDict[precinct.CNTY_NAME] = [index]


    for county in splitDataFrameDict:
        # Iterate over all the data and export into individual geojson
        startIndex = splitDataFrameDict[county].__getitem__(0)
        endIndex = splitDataFrameDict[county].__getitem__(splitDataFrameDict[county].__len__()-1)
        precinctGDF[startIndex:endIndex].to_file(outputFileNamePrefix+"_"+county+"_County.GeoJSON", driver='GeoJSON')
