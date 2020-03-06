import geopandas
import sys

#This code takes an file with a multipolygon and returns the versiob of that file without multipolygons

if __name__ == '__main__':
    #Arg input
    inputFile = ""
    outputFile = ""
    try:
        inputFile = sys.argv[1]
        outputFile = sys.argv[2]
    except:
        print("INVALID USAGE: Please input an input file and output file")
        print("Usage:")
        print("$ Python3 scripts/eliminateMultiPolygons.py \"data/<input_data_file>.GeoJSON\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)

    invalid = False

    # Arg Error Detection
    if not ((".shp" in inputFile) or (".GeoJSON" in inputFile) or (".geojson" in inputFile) or (".json" in inputFile)):
        print("INVALID INPUT: Please provide an input file with .shp or .GeoJSON format")
        invalid = True
    if not ((".shp" in outputFile) or (".GeoJSON" in outputFile) or (".geojson" in outputFile) or (".json" in outputFile)):
        print("INVALID INPUT: Please provide an output file with .shp or .GeoJSON format")
        invalid = True

    if invalid:
        print("Usage:")
        print("$ Python3 scripts/eliminateMultiPolygons.py \"data/<input_data_file>.GeoJSON\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)

    # Turn file into live GeoDataFrame
    try:
        inputGDF: geopandas.GeoDataFrame = geopandas.GeoDataFrame.from_file(inputFile)
    except Exception as err:
        print("ERROR: Unable to open file")
        print("ERROR: ", format(err))

    # Explode Multipolygons into multiple single polygons
    tempGDF = inputGDF.explode()

    # Export exploded data
    tempGDF.to_file(outputFile, driver="GeoJSON")





