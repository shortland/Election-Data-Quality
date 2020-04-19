import geopandas
import sys
from shapely.coords import CoordinateSequence

# This script takes a precinct geojson file and determines the neighbors for each precinct.

if __name__ == '__main__':
    precinctFileName = ""
    outputFileName = ""
    temp  = ""
    try:
        precinctFileName : str = sys.argv[1]
        outputFileName = precinctFileName[:precinctFileName.find(".")]+"_Unclosed_Tagged.GeoJSON"
        temp = precinctFileName[:precinctFileName.find(".")]+"_Temp.GeoJSON"
    except:
        print("INVALID USAGE: Please input an state, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/tagUnclosedPrecincts.py \"data/<precinct_data_file>.GeoJSON\" ")
        exit(1)


    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)

    #Note: This code can only run once and will terminate if tried again.

    if "IS_CLOSED" in precinctGDF.keys():
        print("INVALID FILE: File already has a IS_CLOSED attribute")
        exit(1)

    precinctGDF["IS_CLOSED"] = False

    nonClosed = []

    for index, precinct in precinctGDF.iterrows():

        if(precinct['geometry'].boundary.is_closed):
            precinctGDF.at[index, "IS_CLOSED"] = True
        else:
            nonClosed.append(index)

    # nonClosedPrecincts = precinctGDF[nonClosed[0]:nonClosed[1]]
    # nonClosed.remove(nonClosed[0])
    # for index in nonClosed:
    #     nonClosedPrecincts.append(precinctGDF[index:(index+1)])
    # nonClosedPrecincts.to_file(temp, driver='GeoJSON')

    precinctGDF.to_file(temp, driver='GeoJSON')
    precinctGDF.to_file(outputFileName, driver='GeoJSON')
