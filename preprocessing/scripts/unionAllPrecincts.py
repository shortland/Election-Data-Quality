import geopandas
import sys
import pandas

#This is a work in progress code. it currently doesnt generate ghosts like it should.
#

if __name__ == '__main__':
    fileName = sys.argv[1]

    data: geopandas.GeoDataFrame = geopandas.read_file(fileName)

    # data.append()

    data.to_file(fileName, driver='GeoJSON')



