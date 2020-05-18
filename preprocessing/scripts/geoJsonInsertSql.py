import sys
import json
import os
import mysql.connector

# Mainly for precincts

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

            geoId = area["properties"]["GEOID"]

            insertQuery = "INSERT IGNORE INTO `features` (`notes`, `geometry`, `properties`, `geo_id`) VALUES (\'%s\', \'%s\', \'%s\', \'%s\');\n" % (
                notes, geomJson, propsJson, geoId)

            dbCursor.execute(insertQuery)
            db.commit()

            print(dbCursor.rowcount, "record inserted into features")

            neighbors = area["properties"]["NEIGHBORS"]
            if neighbors == None:
                neighbors = ""

            insertQuery = "INSERT IGNORE INTO `precincts` (`precinct_idn`, `county_fips_code`, `neighbors_idn`, `fullname`) VALUES (\'%s\', \'%s\', \'%s\', \'%s\')" % (
                geoId, area["properties"]["CNTY_FIPS"], "[" + neighbors + "]", area["properties"]["CNTY_NAME"])  # Utah - PrecinctID, Wisconsin - WARDID

            dbCursor.execute(insertQuery)
            db.commit()

            print(dbCursor.rowcount, "record inserted into precincts")


def make_relationship(dbCursor, db):
    dbCursor.execute(
        "SELECT precinct_idn FROM precincts WHERE feature_idn = -1")
    geoIds = dbCursor.fetchall()

    for geoId in geoIds:
        dbCursor.execute(
            "SELECT idn FROM features WHERE geo_id = '%s'" % (geoId[0]))
        featureIdns = dbCursor.fetchall()

        for featureIdn in featureIdns:
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

    for file in os.listdir("../data/Utah/Utah_Precinct_Data_Split_By_County/"):
        if file.endswith(".GeoJSON"):
            print("doing: %s" % file)
            make_insert(os.path.join(
                "../data/Utah/Utah_Precinct_Data_Split_By_County/", file), file, dbCursor, db)
            make_relationship(dbCursor, db)
