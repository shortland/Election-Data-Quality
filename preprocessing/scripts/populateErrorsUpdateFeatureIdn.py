import sys
import json
import os
import mysql.connector

db = mysql.connector.connect(
    host="45.55.121.121",
    user="jerryman",
    passwd="JerryManderingIsBad123!",
    database="jerryman"
)

dbCursor = db.cursor()

dbCursor.execute("SELECT precinct_idn FROM errors WHERE feature_idn IS NULL")
precinctIdns = dbCursor.fetchall()

for precinctIdn in precinctIdns:
    dbCursor.execute(
        "SELECT feature_idn FROM precincts WHERE precinct_idn = '%s'" % precinctIdn[0])
    featureIdns = dbCursor.fetchall()

    for featureIdn in featureIdns:
        dbCursor.execute(
            "UPDATE IGNORE errors SET feature_idn = '%s' WHERE precinct_idn = '%s'" % (featureIdn[0], precinctIdn[0]))
        db.commit()

        print(dbCursor.rowcount, "record updated")
