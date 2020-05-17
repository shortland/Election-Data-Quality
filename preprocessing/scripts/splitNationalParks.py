import geopandas
import sys

# This script takes a precinct geojson file with neighbors and determines which are fully enclosed.

if __name__ == '__main__':

    nationalParkFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Original Data/National_Park_Data.GeoJSON"
    wisconsinOutputFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Wisconsin/Wisconsin_National_Parks.GeoJSON"
    utahOutputFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/Utah/Utah_National_Parks.GeoJSON"
    newYorkOutputFileName = "/Users/samuelhoffmann/PycharmProjects/CSE416/fileConversionTesting/data/New York/New_York_National_Parks.GeoJSON"


    nationalParkGDF: geopandas.GeoDataFrame = geopandas.read_file(nationalParkFileName)

    nationalParkGDF = nationalParkGDF.to_crs(epsg=4269)

    nationalParkGDF[nationalParkGDF.STATE == "NY"].to_file(newYorkOutputFileName, driver='GeoJSON')
    nationalParkGDF[nationalParkGDF.STATE == "WI"].to_file(wisconsinOutputFileName, driver='GeoJSON')
    nationalParkGDF[nationalParkGDF.STATE == "UT"].to_file(utahOutputFileName, driver='GeoJSON')

