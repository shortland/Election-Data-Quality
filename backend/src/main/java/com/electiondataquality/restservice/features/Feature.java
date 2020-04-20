package com.electiondataquality.restservice.features;

import java.util.ArrayList;

import com.electiondataquality.restservice.geometry.MultiPolygon;
import com.electiondataquality.restservice.geometry.Polygon;

public abstract class Feature {
    private ArrayList<ArrayList<ArrayList<double[]>>> shape;
    private boolean isMultiPolygon = false;

    public Feature() {
        this.shape = new ArrayList<ArrayList<ArrayList<double[]>>>();
    }

    public Feature(Polygon polygon) {
        if (polygon != null) {
            this.shape = new ArrayList<ArrayList<ArrayList<double[]>>>();
            this.shape.add(polygon.coordinates);
        }
    }

    public Feature(MultiPolygon multiPolygon) {
        if (multiPolygon != null) {
            this.shape = multiPolygon.coordinates;
            this.isMultiPolygon = true;
        }
    }

    public void setShape(Polygon polygon) {
        this.shape = new ArrayList<ArrayList<ArrayList<double[]>>>();
        this.shape.add(polygon.coordinates);
    }

    public void setShape(MultiPolygon multiPolygon) {
        this.shape = multiPolygon.coordinates;
    }

    public ArrayList<ArrayList<ArrayList<double[]>>> getShape() {
        return this.shape;
    }

    public boolean isMultiPolygon() {
        return this.isMultiPolygon;
    }

    public Polygon getPolygon() {
        if (!isMultiPolygon)
            return new Polygon(shape.get(0));
        else
            return null;
    }

    public MultiPolygon getMultiPolygon() {
        return new MultiPolygon(shape);
    }

    // // double[][][2]
    // public void setShape(double[][][] newShape) {
    // this.shape.clear();
    // // create arrayList<double[]>
    // for (int i = 0; i < newShape.length; i++) {
    // ArrayList<double[]> midArr = new ArrayList<double[]>();
    // this.shape.add(midArr);
    // for (int j = 0; j < newShape[i].length; j++) {
    // double[] cord = new double[2];
    // cord[0] = newShape[i][j][0];
    // cord[1] = newShape[i][j][1];
    // this.shape.get(i).add(cord);
    // }
    // }
    // }

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
