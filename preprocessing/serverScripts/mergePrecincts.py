import sys
from shapely.geometry import Polygon
from shapely.ops import unary_union
from shapely.geometry import MultiLineString
from shapely.geometry import LineString
import re

# This script will merge two precincts as specified with server data input

if __name__ == '__main__':

    # Arg input
    polygonData1 = ""
    polygonData2 = ""
    try:
        polygonData1 = sys.argv[1]
        polygonData2 = sys.argv[2]
    except:
        print("INVALID USAGE: Please input an input file and output file")
        print("Usage:")
        print(
            "$ Python3 ")
        exit(1)

    # tempData1 = "[[[-73.68075370788573,40.74134755281577],[-73.68184804916382,40.7397461504767],[-73.67953062057495,40.738599945434885],[-73.67752432823181,40.73975427951992],[-73.6777925491333,40.741477614257114],[-73.68009924888611,40.74181902433063],[-73.68075370788573,40.74134755281577]]]"
    #
    # tempData2 = "[[[-73.67649435997009,40.740510276198904],[-73.67872595787048,40.74101426921151],[-73.68005633354187,40.74026640724124],[-73.68023872375488,40.73796586607593],[-73.6783504486084,40.73786018559518],[-73.67641925811768,40.73835606947362],[-73.67649435997009,40.740510276198904]]]"
    #
    # polygonData1 = tempData1
    # polygonData2 = tempData2

    points = re.findall(r'[-+]?\d*\.\d+|\d+', polygonData1)

    polygon1Array = []

    p1 = 0.0
    for x in range(0, points.__len__()):
        if (x % 2 == 0):
            p1 = float(points[x])
        else:
            polygon1Array.append((p1, float(points[x])))

    polygon1 = Polygon(polygon1Array)



    points = re.findall(r'[-+]?\d*\.\d+|\d+', polygonData2)

    polygon2Array = []

    p1 = 0.0
    for x in range(0, points.__len__()):
        if (x % 2 == 0):
            p1 = float(points[x])
        else:
            polygon2Array.append((p1, float(points[x])))

    polygon2 = Polygon(polygon2Array)


    mergedPolygon = unary_union([polygon1, polygon2])

    returnPolygonData = "["

    if type(mergedPolygon.boundary) == MultiLineString:
        for mlineIndex in range(0, mergedPolygon.boundary.__len__()):
            mline = mergedPolygon.boundary[mlineIndex]
            returnPolygonData += "["
            for xIndex in range(0, mline.coords.xy[0].__len__()):
                returnPolygonData += "[" + str(mline.coords.xy[0][xIndex]) + ", " + str(
                    mline.coords.xy[1][xIndex]) + "]"
                returnPolygonData += "" if (xIndex == (mline.coords.xy[0].__len__() - 1)) else ","
            returnPolygonData += "]"
            returnPolygonData += "" if (mlineIndex == (mergedPolygon.boundary.__len__() - 1)) else ","
    else:
        returnPolygonData += "["
        for xIndex in range(0, mergedPolygon.boundary.coords.xy[0].__len__()):
            returnPolygonData += "[" + str(mergedPolygon.boundary.coords.xy[0][xIndex]) + ", " + str(
                mergedPolygon.boundary.coords.xy[1][xIndex]) + "]"
            returnPolygonData += "" if (xIndex == (mergedPolygon.boundary.coords.xy[0].__len__() - 1)) else ","
        returnPolygonData += "]"

    returnPolygonData += "]"

    print(returnPolygonData)