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
with open("../data/New York/New_York_Election_Data_Errors.json") as f:
    data = json.load(f)

    error_data = data["PRECINCT_ELECTION_DATA_NO_DATA_ERROR"]

    # for i in range(len(demogrpahic_data)):
    for i in range(len(error_data)):
        print(error_data[i]["precinct_idn"])

        insertQuery = "INSERT IGNORE INTO `errors` (`precinct_idn`, `type`, `text`) VALUES (\'%s\', \'%s\', \'%s\');\n" % (
            error_data[i]["precinct_idn"], "NO_VOTERS", "Error from preprocessing file: NY_Election_Data_Errors.json")

        dbCursor.execute(insertQuery)
        db.commit()

        print(dbCursor.rowcount, "record inserted into errors table")
