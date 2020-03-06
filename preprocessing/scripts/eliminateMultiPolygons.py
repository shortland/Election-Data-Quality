import geopandas
from shapely.geometry.polygon import Polygon
from shapely.geometry.multipolygon import MultiPolygon

# censusBlocks = geopandas.GeoDataFrame.read_file("censusBlocks.geojson")
#
#
#
# geopandas.geodataframe.DataFrame.explode(censusBlocks, )


NYcbGDF = geopandas.GeoDataFrame.from_file("NewYorkCensusBlocks.geojson")
NYcbMultiGDF = geopandas.GeoDataFrame(columns=NYcbGDF.columns)
NYcbPolyGDF = geopandas.GeoDataFrame(columns=NYcbGDF.columns)
for idx, row in NYcbGDF.iterrows():
    if (type(row.geometry) == Polygon):
        NYcbPolyGDF = NYcbPolyGDF.append(row)
    else:
        NYcbMultiGDF = NYcbMultiGDF.append(row)

NYcbMultiGDF.to_file("NYcbMultiGDF", driver='GeoJSON')
# NYcbMultiGDF.plot()



#     if type(row.geometry) == _typing.:
#         outdf = outdf.append(row,ignore_index=True)
#     if type(row.geometry) == MultiPolygon:
#         multdf = gpd.GeoDataFrame(columns=indf.columns)
#         recs = len(row.geometry)
#         multdf = multdf.append([row]*recs,ignore_index=True)
#         for geom in range(recs):
#             multdf.loc[geom,'geometry'] = row.geometry[geom]
#         outdf = outdf.append(multdf,ignore_index=True)
# return outdf



