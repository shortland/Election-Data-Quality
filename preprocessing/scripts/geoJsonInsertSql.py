import sys
import json
import os
import mysql.connector

# Utility template script used for creating .sql insert files.
# $ mysql -h 45.55.121.121 -P 3306 -u jerryman -p jerryman
# $> source output.sql


def make_insert(inputFile, notes, dbCursor, db):
    with open(inputFile, 'r') as contentFile:
        content = contentFile.read()
        jsonData = json.loads(content)
        areaData = jsonData["features"]

        for area in areaData:
            geomJson = json.dumps(area["geometry"])
            propsJson = json.dumps(area["properties"])

            # Values for CongressionalDistricts
            # stateFp = area["properties"]["STATEFP"]
            # congdFp = area["properties"]["CD116FP"]
            # nameLsad = area["properties"]["NAMELSAD"]

            geoId = area["properties"]["GEOID"]

            insertQuery = "INSERT IGNORE INTO `features` (`notes`, `geometry`, `properties`, `geo_id`) VALUES (\'%s\', \'%s\', \'%s\', \'%s\');\n" % (
                notes, geomJson, propsJson, geoId)
            dbCursor.execute(insertQuery)
            db.commit()
            print(dbCursor.rowcount, "record inserted into features")

            # Query for CongressionalDistrics
            # insertQuery = "INSERT INTO `new_congressional_districts` (`state_fips`, `congressional_fips`, `geo_id`, `name_lsad`) VALUES (\'%s\', \'%s\', \'%d\', \'%s\');\n" % (
            #     stateFp, congdFp, geoId, nameLsad)

            # Query for Precincts
            neighbors = area["properties"]["NEIGHBORS"]
            if neighbors == None:
                neighbors = ""

            insertQuery = "INSERT IGNORE INTO `precincts` (`precinct_idn`, `county_fips_code`, `neighbors_idn`, `fullname`) VALUES (\'%s\', \'%s\', \'%s\', \'%s\')" % (
                geoId, area["properties"]["CNTY_FIPS"], "[" + neighbors + "]", area["properties"]["WARDID"])  # Utah - PrecinctID, Wisconsin - WARDID

            dbCursor.execute(insertQuery)
            db.commit()
            # print(dbCursor.rowcount, "record inserted into congressional_districts")
            print(dbCursor.rowcount, "record inserted into precincts")


def make_relationship(dbCursor, db):
    # dbCursor.execute(
    #     "SELECT geo_id FROM new_congressional_districts WHERE feature_idn = -1")

    dbCursor.execute(
        "SELECT precinct_idn FROM precincts WHERE feature_idn = -1")
    geoIds = dbCursor.fetchall()

    for geoId in geoIds:
        dbCursor.execute(
            "SELECT idn FROM features WHERE geo_id = '%s'" % (geoId[0]))
        featureIdns = dbCursor.fetchall()

        for featureIdn in featureIdns:
            # Query for CongressionalDistricts
            # dbCursor.execute(
            #     "UPDATE new_congressional_districts SET feature_idn = %d WHERE geo_id = %d" % (featureIdn[0], geoId[0]))

            # Query for Precincts
            dbCursor.execute(
                "UPDATE IGNORE precincts SET feature_idn = '%s' WHERE precinct_idn = '%s'" % (featureIdn[0], geoId[0]))
            db.commit()

            print(dbCursor.rowcount,
                  "record updated - precincts linked to feature")


if __name__ == "__main__":
    db = mysql.connector.connect(
        host="45.55.121.121",
        user="jerryman",
        passwd="JerryManderingIsBad123!",
        database="jerryman"
    )
    dbCursor = db.cursor()

    for file in os.listdir("../data/Wisconsin/Wisconsin_Precinct_Data_Split_By_County/"):
        if file.endswith(".GeoJSON"):
            make_insert(os.path.join(
                "../data/Wisconsin/Wisconsin_Precinct_Data_Split_By_County/", file), file, dbCursor, db)
            make_relationship(dbCursor, db)
