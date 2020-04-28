import geopandas
import sys

# This script

if __name__ == '__main__':
    precinctFileName = ""
    # countyFileName = ""
    try:
        precinctFileName : str = sys.argv[1]
        # countyFileName: str = sys.argv[2]
    except:
        print("INVALID USAGE: Please input an state, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/tagUnclosedPrecincts.py \"data/<precinct_data_file>.GeoJSON\" ")
        exit(1)


    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(precinctFileName)
    # countyGDF: geopandas.GeoDataFrame = geopandas.read_file(countyFileName)

    precinctGDF = precinctGDF.drop(columns="VTDI10")
    precinctGDF = precinctGDF.drop(columns="NAME10")
    precinctGDF = precinctGDF.drop(columns="LSAD10")
    precinctGDF = precinctGDF.drop(columns="MTFCC10")
    precinctGDF = precinctGDF.drop(columns="FUNCSTAT10")
    precinctGDF = precinctGDF.drop(columns="ALAND10")
    precinctGDF = precinctGDF.drop(columns="AWATER10")
    precinctGDF = precinctGDF.drop(columns="INTPTLAT10")
    precinctGDF = precinctGDF.drop(columns="INTPTLON10")
    precinctGDF = precinctGDF.drop(columns="OID_")
    precinctGDF = precinctGDF.drop(columns="GEOID10_1")
    precinctGDF = precinctGDF.drop(columns="ELECT_ID")
    precinctGDF = precinctGDF.drop(columns="POP100")
    precinctGDF = precinctGDF.drop(columns="VAP")
    precinctGDF = precinctGDF.drop(columns="GOV_DVOTE_")
    precinctGDF = precinctGDF.drop(columns="GOV_RVOTE_")
    precinctGDF = precinctGDF.drop(columns="COMP_DVOTE")
    precinctGDF = precinctGDF.drop(columns="COMP_RVOTE")
    precinctGDF = precinctGDF.drop(columns="AG_DVOTE_1")
    precinctGDF = precinctGDF.drop(columns="AG_RVOTE_1")
    precinctGDF = precinctGDF.drop(columns="USS_2_DVOT")
    precinctGDF = precinctGDF.drop(columns="USS_2_RVOT")
    precinctGDF = precinctGDF.drop(columns="USS_6_DVOT")
    precinctGDF = precinctGDF.drop(columns="USS_6_RVOT")
    precinctGDF = precinctGDF.drop(columns="SUM_VAP")
    precinctGDF = precinctGDF.drop(columns="VAP_SHARE")
    precinctGDF = precinctGDF.drop(columns="TOTAL")
    precinctGDF = precinctGDF.drop(columns="G_10")
    precinctGDF = precinctGDF.drop(columns="COMP_10")
    precinctGDF = precinctGDF.drop(columns="AG_10")
    precinctGDF = precinctGDF.drop(columns="USS_2_10")
    precinctGDF = precinctGDF.drop(columns="USS_6_10")
    precinctGDF = precinctGDF.drop(columns="AV")
    precinctGDF = precinctGDF.drop(columns="NDV")
    precinctGDF = precinctGDF.drop(columns="NRV")
    precinctGDF = precinctGDF.drop(columns="STATEFP10")



    precinctGDF["STATE_ID"] = "36"
    precinctGDF["CNTY_FIPS"] = None
    precinctGDF["CNTY_NAME"] = None
    precinctGDF["WARDID"] = None
    precinctGDF["WARD_FIPS"] = None
    precinctGDF["COUSUBFP"] = "99999"
    precinctGDF["GEOID"] = None
    precinctGDF["NOTES"] = None

    lastWardID = ""
    index = 0

    for p_index, precinct in precinctGDF.iterrows():
        precinctGDF.loc[p_index, "CNTY_NAME"] = precinctGDF.loc[p_index, "COUNTYFP10"]
        precinctGDF.loc[p_index, "CNTY_FIPS"] = "36"+(precinctGDF.loc[p_index, "COUNTYFP10"].zfill(3))
        if lastWardID != "36"+(precinctGDF.loc[p_index, "COUNTYFP10"].zfill(3)):
            lastWardID = "36"+(precinctGDF.loc[p_index, "COUNTYFP10"].zfill(3))
            index = 0
        precinctGDF.loc[p_index, "WARDID"] = str(index).zfill(4)
        precinctGDF.loc[p_index, "WARD_FIPS"] = "49"+(
            precinctGDF.loc[p_index, "COUNTYFP10"].zfill(3))+"99999"+str(index).zfill(4)
        precinctGDF.loc[p_index, "GEOID"] = "49" + (
            precinctGDF.loc[p_index, "COUNTYFP10"].zfill(3)) + "99999" + str(index).zfill(4)
        index += 1



    precinctGDF.to_file(precinctFileName, driver='GeoJSON')
