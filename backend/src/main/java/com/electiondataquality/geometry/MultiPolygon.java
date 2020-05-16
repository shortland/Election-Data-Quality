package com.electiondataquality.geometry;

import java.util.List;
import java.util.ArrayList;

public class MultiPolygon {

    public List<List<List<double[]>>> coordinates;

    public MultiPolygon(List<List<List<double[]>>> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPolygon() {
        this.coordinates = new ArrayList<List<List<double[]>>>();
    }
}
