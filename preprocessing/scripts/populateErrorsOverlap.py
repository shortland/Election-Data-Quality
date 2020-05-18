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
with open("../data/New York/New_York_Precinct_Data_Overlap_Error.GeoJSON") as f:
    data = json.load(f)

    error_data = data["INTERSECTING"]

    for i in range(len(error_data)):

        insertQuery = "INSERT IGNORE INTO `errors` (`precinct_idn`, `type`, `text`) VALUES (\'%s\', \'%s\', \'%s\');\n" % (
            error_data[i], "INTERSECTING", "Error from preprocessing file: NY_Precinct_Data_Overlap_Error.GeoJSON")

        dbCursor.execute(insertQuery)
        db.commit()

        print(dbCursor.rowcount, "record inserted into errors table")
