import geopandas
from shapely.geometry.polygon import Polygon
import sys
import shapely



if __name__ == '__main__':
    stateBoundaryFileName = sys.argv[1]
    statePrecinctDataFileName = sys.argv[2]
    outputFileName = sys.argv[3]

    stateGDF = geopandas.read_file(stateBoundaryFileName)
    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(statePrecinctDataFileName)


    # print(precinctGDF.keys())

    precinctGDF = precinctGDF.dissolve(by='CNTY_NAME')

    ghostGDF = geopandas.overlay(stateGDF, precinctGDF, how="symmetric_difference")

    # ghostGDF.explode()


    precinctGDF.to_file(outputFileName, driver='GeoJSON')



