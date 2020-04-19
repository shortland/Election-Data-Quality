import geopandas
import sys

# This script takes precinct data and state shape outline and determines Ghost Precincts. After running this script
# you will have single polygons all with a new State_ID and the notes field saying 'Ghosts'. This script will not
# modify any existing data.

if __name__ == '__main__':
    stateBoundaryFileName = ""
    statePrecinctDataFileName = ""
    outputFileName = ""
    try:
        stateBoundaryFileName = sys.argv[1]
        statePrecinctDataFileName = sys.argv[2]
        outputFileName = sys.argv[3]
    except:
        print("INVALID USAGE: Please input an state, precinct, and output file name")
        print("Usage:")
        print("$ Python3 scripts/createGhosts.py \"data/<state_data_file>.GeoJSON\" \"data/<precinct_data_file>.GeoJSON\" \"data/<output_data_file>.GeoJSON\"")
        exit(1)

    # Note: These lines of code add a StateID to each precinct inside the file. If each precinct already has this data
    # then comment out these lines and change the dissolve code to dissolve by that ID. Please note: 55 is the ID of
    # Wisconsin. Every state has their own number ID you can find it as the first two numbers of their GEOID. This code
    # has been modified to find that ID Number.
    precinctTXT = open(statePrecinctDataFileName, mode="rt")
    tempData = precinctTXT.read()
    stateID = ""
    if tempData.find("STATE_ID") == -1:
        id = tempData[tempData.find("\"GEOID\": \"")+10:tempData.find("\"GEOID\": \"")+12]
        stateID = id
        tempData = tempData.replace(", \"GEOID\":", ", \"STATE_ID\": "+str(id)+", \"GEOID\":")
    precinctTXT.close()

    precinctTXT = open(statePrecinctDataFileName, mode="wt")
    precinctTXT.write(tempData)
    precinctTXT.close()



    stateGDF = geopandas.read_file(stateBoundaryFileName)
    precinctGDF: geopandas.GeoDataFrame = geopandas.read_file(statePrecinctDataFileName)

    precinctGDF['geometry'] = precinctGDF['geometry'].buffer(0.0001)

    precinctGDF = precinctGDF.dissolve(by='STATE_ID')

    stateGDF.explode()
    precinctGDF.explode()

    precinctGDF.crs
    stateGDF.crs
    precinctGDF.to_crs(crs=stateGDF.crs, inplace=True)


    ghosts = geopandas.overlay(precinctGDF, stateGDF, how="symmetric_difference", keep_geom_type=False)

    # Explode Multipolygons into multiple single polygons
    ghosts = ghosts.explode()

    ghosts['GEOID'] = None
    for index, ghost in ghosts.iterrows():
        CNTY_FIPS = '999'
        COUSUBFP = '99999'
        precinctID = str(index).zfill(4)
        ghost.__setattr__("GEOID", str(stateID)+CNTY_FIPS+COUSUBFP+precinctID)
        ghost.__setattr__("CNTY_FIPS", CNTY_FIPS)
        ghost.__setattr__("CNTY_NAME", None)
        ghost.__setattr__("COUSUBFP", COUSUBFP)
        ghost.__setattr__("MCD_FIPS", str(stateID)+CNTY_FIPS+COUSUBFP)
        ghost.__setattr__("MCD_NAME", None)
        ghost.__setattr__("WARD_FIPS", str(stateID)+CNTY_FIPS+COUSUBFP+precinctID)
        ghost.__setattr__("WARDID", precinctID)


    ghosts.__setattr__(attr='NOTES', val='Ghosts')

    ghosts.append(precinctGDF)

    ghosts.to_file(outputFileName, driver='GeoJSON')





