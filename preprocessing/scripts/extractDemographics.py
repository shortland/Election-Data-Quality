import geopandas
import sys
import json

# This script takes a precinct geojson file with neighbors and determines which are fully enclosed.

if __name__ == '__main__':
    precinctFileName = ""
    demographicsFileName = ""
    outputFileName = ""
    try:
        precinctFileName : str = sys.argv[1]
        demographicsFileName : str = sys.argv[2]
        outputFileName: str = sys.argv[3]
    except:
        print("INVALID USAGE: Please input an demographic data file, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/extractDemographics.py \"data/<precinct_data_file>.GeoJSON\" \"data/<demographics_data_file>.GeoJSON\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)


    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)
    demographicGDF: geopandas.GeoDataFrame = geopandas.read_file(demographicsFileName)

    # if "NEIGHBORS" not in precinctGDF.keys():
    #     print("INVALID FILE: File does not have NEIGHBOR attribute")
    #     exit(1)

    # Utah = 49, New York = 36, Wisconsin = 55
    filteredData = {"STATE_ID": "36", "PRECINCT_DEMOGRAPHICS": []}


    # Total pop = B02001e1
    # white only = B02001e2
    # black only = B02001e3
    # Native american only = B02001e4
    # asian only = B02001e5
    # Native Hawaiian only = B02001e6
    # other = B02001e7

    # Hispanic = B03003e1

    usedindecies = []

    for p_index in range(0, precinctGDF.__len__()-1):
        precinctID = precinctGDF.loc[p_index, "GEOID"]
        white = 0
        black = 0
        native_American = 0
        asian = 0
        native_Hawaiian = 0
        other = 0

        geo2 = precinctGDF.loc[p_index, "geometry"]

        for d_index in range(0, demographicGDF.__len__()-1):
            if d_index in usedindecies:
                continue
            geo1 = demographicGDF.loc[d_index, "geometry"]

            if geo1.intersects(geo2):
                usedindecies.append(d_index)
                white += int(demographicGDF.loc[d_index, "B02001e2"])
                black += int(demographicGDF.loc[d_index, "B02001e3"])
                native_American += int(demographicGDF.loc[d_index, "B02001e4"])
                asian += int(demographicGDF.loc[d_index, "B02001e5"])
                native_Hawaiian += int(demographicGDF.loc[d_index, "B02001e6"])
                other += int(demographicGDF.loc[d_index, "B02001e7"])

        filteredData["PRECINCT_DEMOGRAPHICS"].append({"PRECINCT_ID":str(precinctID),
                                                      "WHITE":white,"BLACK":black,
                                                      "NATIVE_AMERICAN":native_American,
                                                      "ASIAN":asian,
                                                      "NATIVE_HAWAIIAN":native_Hawaiian,
                                                      "OTHER":other})

    outputFile = open(outputFileName, mode="w")
    outputFile.write(json.dumps(filteredData))
    outputFile.close()