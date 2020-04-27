package com.electiondataquality.geometry;

import java.util.ArrayList;

public class MultiPolygon {

    public ArrayList<ArrayList<ArrayList<double[]>>> coordinates;

    public MultiPolygon(ArrayList<ArrayList<ArrayList<double[]>>> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPolygon() {
        this.coordinates = new ArrayList<ArrayList<ArrayList<double[]>>>();
    }
}
