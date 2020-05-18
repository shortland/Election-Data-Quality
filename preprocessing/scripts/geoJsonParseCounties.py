import sys
import json
import os
import mysql.connector


def insert_county_feature(inputFile, dbCursor, db):
    with open(inputFile, 'r') as contentFile:
        content = contentFile.read()
        jsonData = json.loads(content)
        allCounties = jsonData["features"]

        for county in allCounties:
            insertQuery = "INSERT IGNORE INTO `features` (`notes`, `geometry`, `properties`, `geo_id`) VALUES (\'%s\', \'%s\', \'%s\', \'%s\');\n" % (
                county["properties"]['OBJECTID'], json.dumps(county["geometry"]), json.dumps(county["properties"]), "55" + (county["properties"]["COUNTY_FIPS_CODE"]).zfill(3) + str(county["properties"]['OBJECTID']))

            dbCursor.execute(insertQuery)
            db.commit()

            print(dbCursor.rowcount, "record inserted into features")

            # Get the idn of the feature we just inserted
            dbCursor.execute(
                "SELECT idn FROM features WHERE geo_id = '%s'" % ("55" + (county["properties"]["COUNTY_FIPS_CODE"]).zfill(3) + str(county["properties"]['OBJECTID'])))
            featureIdns = dbCursor.fetchall()

            for featureIdn in featureIdns:
                dbCursor.execute(
                    "UPDATE IGNORE counties SET feature_idn = '%s' WHERE fips_code = '%s' LIMIT 1" % (featureIdn[0], "55" + (county["properties"]["COUNTY_FIPS_CODE"]).zfill(3)))
                db.commit()

                print(dbCursor.rowcount, "record updated")
    return


def insert_county(inputFile, notes, dbCursor, db):
    with open(inputFile, 'r') as contentFile:
        content = contentFile.read()
        jsonData = json.loads(content)
        allCounties = jsonData["features"]

        for county in allCounties:
            insertQuery = "INSERT IGNORE INTO `counties` (`fips_code`, `state_idn`, `county_name`, `feature_idn`) VALUES (\'%s\', \'%s\', \'%s\', \'%s\');\n" % (
                "55" + (county["properties"]["COUNTY_FIPS_CODE"]).zfill(3), 55, county["properties"]['COUNTY_NAME'], -1)

            dbCursor.execute(insertQuery)
            db.commit()

            print(dbCursor.rowcount, "record inserted into counties")
    return


if __name__ == "__main__":
    db = mysql.connector.connect(
        host="45.55.121.121",
        user="jerryman",
        passwd="JerryManderingIsBad123!",
        database="jerryman"
    )

    dbCursor = db.cursor()

    # insert_county("../data/Wisconsin/Wisconsin_County_Boundaries.GeoJSON",
    #               "Wisconsin_County_Data", dbCursor, db)

    insert_county_feature(
        "../data/Wisconsin/Wisconsin_County_Boundaries.GeoJSON", dbCursor, db)
