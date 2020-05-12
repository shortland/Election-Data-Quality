package com.electiondataquality.geometry.util;

import com.google.gson.Gson;

import com.electiondataquality.geometry.Geometry;
import com.electiondataquality.geometry.GeometryCoords;
import com.electiondataquality.geometry.GeometryType;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.Polygon;
import com.electiondataquality.restservice.RestServiceApplication;

public class RawGeometryToShape {

    public static MultiPolygon convertRawGeometryToMultiPolygon(String rawGeometry) {
        Gson gson = new Gson();

        GeometryType geoType = gson.fromJson(rawGeometry, GeometryType.class);

        if (geoType.type.equals("Polygon")) {
            return gson.fromJson(rawGeometry, Polygon.class).toMultiPolygon();
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

    public static String convertGeometryToRaw(Geometry geo) {
        Gson gson = new Gson();

        return gson.toJson(geo);
    }

    /**
     * Coords being a Polygon; e.g.) [[[0,1],[2,3],[4,8]]]
     */
    public static Geometry convertPolygonRawCoordsToGeometry(String rawCoords) {
        Gson gson = new Gson();
        Geometry geo = new Geometry();

        GeometryCoords geoCoords = gson.fromJson("{\"coordinates\": [" + rawCoords + "]}", GeometryCoords.class);

        geo.type = "MultiPolygon";
        geo.coordinates = geoCoords.coordinates;

        RestServiceApplication.logger.info("The new geo object from rawCoords is: " + geo.toString());

        return geo;
    }
}
