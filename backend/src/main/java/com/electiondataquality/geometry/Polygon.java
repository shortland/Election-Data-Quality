package com.electiondataquality.geometry;

import java.util.ArrayList;

public class Polygon {

    public ArrayList<ArrayList<double[]>> coordinates;

    public Polygon(ArrayList<ArrayList<double[]>> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPolygon toMultiPolygon() {
        ArrayList<ArrayList<ArrayList<double[]>>> multi = new ArrayList<ArrayList<ArrayList<double[]>>>();

        multi.add(this.coordinates);

        return new MultiPolygon(multi);
    }
}
