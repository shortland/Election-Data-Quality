package com.electiondataquality.features;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.electiondataquality.features.util.CompareFeatureShape;
import com.electiondataquality.geometry.Geometry;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.Polygon;

public abstract class Feature {

    public Geometry geometry;

    /**
     * This isn't used ATM. @see Geometry above
     */
    @JsonIgnore
    public List<List<List<double[]>>> shape;

    @JsonIgnore
    public boolean isMultiPolygon = false;

    public static boolean CompareShape(Feature f1, Feature f2) {
        return CompareFeatureShape.CompareFeatures(f1, f2);
    }

    public Feature() {
        this.shape = new ArrayList<List<List<double[]>>>();
    }

    public Feature(MultiPolygon multiPolygon) {
        if (multiPolygon != null) {
            this.shape = multiPolygon.coordinates;
            this.isMultiPolygon = true;
        }
    }

    public void setShape(MultiPolygon multiPolygon) {
        this.shape = multiPolygon.coordinates;
    }

    public List<List<List<double[]>>> getShape() {
        return this.shape;
    }

    public boolean isMultiPolygon() {
        return this.isMultiPolygon;
    }

    @JsonIgnore
    public Polygon getPolygon() {
        if (!isMultiPolygon) {
            return new Polygon(shape.get(0));
        }

        return null;
    }

    @JsonIgnore
    public MultiPolygon getMultiPolygon() {
        return new MultiPolygon(shape);
    }

    public String shapeToString() {
        String str = "";

        for (int i = 0; i < this.shape.size(); i++) {
            for (int j = 0; j < this.shape.get(i).size(); j++) {
                for (int k = 0; k < this.shape.get(i).get(j).size(); k++) {
                    str = str + Double.toString(this.shape.get(i).get(j).get(k)[0]) + ", "
                            + Double.toString(this.shape.get(i).get(j).get(k)[1]) + "\n";
                }
            }
        }

        return str;
    }
}
