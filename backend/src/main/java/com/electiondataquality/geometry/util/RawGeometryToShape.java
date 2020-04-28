package com.electiondataquality.geometry.util;

import com.electiondataquality.geometry.Geometry;
import com.electiondataquality.geometry.GeometryType;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.Polygon;
import com.google.gson.Gson;

public class RawGeometryToShape {

    public static MultiPolygon convertRawGeometryToMultiPolygon(String rawGeometry) {
        Gson gson = new Gson();
        GeometryType geoType = gson.fromJson(rawGeometry, GeometryType.class);

        if (geoType.type.equals("Polygon")) {
            Polygon shapeP = gson.fromJson(rawGeometry, Polygon.class);
            return shapeP.toMultiPolygon();
        } else if (geoType.type.equals("MultiPolygon")) {
            return gson.fromJson(rawGeometry, MultiPolygon.class);
        }

        return new MultiPolygon();
    }

    public static Geometry convertRawToGeometry(String rawGeometry) {
        Geometry geo = new Geometry();
        geo.type = "MultiPolygon";
        geo.coordinates = RawGeometryToShape.convertRawGeometryToMultiPolygon(rawGeometry).coordinates;

        return geo;
    }

    // public static MultiPolygon convertGeometryToString(Geometry geometry) {
    // Gson gson = new Gson();
    // GeometryType geoType = gson.fromJson(rawGeometry, GeometryType.class);

    // if (geoType.type.equals("Polygon")) {
    // Polygon shapeP = gson.fromJson(rawGeometry, Polygon.class);
    // return shapeP.toMultiPolygon();
    // } else if (geoType.type.equals("MultiPolygon")) {
    // return gson.fromJson(rawGeometry, MultiPolygon.class);
    // }

    // return new MultiPolygon();
    // }
}
