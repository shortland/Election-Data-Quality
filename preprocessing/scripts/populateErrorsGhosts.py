import json
import mysql.connector

db = mysql.connector.connect(
    host="45.55.121.121",
    user="jerryman",
    passwd="JerryManderingIsBad123!",
    database="jerryman"
)

dbCursor = db.cursor()

# need to change the directory
with open("../data/New York/New_York_Precinct_Data_Ghosts.GeoJSON") as f:
    data = json.load(f)

    error_data = data["features"]

    # for i in range(len(demogrpahic_data)):
    for i in range(len(error_data)):
        print(error_data[i]["properties"]["GEOID"])

        insertQuery = "INSERT IGNORE INTO `errors` (`precinct_idn`, `type`, `text`) VALUES (\'%s\', \'%s\', \'%s\');\n" % (
            error_data[i]["properties"]["GEOID"], "GHOST", "Error from preprocessing file: NY_Precinct_Data_Ghosts.GeoJSON")

        dbCursor.execute(insertQuery)
        db.commit()

        print(dbCursor.rowcount, "record inserted into errors table")
