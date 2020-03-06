import geopandas
import sys




if __name__ == '__main__':
    inputName = sys.argv[1]
    outputName = sys.argv[2]
    censusBlocks = geopandas.read_file(inputName)
    censusBlocks.to_file(outputName, driver='GeoJSON')

    # censusBlocks = geopandas.read_file("tl_2019_55_tabblock10.shp")
    # censusBlocks.to_file("censusBlocks.geojson", driver='GeoJSON')