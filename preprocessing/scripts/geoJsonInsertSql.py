import sys
import json
import os

def make_insert(inputFile, notes, f):
    i = 0

    with open(inputFile) as fin:
        for line in fin:
            i = i + 1

            if i < 5:
                continue

            try:
                jsonData = json.loads(line[:-2])
                propsJson = json.dumps(jsonData['properties'])
                geomJson = json.dumps(jsonData['geometry'])

                insertQuery = "INSERT INTO `features` (`notes`, `geometry`, `properties`) VALUES (\'%s\', \'%s\', \'%s\');\n" % (notes, geomJson, propsJson)

                f.write(insertQuery)
            except:
                pass


if __name__ == '__main__':
    f = open("output.sql", "w")

    for file in os.listdir("../data/Wisconsin/Wisconsin_Precinct_Data_Split_By_County/"):
        if file.endswith(".GeoJSON"):
            make_insert(os.path.join("../data/Wisconsin/Wisconsin_Precinct_Data_Split_By_County/", file), file, f)

    f.close()
