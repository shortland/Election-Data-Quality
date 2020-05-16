import json
import mysql.connector

# David's code

db = mysql.connector.connect(
    host="45.55.121.121",
    user="jerryman",
    passwd="JerryManderingIsBad123!",
    database="jerryman"
)

dbCursor = db.cursor()

# need to change the directory
with open("../data/New York/New_York_Demographic_Data.json") as f:
    data = json.load(f)

    demographic_data = data["PRECINCT_DEMOGRAPHICS"]
    precinctId = ""
    WHITE = 0
    BLACK = 0
    NATIVE_AMERICAN = 0
    ASIAN = 0
    NATIVE_HAWAIIAN = 0
    OTHER = 0

    # for i in range(len(demogrpahic_data)):
    for i in range(len(demographic_data)):
        currRow = demographic_data[i]
        precinctId = currRow["PRECINCT_ID"]
        WHITE = currRow["WHITE"]
        BLACK = currRow["BLACK"]
        NATIVE_AMERICAN = currRow["NATIVE_AMERICAN"]
        ASIAN = currRow["ASIAN"]
        NATIVE_HAWAIIAN = currRow["NATIVE_HAWAIIAN"]
        OTHER = currRow["OTHER"]

        print(precinctId, WHITE, BLACK, NATIVE_AMERICAN,
              ASIAN, NATIVE_HAWAIIAN, OTHER)

        insertQuery = "INSERT IGNORE INTO `demographic_data` (`precinct_idn`, `asian`, `black`, `native_american`, `native_hawaiian`, `other`, `white`) VALUES (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\');\n" % (
            precinctId, ASIAN, BLACK, NATIVE_AMERICAN, NATIVE_HAWAIIAN, OTHER, WHITE)

        dbCursor.execute(insertQuery)
        db.commit()

        print(dbCursor.rowcount, "record inserted into demographic_data")
