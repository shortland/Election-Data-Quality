import geopandas
import sys
import json

# This script takes a precinct geojson file with neighbors and determines which are fully enclosed.

if __name__ == '__main__':
    precinctFileName = ""
    outputFileName = ""
    try:
        precinctFileName : str = sys.argv[1]
        outputFileName : str = sys.argv[2]
    except:
        print("INVALID USAGE: Please input an state, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/findEnclosedPrecincts.py \"data/<precinct_data_file>.GeoJSON\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)


    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)

    if "NEIGHBORS" not in precinctGDF.keys():
        print("INVALID FILE: File does not have NEIGHBOR attribute")
        exit(1)

    outData = []

    for p_index, precinct in precinctGDF.iterrows():
        if precinct.NEIGHBORS != None:
            if precinct.NEIGHBORS.split(',').__len__() == 1:
                outData.append(precinct.GEOID)


    # precinctGDF[precinctGDF.NEIGHBORS.split(',').__len__() == 1].to_file("temp.GeoJSON", driver='GeoJSON')

    outputFile = open(outputFileName, mode="w")
    outputFile.write(json.dumps({"ENCLOSED" : outData}))
    outputFile.close()