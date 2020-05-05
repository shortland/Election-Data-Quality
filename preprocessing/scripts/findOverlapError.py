import geopandas
import sys
import json
from shapely.geometry import MultiPolygon

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
        print("$ Python3 scripts/findOverlapError.py \"data/<precinct_data_file>.GeoJSON\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)


    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)

    if "NEIGHBORS" not in precinctGDF.keys():
        print("INVALID FILE: File does not have NEIGHBOR attribute")
        exit(1)

    overlapErrorData = []
    selfIntersectErrorData = []

    for p_index, precinct in precinctGDF.iterrows():
        neighborList = precinctGDF[precinctGDF.geometry.touches(precinct['geometry'])]
        for n_index, neighborPrecinct in neighborList.iterrows():
            try:
                overlap = neighborPrecinct.geometry.intersection(precinct.geometry)
                if overlap.area != 0.0:
                    print(overlap.area)
                    overlapErrorData.append(precinct.GEOID)
            except:
                if neighborPrecinct.GEOID not in selfIntersectErrorData:
                    selfIntersectErrorData.append(neighborPrecinct.GEOID)
                if precinct.GEOID not in selfIntersectErrorData:
                    selfIntersectErrorData.append(precinct.GEOID)

    # precinctGDF[precinctGDF.NEIGHBORS.split(',').__len__() == 1].to_file("temp.GeoJSON", driver='GeoJSON')

    outputFile = open(outputFileName, mode="w")
    outputFile.write(json.dumps({"ENCLOSED" : overlapErrorData, "INTERSECTING" : selfIntersectErrorData}))
    outputFile.close()