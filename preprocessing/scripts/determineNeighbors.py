import geopandas
import sys

# This script takes a precinct geojson file and determines the neighbors for each precinct.

if __name__ == '__main__':
    precinctFileName = ""
    outputFileName = ""
    try:
        precinctFileName : str = sys.argv[1]
        outputFileName = precinctFileName[:precinctFileName.find(".")]+"_With_Neighbors.GeoJSON"
    except:
        print("INVALID USAGE: Please input an state, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/determineNeighbors.py \"data/<precinct_data_file>.GeoJSON\" ")
        exit(1)


    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)

    #Note: precincts must have a unique GEOID per precinct for this neighbor test to work.
    # This code will not pass if it does not.

    if "GEOID" not in precinctGDF.keys():
        print("INVALID FILE: File must have unique GEOID's")
        exit(1)

    if "NEIGHBORS" in precinctGDF.keys():
        print("INVALID FILE: File already has a NEIGHBOR attribute")
        exit(1)

    precinctGDF["NEIGHBORS"] = None

    noNeighbors = [] # useful for debugging purposes.
                     # In usage any precinct with no neighbors will have None for it's neighbor feild.
    for index, precinct in precinctGDF.iterrows():
        neighborList = precinctGDF[precinctGDF.geometry.touches(precinct['geometry'])].GEOID.tolist()
        if precinct.GEOID in neighborList:
            neighborList.remove(precinct.GEOID)
        try:
            precinctGDF.at[index, "NEIGHBORS"] = ", ".join(neighborList)
        except:
            noNeighbors.append(precinct.GEOID)
            precinctGDF.at[index, "NEIGHBORS"] = None

    precinctGDF.to_file(outputFileName, driver='GeoJSON')






