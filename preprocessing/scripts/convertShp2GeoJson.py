import geopandas
import sys

# This script will convert any shapefile to geojson. Note: although this function is called with the .shp file
# it must be in the same directory as the counter parts to the shapefile. That is one of the benefits of the
# geojson format.

if __name__ == '__main__':

    # Arg input
    inputFile = ""
    outputFile = ""
    try:
        inputFile = sys.argv[1]
        outputFile = sys.argv[2]
    except:
        print("INVALID USAGE: Please input an input file and output file")
        print("Usage:")
        print(
            "$ Python3 scripts/eliminateMultiPolygons.py \"data/<input_data_file>.GeoJSON\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)

    invalid = False

    # Arg Error Detection
    if not ((".shp" in inputFile) or (".GeoJSON" in inputFile) or (".geojson" in inputFile) or (".json" in inputFile)):
        print("INVALID INPUT: Please provide an input file with .shp or .GeoJSON format")
        invalid = True
    if not ((".shp" in outputFile) or (".GeoJSON" in outputFile) or (".geojson" in outputFile) or (
            ".json" in outputFile)):
        print("INVALID INPUT: Please provide an output file with .shp or .GeoJSON format")
        invalid = True

    if invalid:
        print("Usage:")
        print(
            "$ Python3 scripts/convertShp2GeoJson.py \"data/<input_data_file>.shp\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)


    censusBlocks = geopandas.read_file(inputFile)
    censusBlocks.to_file(outputFile, driver='GeoJSON')
