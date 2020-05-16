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
                county["properties"]['geoid'], json.dumps(county["geometry"]), json.dumps(county["properties"]), county["properties"]['geoid'])

            dbCursor.execute(insertQuery)
            db.commit()

            print(dbCursor.rowcount, "record inserted into features")

            # Get the idn of the feature we just inserted
            dbCursor.execute(
                "SELECT idn FROM features WHERE geo_id = '%s'" % (county["properties"]['geoid']))
            featureIdns = dbCursor.fetchall()

            for featureIdn in featureIdns:
                dbCursor.execute(
                    "UPDATE IGNORE counties SET feature_idn = '%s' WHERE fips_code = '%s' LIMIT 1" % (featureIdn[0], county["properties"]['geoid'][-5:]))
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
                county["properties"]['geoid'][-5:], county["properties"]['geoid'][-5:-3], county["properties"]['name'], -1)

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

    insert_county("../data/New York/New_York_County_Boundaries.GeoJSON",
                  "New_York_County_Boundaries", dbCursor, db)

    insert_county_feature(
        "../data/New York/New_York_County_Boundaries.GeoJSON", dbCursor, db)
