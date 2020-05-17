import geopandas
import sys

# This script standardizes the Utah precinct data to our standard form

if __name__ == '__main__':
    precinctFileName = ""
    countyFileName = ""
    try:
        precinctFileName : str = sys.argv[1]
        countyFileName: str = sys.argv[2]
    except:
        print("INVALID USAGE: Please input an state, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/tagUnclosedPrecincts.py \"data/<precinct_data_file>.GeoJSON\" ")
        exit(1)

    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)
    countyGDF: geopandas.GeoDataFrame = geopandas.read_file(countyFileName)

    precinctGDF = precinctGDF.drop(columns="SubPrecinc")
    precinctGDF = precinctGDF.drop(columns="VersionNbr")
    precinctGDF = precinctGDF.drop(columns="EffectiveD")
    precinctGDF = precinctGDF.drop(columns="Comments")
    precinctGDF = precinctGDF.drop(columns="RcvdDate")
    precinctGDF = precinctGDF.drop(columns="Shape_Leng")
    precinctGDF = precinctGDF.drop(columns="Shape_Area")

    precinctGDF["STATE_ID"] = "49"
    precinctGDF["CNTY_FIPS"] = None
    precinctGDF["CNTY_NAME"] = None
    precinctGDF["WARDID"] = None
    precinctGDF["WARD_FIPS"] = None
    precinctGDF["COUSUBFP"] = "99999"
    precinctGDF["GEOID"] = None
    precinctGDF["NOTES"] = None

    precinctGDF = precinctGDF.to_crs(epsg=4326)


    for c_index, county in countyGDF.iterrows():
        nextPrecinctID = 1
        for p_index, precinct in precinctGDF.iterrows():
            geo1 = county.geometry
            geo2 = precinct.geometry
            if geo1.intersects(geo2):
                precinctGDF.loc[p_index, "CNTY_NAME"] = countyGDF.loc[c_index, "NAME"]
                countynbr = (countyGDF.loc[c_index, "COUNTYNBR"].zfill(3))
                precinctGDF.loc[p_index, "CNTY_FIPS"] = "49"+countynbr
                precinctGDF.loc[p_index, "WARDID"] = str(nextPrecinctID).zfill(4)
                precinctGDF.loc[p_index, "WARD_FIPS"] = "49"+countynbr+"99999"+str(nextPrecinctID).zfill(4)
                precinctGDF.loc[p_index, "GEOID"] = "49" + countynbr + "99999" + str(nextPrecinctID).zfill(4)
                nextPrecinctID += 1

    precinctGDF.to_file(precinctFileName, driver='GeoJSON')
