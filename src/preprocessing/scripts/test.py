import shapefile

# read the shapefile
reader = shapefile.Reader("/Users/samuelhoffmann/Desktop/tl_2019_55_tabblock10/tl_2019_55_tabblock10.shp")
fields = reader.fields[1:]
field_names = [field[0] for field in fields]
reader.encoding = "latin1"
buffer = []
for sr in reader.shapeRecords():
    atr = dict(zip(field_names, sr.record))
    geom = sr.shape.__geo_interface__
    buffer.append(dict(type="Feature", geometry=geom, properties=atr))

    # write the GeoJSON file
from json import dumps

geojson = open("/Users/samuelhoffmann/Desktop/tl_2019_55_tabblock10/pyshp-demo.json", "w")
geojson.write(dumps({"type": "FeatureCollection", "features": buffer}, indent=2) + "\n")
geojson.close()