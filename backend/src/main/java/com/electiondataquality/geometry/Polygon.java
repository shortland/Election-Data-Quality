package com.electiondataquality.geometry;

import java.util.List;
import java.util.ArrayList;

public class Polygon {

    public List<List<double[]>> coordinates;

    public Polygon(List<List<double[]>> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPolygon toMultiPolygon() {
        List<List<List<double[]>>> multi = new ArrayList<List<List<double[]>>>();

        multi.add(this.coordinates);

        return new MultiPolygon(multi);
    }
}
