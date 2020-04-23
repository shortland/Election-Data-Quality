import sys
from shapely.geometry import Polygon
from shapely.geometry import LineString
from shapely.geometry import MultiLineString
import re

# This script will verify that user created polygon are closed

if __name__ == '__main__':

    # Arg input
    polygonData = ""
    # try:
    #     polygonData = sys.argv[1]
    # except:
    #     print("INVALID USAGE: ")
    #     print("Usage:")
    #     print(
    #         "$ Python3 ")
    #     exit(1)

    tempData1 = "[[[-73.68075370788573,40.74134755281577],[-73.68184804916382,40.7397461504767],[-73.67953062057495,40.738599945434885],[-73.67752432823181,40.73975427951992],[-73.6777925491333,40.741477614257114],[-73.68009924888611,40.74181902433063],[-73.68075370788573,40.74134755281577]]]"

    polygonData = tempData1

    points = re.findall(r'[-+]?\d*\.\d+|\d+', polygonData)

    polygonArray = []

    p1 = 0.0
    for x in range(0, points.__len__()):
        if (x % 2 == 0):
            p1 = float(points[x])
        else:
            polygonArray.append((p1, float(points[x])))

    try:
        polygon1 = Polygon(polygonArray)
        # if type(polygon1.boundary) == LineString:
        #     x1 = polygon1.boundary.coords.xy[0][0]
        #     y1 = polygon1.boundary.coords.xy[1][0]
        #     x2 = polygon1.boundary.coords.xy[0][polygon1.boundary.coords.xy[0].__len__()-1]
        #     y2 = polygon1.boundary.coords.xy[1][polygon1.boundary.coords.xy[1].__len__()-1]
        #     print()
        returnPolygonData = "["

        if type(polygon1.boundary) == MultiLineString:
            for mlineIndex in range(0, polygon1.boundary.__len__()):
                mline = polygon1.boundary[mlineIndex]
                returnPolygonData += "["
                for xIndex in range(0, mline.coords.xy[0].__len__()):
                    returnPolygonData += "[" + str(mline.coords.xy[0][xIndex]) + "," + str(
                        mline.coords.xy[1][xIndex]) + "]"
                    returnPolygonData += "" if (xIndex == (mline.coords.xy[0].__len__() - 1)) else ","
                returnPolygonData += "]"
                returnPolygonData += "" if (mlineIndex == (polygon1.boundary.__len__() - 1)) else ","
        else:
            returnPolygonData += "["
            for xIndex in range(0, polygon1.boundary.coords.xy[0].__len__()):
                returnPolygonData += "[" + str(polygon1.boundary.coords.xy[0][xIndex]) + "," + str(
                    polygon1.boundary.coords.xy[1][xIndex]) + "]"
                returnPolygonData += "" if (xIndex == (polygon1.boundary.coords.xy[0].__len__() - 1)) else ","
            returnPolygonData += "]"

        returnPolygonData += "]"

        print(returnPolygonData == polygonData)
    except:
        print(False)